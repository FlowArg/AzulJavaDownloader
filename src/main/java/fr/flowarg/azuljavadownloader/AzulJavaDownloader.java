package fr.flowarg.azuljavadownloader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import fr.flowarg.flowio.FileUtils;
import fr.flowarg.flowstringer.StringUtils;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * This class is used to retrieve, download and install Java versions from Azul Zulu's API.
 * <pre>
 * {@code
 * // Example :
 * final AzulJavaDownloader downloader = new AzulJavaDownloader(System.out::println);
 * final Path javas = Paths.get("javas"); // The directory where the Java versions will be downloaded.
 * final AzulJavaBuildInfo buildInfoWindows = downloader.getBuildInfo(new RequestedJavaInfo("17", AzulJavaType.JDK, AzulJavaOS.WINDOWS, AzulJavaArch.X64).setJavaFxBundled(true)); // jdk 17 with javafx for windows 64 bits
 * final Path javaHomeWindows = downloader.downloadAndInstall(buildInfoWindows, javas);
 * System.out.println(javaHomeWindows.toAbsolutePath());
 *
 * final AzulJavaBuildInfo buildInfoLinux = downloader.getBuildInfo(new RequestedJavaInfo("17", AzulJavaType.JDK, AzulJavaOS.LINUX, AzulJavaArch.X64).setJavaFxBundled(true)); // jdk 17 with javafx for linux 64 bits
 * final Path javaHomeLinux = downloader.downloadAndInstall(buildInfoLinux, javas);
 * System.out.println(javaHomeLinux.toAbsolutePath());
 * }
 * </pre>
 * @see <a href="https://docs.azul.com/core/install/metadata-api">Azul Zulu's Metadata API</a>
 */
public class AzulJavaDownloader
{
    private static final String API_ENDPOINT_PACKAGES = "https://api.azul.com/metadata/v1/zulu/packages/";
    private static final String API_ENDPOINT_PACKAGE = "https://api.azul.com/metadata/v1/zulu/packages/%s";
    private static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();

    private final Callback callback;

    /**
     * Create a new AzulJavaDownloader with no callback.
     */
    public AzulJavaDownloader()
    {
        this.callback = null;
    }

    /**
     * Create a new AzulJavaDownloader with a callback.
     * @param callback The callback to use.
     */
    public AzulJavaDownloader(@NotNull Callback callback)
    {
        this.callback = callback;
    }

    /**
     * Get the build info of the requested Java version.
     * @param requestedJavaInfo The requested Java version with specific details.
     * @return The build info of the requested Java version.
     * @throws IOException If the build info cannot be found.
     */
    public AzulJavaBuildInfo getBuildInfo(RequestedJavaInfo requestedJavaInfo) throws IOException
    {
        if(this.callback != null)
            this.callback.onStep(Callback.Step.QUERYING);

        try
        {
            return requestPackage(requestedJavaInfo);
        }
        catch (IOException e)
        {
            throw new IOException("No build info found!", e);
        }
    }

    /**
     * Download and unzip the given archive of the provided Java version into the given directory.
     * @param buildInfo The build info of the Java version to download.
     * @param dirPath The directory where the Java version will be downloaded.
     * @return The path of the extracted Java version.
     * @throws IOException If the download or the extraction fails.
     */
    public Path downloadAndInstall(AzulJavaBuildInfo buildInfo, Path dirPath) throws IOException
    {
        final Path archivePath = dirPath.resolve(buildInfo.getName());
        final Path extractedPath = dirPath.resolve(StringUtils.empty(buildInfo.getName(), "." + buildInfo.getArchiveType()));

        if(Files.notExists(extractedPath))
            Files.createDirectories(extractedPath);

        if(this.callback != null)
            this.callback.onStep(Callback.Step.DOWNLOADING);

        if(Files.notExists(archivePath) || !FileUtils.getMD5(archivePath).equalsIgnoreCase(buildInfo.getMd5Hash()))
        {
            Files.deleteIfExists(archivePath);
            Files.copy(new URL(buildInfo.getDownloadUrl()).openStream(), archivePath);
        }

        if(this.callback != null)
            this.callback.onStep(Callback.Step.EXTRACTING);

        if(buildInfo.getArchiveType().equalsIgnoreCase("zip"))
            smartUnzip(dirPath, archivePath);
        else if(buildInfo.getArchiveType().equalsIgnoreCase("tar.gz"))
            smartDecompressTarArchive(archivePath, dirPath);

        if (this.callback != null)
            this.callback.onStep(Callback.Step.DONE);

        return extractedPath.toAbsolutePath();
    }

