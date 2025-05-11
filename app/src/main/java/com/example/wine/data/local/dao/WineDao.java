package com.example.wine.data.local.dao;


import androidx.room.*;
import com.example.wine.data.local.entity.WineEntity;
import java.util.List;

@Dao
public interface WineDao {

    @Query("SELECT * FROM wine")
    List<WineEntity> getAll();

    @Insert
    void insert(WineEntity wine);

    @Update
    void update(WineEntity wine);

    @Delete
    void delete(WineEntity wine);

    @Query("SELECT * FROM wine WHERE id = :id")
    WineEntity getById(int id);
}