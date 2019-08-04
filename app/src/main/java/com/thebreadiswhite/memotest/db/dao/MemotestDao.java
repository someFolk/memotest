package com.thebreadiswhite.memotest.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.thebreadiswhite.memotest.Memotest;

import java.util.List;

@Dao
public interface MemotestDao {

    @Query("SELECT * FROM memotest")
    List<Memotest> getAll();

    @Query("SELECT * FROM memotest WHERE stack = :stackid")
    List<Memotest> findAllByKey(int stackid);

    @Query("SELECT COUNT(*) from memotest")
    int count();

    @Query("SELECT COUNT(stack) FROM memotest WHERE stack = :stackid")
    int countStackItems(int stackid);

    @Insert
    void insert(Memotest memotest);

    @Delete
    void delete(Memotest memotest);
}