package com.example.wine.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.wine.data.local.entity.WineEntity;
import java.util.List;

@Dao
public interface WineDao {
    @Query("SELECT * FROM wine WHERE deleted = 0 ORDER BY name ASC")
    List<WineEntity> getAll();

    @Query("SELECT * FROM wine WHERE id = :id AND deleted = 0 LIMIT 1")
    WineEntity getById(String id);

    @Insert
    void insert(WineEntity wine);

    @Update
    void update(WineEntity wine);

    @Query("UPDATE wine SET deleted = 1 WHERE id = :id")
    void softDelete(String id);
}
