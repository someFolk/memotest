package com.thebreadiswhite.memotest.systems.positionable;

import android.content.Context;
import com.google.firebase.database.Exclude;

public class Positionable
{
    // The count of items in the `list`
    // So when a new item is signed it gets the counter value to it's position
    // Even if item is deleted with a position, tge system will sort it by it's value
    // and display it by it's `for` loop count
    private int count;

    // The target key which represent the container which under it count the position
    private String containerTargetKey;

    // the table name of the object that need positioning
    // It basically contain constants of the objects name
    // and it's database table name so it know where to update
    // objects are those who implements @PositionableAdapter
    @Exclude
    private String positionableType;

    // The target key of the object
    @Exclude
    private String objectTargetKey;

    public Positionable(){}

}
