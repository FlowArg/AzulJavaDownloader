package fr.flowarg.azuljavadownloader;

public interface Callback
{
    void onStep(Step step);

    enum Step
    {
        QUERYING,
        DOWNLOADING,
        EXTRACTING,
        DONE
    }
}
