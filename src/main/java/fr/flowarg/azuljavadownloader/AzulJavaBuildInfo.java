package fr.flowarg.azuljavadownloader;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AzulJavaBuildInfo
{
    @SerializedName("package_uuid")
    private final String packageUUID;
    private final String name;
    @SerializedName("md5_hash")
    private final String md5Hash;
    @SerializedName("sha256_hash")
    private final String sha256Hash;
    @SerializedName("build_date")
    private final String buildDate;
    @SerializedName("last_modified")
    private final String lastModified;
    @SerializedName("download_url")
    private final String downloadUrl;
    private final String product;
    @SerializedName("java_version")
    private final List<String> javaVersion;
    @SerializedName("java_package_type")
    private final String javaPackageType;
    @SerializedName("javafx_bundled")
    private final boolean javafxBundled;
    @SerializedName("java_package_features")
    private final List<String> javaPackageFeatures;
    private final String os;
    @SerializedName("archive_type")
    private final String archiveType;
    private final int size;
    private final boolean latest;
    @SerializedName("distro_version")
    private final List<String> distroVersion;

    public AzulJavaBuildInfo(String packageUUID, String name, String md5Hash, String sha256Hash, String buildDate, String lastModified, String downloadUrl, String product, List<String> javaVersion, String javaPackageType, boolean javafxBundled, List<String> javaPackageFeatures, String os, String archiveType, int size, boolean latest, List<String> distroVersion)
    {
        this.packageUUID = packageUUID;
        this.name = name;
        this.md5Hash = md5Hash;
        this.sha256Hash = sha256Hash;
        this.buildDate = buildDate;
        this.lastModified = lastModified;
        this.downloadUrl = downloadUrl;
        this.product = product;
        this.javaVersion = javaVersion;
        this.javaPackageType = javaPackageType;
        this.javafxBundled = javafxBundled;
        this.javaPackageFeatures = javaPackageFeatures;
        this.os = os;
        this.archiveType = archiveType;
        this.size = size;
        this.latest = latest;
        this.distroVersion = distroVersion;
    }

    public String getPackageUUID()
    {
        return this.packageUUID;
    }

    public String getName()
    {
        return this.name;
    }

    public String getMd5Hash()
    {
        return this.md5Hash;
    }

    public String getSha256Hash()
    {
        return this.sha256Hash;
    }

    public String getBuildDate()
    {
        return this.buildDate;
    }

    public String getLastModified()
    {
        return this.lastModified;
    }

    public String getDownloadUrl()
    {
        return this.downloadUrl;
    }

    public String getProduct()
    {
        return this.product;
    }

    public List<String> getJavaVersion()
    {
        return this.javaVersion;
    }

    public String getJavaPackageType()
    {
        return this.javaPackageType;
    }

    public boolean isJavafxBundled()
    {
        return this.javafxBundled;
    }

    public List<String> getJavaPackageFeatures()
    {
        return this.javaPackageFeatures;
    }

    public String getOs()
    {
        return this.os;
    }

    public String getArchiveType()
    {
        return this.archiveType;
    }

    public int getSize()
    {
        return this.size;
    }

    public boolean isLatest()
    {
        return this.latest;
    }

    public List<String> getDistroVersion()
    {
        return this.distroVersion;
    }

    @Override
    public String toString()
    {
        return "AzulJavaBuildInfo{" + "packageUUID='" + this.packageUUID + '\'' + ", name='" + this.name + '\'' +
                ", md5Hash='" + this.md5Hash + '\'' + ", sha256Hash='" + this.sha256Hash + '\'' + ", buildDate='" +
                this.buildDate + '\'' + ", lastModified='" + this.lastModified + '\'' + ", downloadUrl='" +
                this.downloadUrl + '\'' + ", product='" + this.product + '\'' + ", javaVersion=" + this.javaVersion +
                ", javaPackageType='" + this.javaPackageType + '\'' + ", javafxBundled=" + this.javafxBundled +
                ", javaPackageFeatures=" + this.javaPackageFeatures + ", os='" + this.os + '\'' + ", archiveType='" +
                this.archiveType + '\'' + ", size=" + this.size + ", latest=" + this.latest + ", distroVersion=" + this.distroVersion + '}';
    }
}
