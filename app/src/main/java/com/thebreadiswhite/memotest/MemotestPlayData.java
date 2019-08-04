package com.thebreadiswhite.memotest;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "play_data")
public class MemotestPlayData
{
    // This is the primary key that's
    // assigned by the Room Database
    @PrimaryKey (autoGenerate = true)
    private int pk;

    // The total duration of
    // the play in seconds.
    @ColumnInfo(name = "duration")
    private int duration;

    // Which date the play conducted
    @ColumnInfo(name = "date")
    private int date;

    // The StackID of which
    // this play took place
    @ColumnInfo(name = "parent")
    private int parent;

    public MemotestPlayData(int pk, int duration, int date, int parent)
    {
        this.pk = pk;
        this.duration = duration;
        this.date = date;
        this.parent = parent;
    }

    public int getPk()
    {
        return pk;
    }

    public void setPk(int pk)
    {
        this.pk = pk;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public int getDate()
    {
        return date;
    }

    public void setDate(int date)
    {
        this.date = date;
    }

    public int getParent()
    {
        return parent;
    }

    public void setParent(int parent)
    {
        this.parent = parent;
    }
}
