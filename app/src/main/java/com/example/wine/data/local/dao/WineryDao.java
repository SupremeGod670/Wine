package com.example.wine.data.local.dao;

import androidx.room.*;
import com.example.wine.data.local.entity.WineryEntity;
import java.util.List;

@Dao
public interface WineryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WineryEntity winery);

    @Update
    void update(WineryEntity winery);

    @Query("UPDATE winery SET is_synced = :isSynced WHERE id = :id")
    void updateSyncStatus(String id, boolean isSynced);

    @Query("UPDATE winery SET deleted = 1 WHERE id = :id")
    void softDelete(String id);

    @Query("SELECT * FROM winery WHERE is_synced = 0")
    List<WineryEntity> getAllNotSynced();

    @Query("SELECT * FROM winery")
    List<WineryEntity> getAll();

    @Query("SELECT * FROM winery WHERE id = :id")
    WineryEntity getById(String id);
}
