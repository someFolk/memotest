package com.thebreadiswhite.memotest.util;


// This enum is holding the constants of favouratability
// TODO: consider creating a favouriteable class that holds the view
// TODO: than it attach an onclick listener toa toggle method which toggle the
// TODO: favourateability of the object, than updating the specific object with it's DAO update which will be supplied also to the class

public enum FavouriteConst
{
    REGULAR(0),
    FAVOURITE(1);

    public int value;

    FavouriteConst(int value)
    {
        this.value = value;
    }

    // The purpose of this method is to get
    // constant by the value stored on the database.
    public static FavouriteConst getConstByValue(int value) {
        FavouriteConst constant = getDefault();
        for(FavouriteConst tempVal : FavouriteConst.values()) {
            if(tempVal.value == value) {
                constant = tempVal;
                break;
            }
        }
        return constant;
    }

    // The default value of this enum.
    public static FavouriteConst getDefault()
    {
        return REGULAR;
    }
}
