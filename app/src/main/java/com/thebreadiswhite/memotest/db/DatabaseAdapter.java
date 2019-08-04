package com.thebreadiswhite.memotest.db;

import androidx.room.ColumnInfo;
import com.google.firebase.database.Exclude;

import java.util.Map;

// This class is a concrete foundation of every object
// That supposed to get stored on the database
public abstract class DatabaseAdapter
{
    // For that value to be accourate across all users feeding from the table
    // we need the user to deliver the full object with it's client key.
    // We are doing it by having a system that is responsible of figuring out which
    // client key position the saved key on the server supposed to be.
    @ColumnInfo(name = "client_key")
    protected int clientKey;

    // This is the server key generated by the Firebase database.
    // The key gets generated when a row allocated on the server.
    @Exclude
    @ColumnInfo(name = "server_key")
    protected String serverKey;

    // The timestamp of which the
    // row has been created.
    @ColumnInfo(name = "timestamp")
    protected long timestamp;


    public int getClientKey()
    {
        return clientKey;
    }

    public void setClientKey(int clientKey)
    {
        this.clientKey = clientKey;
    }

    public String getServerKey()
    {
        return serverKey;
    }

    public void setServerKey(String serverKey)
    {
        this.serverKey = serverKey;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }
}
