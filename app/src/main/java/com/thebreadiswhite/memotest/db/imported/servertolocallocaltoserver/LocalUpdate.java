package com.morsela.tel.db.servertolocallocaltoserver;

import com.google.firebase.database.Exclude;
import com.morsela.tel.db.DatabaseAdapter;

public class LocalUpdate extends DatabaseAdapter
{
    @Exclude
    private boolean isDone;

    private String node;
    private String targetKey;
    private LocalUpdateType type;

    // Firebase optimization
    public LocalUpdate(){}

    public LocalUpdate(String firebaseKey, long timestamp, String node, boolean isDone, String targetKey, LocalUpdateType type)
    {
        this.firebaseKey = firebaseKey;
        this.timestamp = timestamp;
        this.isDone = isDone;
        this.node = node;
        this.targetKey = targetKey;
        this.type = type;
    }

    public LocalUpdate(String node, String targetKey, LocalUpdateType type)
    {
        isDone = false;
        this.node = node;
        this.targetKey = targetKey;
        this.type = type;
    }

    public boolean isDone()
    {
        return isDone;
    }

    public void setIsDone(boolean isDone)
    {
        this.isDone = isDone;
    }

    public String getNode()
    {
        return node;
    }

    public String getTargetKey()
    {
        return targetKey;
    }

    public LocalUpdateType getType()
    {
        return type;
    }
}
