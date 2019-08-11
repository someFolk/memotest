package com.thebreadiswhite.memotest.db.memotest.memotest;

import androidx.room.*;
import com.thebreadiswhite.memotest.Memotest;

import java.util.List;

@Dao
public interface MemotestDao {

    @Query("SELECT * FROM memotest")
    List<Memotest> getAll();

    @Query("SELECT * FROM memotest WHERE pk = :id")
    Memotest getById(int id);

    @Query("SELECT * FROM memotest WHERE stack = :stackid")
    List<Memotest> getAllChilds(int stackid);

    @Query("SELECT COUNT(*) from memotest")
    int count();

    @Query("SELECT COUNT(stack) FROM memotest WHERE stack = :stackid")
    int countStackItems(int stackid);

    @Insert
    Long insert(Memotest memotest);

    @Delete
    void delete(Memotest memotest);

    @Update
    void update(Memotest memotest);
}