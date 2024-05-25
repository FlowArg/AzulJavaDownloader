package fr.flowarg.azuljavadownloader;

public enum AzulJavaOS
{
    MACOS("macos"),
    LINUX("linux"),
    WINDOWS("windows"),

    // mostly unused
    SOLARIS("solaris"),
    AIX("aix"),
    QNX("qnx"),
    LINUX_MUSL("linux-musl"),
    LINUX_GLIBC("linux-glibc");

    private final String os;

    AzulJavaOS(String os)
    {
        this.os = os;
    }

    public String getOs()
    {
        return this.os;
    }

    @Override
    public String toString()
    {
        return this.os;
    }
}
