// Caminho: com.example.wine.data.local.dao/RepresentativeDao.java
package com.example.wine.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wine.data.local.entity.RepresentativeEntity;

import java.util.List;

@Dao
public interface RepresentativeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RepresentativeEntity representative);

    @Update
    void update(RepresentativeEntity representative);

    @Query("SELECT * FROM representative WHERE id = :id")
    RepresentativeEntity getById(String id);

    @Query("SELECT * FROM representative WHERE user_id = :userId")
    RepresentativeEntity getByUserId(String userId); // Novo método para buscar pelo ID do usuário

    @Query("SELECT * FROM representative")
    List<RepresentativeEntity> getAll();

    @Query("SELECT * FROM representative WHERE is_synced = 0")
    List<RepresentativeEntity> getAllNotSynced();

    @Query("UPDATE representative SET is_synced = :isSynced WHERE id = :id")
    void updateSyncStatus(String id, boolean isSynced);

    @Query("UPDATE representative SET deleted = 1 WHERE id = :id")
    void softDelete(String id);
}