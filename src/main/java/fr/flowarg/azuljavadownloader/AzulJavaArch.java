package fr.flowarg.azuljavadownloader;

public enum AzulJavaArch
{
    /** x86 64bit and x86 32bit */
    X86("x86"),
    /** x86 64bit same as {@link #AMD64} */
    X64("x64"),
    /** x86 64bit same as {@link #X64} */
    AMD64("amd64"),
    /** x86 32bit */
    I686("i686"),
    /** arm 64bit and arm 32bit */
    ARM("arm"),
    /** arm 64bit */
    AARCH64("aarch64"),
    /** arm 32bit */
    AARCH32("aarch32"),

    // mostly unused
    /** arm 32bit soft float */
    AARCH32SF("aarch32sf"),
    /** arm 32bit hard float */
    AARCH32HF("aarch32hf"),
    /** ppc 64bit and ppc 32bit */
    PPC("ppc"),
    /** ppc 64bit */
    PPC64("ppc64"),
    /** ppc 32bit */
    PPC32("ppc32"),
    /** ppc 32bit hard float */
    PPC32HF("ppc32hf"),
    /** ppc 32bit spe */
    PPC32SPE("ppc32spe"),
    /** sparcv9 64bit and sparcv9 32bit */
    SPARCV9("sparcv9"),
    /** sparcv9 32bit */
    SPARCV9_32("sparcv9-32"),
    /** sparcv9 64bit */
    SPARCV9_64("sparcv9-64")
    ;

    private final String arch;

    AzulJavaArch(String arch)
    {
        this.arch = arch;
    }

    public String getArch()
    {
        return this.arch;
    }

    @Override
    public String toString()
    {
        return this.arch;
    }
}
