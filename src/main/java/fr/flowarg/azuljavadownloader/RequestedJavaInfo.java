package fr.flowarg.azuljavadownloader;

public class RequestedJavaInfo
{
    private final String javaVersion;
    private final AzulJavaType javaType;
    private final AzulJavaOS os;
    private final AzulJavaArch arch;

    // optionals
    private boolean javafxBundled;
    private String archiveType;

    public RequestedJavaInfo(String javaVersion, AzulJavaType javaType, AzulJavaOS os, AzulJavaArch arch)
    {
        this.javaVersion = javaVersion;
        this.javaType = javaType;
        this.os = os;
        this.arch = arch;
        this.archiveType = this.os == AzulJavaOS.WINDOWS ? "zip" : "tar.gz";
    }

    public String getJavaVersion()
    {
        return this.javaVersion;
    }

    public AzulJavaType getJavaType()
    {
        return this.javaType;
    }

    public AzulJavaOS getOs()
    {
        return this.os;
    }

    public AzulJavaArch getArch()
    {
        return this.arch;
    }

    public boolean isJavafxBundled()
    {
        return this.javafxBundled;
    }

    public String getBinaryType()
    {
        return this.archiveType;
    }

    public RequestedJavaInfo setJavaFxBundled(boolean javafxBundled)
    {
        this.javafxBundled = javafxBundled;
        return this;
    }

    public RequestedJavaInfo setArchiveType(String archiveType)
    {
        this.archiveType = archiveType;
        return this;
    }

    public String buildParams(String apiEndpointPackages)
    {
        return apiEndpointPackages + String.format(
                "?java_version=%s&os=%s&arch=%s&java_package_type=%s&javafx_bundled=%s&latest=true&archive_type=%s&page=1&page_size=100",
                this.javaVersion, this.os.toString(), this.arch.toString(), this.javaType.toString(), this.javafxBundled, this.archiveType
        );
    }
}
