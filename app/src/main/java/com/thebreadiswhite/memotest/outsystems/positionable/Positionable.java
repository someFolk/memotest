package com.thebreadiswhite.memotest.outsystems.positionable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import com.google.firebase.database.Exclude;


// This is the object which represent a new positionable list.
// This holds the count of the list which responsible of giving a new position to new items.
// This holds the id of this list.

// There is a root positionable object with the id of 0
// which gives every positionable object a new id.
// So basically every item of positionable is positioned by a root positionable object.
// This so the ID will represent as INT and will be database applicapable.
@Entity(tableName = "positionable")
public class Positionable
{

    // The count of items in the `list`
    // So when a new item is signed it gets the counter value to it's position
    // Even if item is deleted with a position, tge system will sort it by it's value
    // and display it by it's `for` loop count
    @ColumnInfo(name = "count")
    private int count;

    // The target key which represent the container which under it count the position
    @ColumnInfo(name = "target_key")
    private int targetKey;

    // the table name of the object that need positioning
    // It basically contain constants of the objects name
    // and it's database table name so it know where to update
    // objects are those who implements @PositionableAdapter
    @Exclude
    private String positionableType;

    // The target key of the object
    @Exclude
    private String objectTargetKey;

    public Positionable(int count){}

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public int getTargetKey()
    {
        return targetKey;
    }

    public void setTargetKey(int targetKey)
    {
        this.targetKey = targetKey;
    }

    public String getPositionableType()
    {
        return positionableType;
    }

    public void setPositionableType(String positionableType)
    {
        this.positionableType = positionableType;
    }

    public String getObjectTargetKey()
    {
        return objectTargetKey;
    }

    public void setObjectTargetKey(String objectTargetKey)
    {
        this.objectTargetKey = objectTargetKey;
    }
}
