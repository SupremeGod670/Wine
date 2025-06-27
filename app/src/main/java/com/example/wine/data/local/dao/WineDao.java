package com.example.wine.data.local.dao;

import android.util.Log;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wine.data.local.entity.WineEntity;

import java.util.List;

@Dao
public interface WineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WineEntity wine);

    @Update
    void update(WineEntity wine);

    @Query("SELECT * FROM wine WHERE id = :id")
    WineEntity getById(String id);

    @Query("SELECT * FROM wine WHERE deleted = 0")
    List<WineEntity> getAll();
    default List<WineEntity> getAllWithLog() {
        List<WineEntity> result = getAll();
        Log.d("WineDebug", "WineDao: getAll() retornou " + (result != null ? result.size() : "null") + " entidades.");
        return result;
    }

    @Query("SELECT * FROM wine WHERE is_synced = 0")
    List<WineEntity> getAllNotSynced();

    @Query("UPDATE wine SET is_synced = :isSynced WHERE id = :id")
    void updateSyncStatus(String id, boolean isSynced);

    @Query("UPDATE wine SET deleted = 1 WHERE id = :id")
    void softDelete(String id);
}
