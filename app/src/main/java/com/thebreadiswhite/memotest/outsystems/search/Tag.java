package com.thebreadiswhite.memotest.outsystems.search;


import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// The Tag class represent a tag that exist in the search engine.
// Tags may be found in titles description or even text, that depends on what you feeds it.
public abstract class Tag
{
    // This is the default value of the info tag.
    // Just some portion of the system will use the info var.
    public static final String INFO_DEFAULT_VALUE = "0";

    // The tag itself
    private String tag;

    // The key that represent
    // this tag on the keys table.

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "client_key")
    private int clientKey;

    @ColumnInfo(name = "server_key")
    private int serverKey;

    // OPTIONAL. some of the system will make use of this.
    // such as the music app where you click on a tag and songs that belongs shows up there
    // a description might be great in that case.
    // On other cases, it stays on the default value
    @ColumnInfo(name = "info")
    private String info = INFO_DEFAULT_VALUE;

    // This contains the class that suppose to open the search result.
    // It will be set on the inherited class
    @Ignore
    Class klass;
}
