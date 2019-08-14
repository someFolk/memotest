package com.thebreadiswhite.memotest.modules.stack;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thebreadiswhite.memotest.modules.memotest.Memotest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MemotestStackConverter
{
    
    @TypeConverter
    public static List<Memotest> stringToSomeObjectList(String data) {
        if (data == null) {
            return new ArrayList<>();
        }

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Memotest>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Memotest> someObjects) {
        Gson gson = new Gson();
        return gson.toJson(someObjects);
    }
}
