package fr.flowarg.azuljavadownloader;

public class RequestedJavaInfo
{
    private final String javaVersion;
    private final AzulJavaType javaType;
    private final String os;
    private final String arch;
    private final boolean javafxBundled;

    // optionals
    private String binaryType;

    public RequestedJavaInfo(String javaVersion, AzulJavaType javaType, String os, String arch, boolean javafxBundled)
    {
        this.javaVersion = javaVersion;
        this.javaType = javaType;
        this.os = os;
        this.arch = arch;
        this.javafxBundled = javafxBundled;
        this.binaryType = this.os.equalsIgnoreCase("windows") ? "zip" : "tar.gz";
    }

    public String getJavaVersion()
    {
        return this.javaVersion;
    }

    public AzulJavaType getJavaType()
    {
        return this.javaType;
    }

    public String getOs()
    {
        return this.os;
    }

    public String getArch()
    {
        return this.arch;
    }

    public boolean isJavafxBundled()
    {
        return this.javafxBundled;
    }

    public String getBinaryType()
    {
        return this.binaryType;
    }

    public RequestedJavaInfo setBinaryType(String binaryType)
    {
        this.binaryType = binaryType;
        return this;
    }

    public String buildParams(String apiEndpointPackages)
    {
        return apiEndpointPackages + String.format(
                "?java_version=%s&os=%s&arch=%s&java_package_type=%s&javafx_bundled=%s&latest=true&archive_type=%s&page=1&page_size=100",
                this.javaVersion, this.os, this.arch, this.javaType.getType(), this.javafxBundled, this.binaryType
        );
    }
}
