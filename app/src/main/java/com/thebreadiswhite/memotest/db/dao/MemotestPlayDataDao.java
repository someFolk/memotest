package com.thebreadiswhite.memotest.db.dao;

import androidx.room.*;
import com.thebreadiswhite.memotest.MemotestPlayData;

import java.util.List;

@Dao
public interface MemotestPlayDataDao
{
    @Query("SELECT * FROM play_data WHERE parent = :parent")
    List<MemotestPlayData> getAllItemsByStack(int parent);

    @Query("SELECT COUNT(*) from play_data")
    int count();

    @Delete
    void delete(MemotestPlayData data);

    @Insert
    void insert(MemotestPlayData data);

    @Update
    void update(MemotestPlayData data);
}
