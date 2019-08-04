package com.thebreadiswhite.memotest.db.dao;

import androidx.room.*;
import com.thebreadiswhite.memotest.MemotestStack;

import java.util.List;

@Dao
public interface MemotestStackDao
{
    // Selecting a specific stack
    @Query("SELECT * FROM memotest_stack WHERE id = :uid")
    MemotestStack getStackById(int uid);

    // Get all stacks to be found
    @TypeConverters(MemotestStackConverter.class)
    @Query("SELECT * FROM memotest_stack")
    List<MemotestStack> getAllStacks();

    // Get Count of the
    @Query("SELECT COUNT(*) from memotest_stack")
    int count();

    // Insert
    @Insert
    long insert(MemotestStack stack);

    // Delete
    @Delete
    void deleteStack(MemotestStack stack);

    @Update
    void update(MemotestStack stack);
}
