package com.treedbscan.core;

public abstract class AbstractPoint
{
    private Boolean processed = false;

    public Boolean getProcessed()
    {
        return processed;
    }

    public void setProcessed(Boolean processed)
    {
        this.processed = processed;
    }
}
