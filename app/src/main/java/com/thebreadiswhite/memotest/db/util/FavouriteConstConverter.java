package com.thebreadiswhite.memotest.db.util;

import androidx.room.TypeConverter;
import com.thebreadiswhite.memotest.util.FavouriteConst;

public class FavouriteConstConverter
{

    // Getting an
    @TypeConverter
    public static int getValueByConst(FavouriteConst constant)
    {
        return constant.value;
    }

    @TypeConverter
    public static FavouriteConst getConstByValue(int value)
    {
        return FavouriteConst.getConstByValue(value);
    }


}