    // Internal methods

    private static AzulJavaBuildInfo requestPackage(RequestedJavaInfo requestedJavaInfo) throws IOException
    {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format(API_ENDPOINT_PACKAGE, requestPackages(requestedJavaInfo))).openConnection();
        connection.addRequestProperty("Accept", "application/json");
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
        connection.setInstanceFollowRedirects(true);
        try(InputStream stream = new BufferedInputStream(connection.getInputStream()))
        {
            final ReadableByteChannel rbc = Channels.newChannel(stream);
            final Reader enclosedReader = Channels.newReader(rbc, StandardCharsets.UTF_8.newDecoder(), -1);

            return GSON.fromJson(enclosedReader, AzulJavaBuildInfo.class);
        }
    }

    private static String requestPackages(RequestedJavaInfo requestedJavaInfo) throws IOException
    {
        HttpsURLConnection connection = (HttpsURLConnection)new URL(requestedJavaInfo.buildParams(API_ENDPOINT_PACKAGES)).openConnection();
        connection.addRequestProperty("Accept", "application/json");
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
        connection.setInstanceFollowRedirects(true);
        try(InputStream stream = new BufferedInputStream(connection.getInputStream()))
        {
            final ReadableByteChannel rbc = Channels.newChannel(stream);
            final Reader enclosedReader = Channels.newReader(rbc, StandardCharsets.UTF_8.newDecoder(), -1);
            final JsonArray array = JsonParser.parseReader(new BufferedReader(enclosedReader)).getAsJsonArray();
            return array.get(0).getAsJsonObject().get("package_uuid").getAsString();
        }
    }

    private static void smartDecompressTarArchive(final Path tarGzFile, final Path destinationDir) throws IOException
    {
        try(final InputStream is = Files.newInputStream(tarGzFile)
            ; final GzipCompressorInputStream gzipIn = new GzipCompressorInputStream(is)
            ; final TarArchiveInputStream tarIn = new TarArchiveInputStream(gzipIn)
        )
        {
            TarArchiveEntry entry;

            while ((entry = tarIn.getNextEntry()) != null)
            {
                final Path path = destinationDir.resolve(entry.getName());
                if(entry.isDirectory())
                    Files.createDirectories(path);
                else
                {
                    if(Files.notExists(path) || FileUtils.getFileSizeBytes(path) != entry.getSize())
                    {
                        int count;
                        final byte[] data = new byte[4096];
                        try(final OutputStream output = Files.newOutputStream(path))
                        {
                            while((count = tarIn.read(data, 0, 4096)) != -1)
                                output.write(data, 0, count);
                        }
                    }
                }
            }
        }
    }

    private static void smartUnzip(Path destinationDir, Path zipFile) throws IOException
    {
        try(final ZipFile toUnZip = new ZipFile(zipFile.toFile()))
        {
            final Enumeration<? extends ZipEntry> enu = toUnZip.entries();
            while (enu.hasMoreElements())
            {
                final ZipEntry entry = enu.nextElement();
                smartUnzip0(destinationDir.resolve(entry.getName()), toUnZip, entry);
            }
        }
    }

    private static void smartUnzip0(Path fl, ZipFile zipFile, ZipEntry entry) throws IOException
    {
        if(entry.isDirectory())
            return;

        if (Files.notExists(fl))
            Files.createDirectories(fl.getParent());

        if(Files.notExists(fl) || FileUtils.getCRC32(fl) != entry.getCrc())
            Files.copy(zipFile.getInputStream(entry), fl, StandardCopyOption.REPLACE_EXISTING);
    }
}
