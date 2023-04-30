package fr.flowarg.azuljavadownloader;

public enum AzulJavaType
{
    JDK("jdk"),
    JRE("jre");

    private final String type;

    AzulJavaType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return this.type;
    }

}
