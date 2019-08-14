package com.thebreadiswhite.memotest.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Ignore;
import com.google.firebase.database.Exclude;

//This enum purpose is to have a clear value to name relationship
// when handling with data from the database
public enum GeneralConsts
{
    // For boolean storing on DB
    TRUE(1, "True"),
    FALSE(0, "False"),

    // FOR QUESTIONERY
    YES(1, "Yes"),
    NO(0, "No"),
    MAYBE(2, "Maybe"),

    // For favourites
    FAVOURITE(1, "Favourite"),
    NON_FAVOURITE(0, "Non Favourite");

    public int value;

    @Ignore
    @Exclude
    public String name;

    GeneralConsts(int value, String name)
    {
        this.value = value;
    }

    // The purpose of this method is to get
    // constant by the value stored on the database.
    @Nullable
    public static GeneralConsts getConstByValue(int value) {
        GeneralConsts constant = getDefault();
        for(GeneralConsts tempVal : GeneralConsts.values()) {
            if(tempVal.value == value) {
                constant = tempVal;
                break;
            }
        }
        return constant;
    }

    // The default value of this enum.
    public static GeneralConsts getDefault()
    {
        return TRUE;
    }
}
