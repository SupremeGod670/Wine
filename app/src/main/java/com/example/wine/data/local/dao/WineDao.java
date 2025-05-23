package com.example.wine.data.local.dao;


import androidx.room.*;
import com.example.wine.data.local.dao.WineryDao;
import com.example.wine.data.local.entity.WineEntity;

import java.util.List;

@Dao
public interface WineDao {

    @Insert
    long insert(WineEntity wine);

    @Query("SELECT * FROM wine")

    List<WineEntity> getAllWines();

    @Query("SELECT * FROM wine WHERE id = :id LIMIT 1")
    WineEntity getWineById(int id);

    @Update
    int update(WineEntity wine);

    @Delete
    int delete(WineEntity wine);


}