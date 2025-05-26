package com.example.wine.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wine.data.local.entity.WineryEntity;

import java.util.List;

@Dao
public interface WineryDao {
    @Insert
    void insertWinery(WineryEntity winery);

    @Update
    void updateWinery(WineryEntity winery);

    @Query("UPDATE winery SET deleted = 1, updatedAt = :updatedAt WHERE id = :id")
    void softDeleteWinery(String id, long updatedAt);

    @Query("SELECT * FROM winery WHERE deleted = 0")
    List<WineryEntity> getAllActiveWineries();

    @Query("SELECT * FROM winery WHERE id = :id AND deleted = 0 LIMIT 1")
    WineryEntity getWineryById(String id);
}
