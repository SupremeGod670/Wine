package com.example.wine.data.local.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.wine.data.local.entity.WineryEntity;

import java.util.List;

@Dao
public interface WineryDao {

    @Insert
    long insert(WineryEntity winery);

    @Query("SELECT * FROM wineries")
    List<WineryEntity> getAllWineries();

    @Query("SELECT * FROM wineries WHERE id = :id LIMIT 1")
    WineryEntity getById(int id);

    @Update
    int update(WineryEntity winery);

    @Delete
    int delete(WineryEntity winery);
}