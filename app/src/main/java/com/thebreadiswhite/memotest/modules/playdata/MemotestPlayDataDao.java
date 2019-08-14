package com.thebreadiswhite.memotest.modules.playdata;

import androidx.room.*;
import com.thebreadiswhite.memotest.modules.playdata.MemotestPlayData;

import java.util.List;

@Dao
public interface MemotestPlayDataDao
{
    @Query("SELECT * FROM play_data WHERE parent = :parent")
    List<MemotestPlayData> getAllItemsByStack(int parent);

    // This will be useful for the number
    // of times played in the game statistic
    @Query("SELECT COUNT(*) from play_data")
    int count();

    @Delete
    void delete(MemotestPlayData data);

    @Insert
    Long insert(MemotestPlayData data);

    @Update
    void update(MemotestPlayData data);
}
