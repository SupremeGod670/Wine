// Caminho: com.example.wine.data.local.dao/AppUserDao.java
package com.example.wine.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.wine.data.local.entity.AppUserEntity;
import java.util.List;

@Dao
public interface AppUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AppUserEntity user);

    @Update
    void update(AppUserEntity user);

    @Query("SELECT * FROM app_user WHERE id = :id")
    AppUserEntity getById(String id);

    @Query("SELECT * FROM app_user WHERE email = :email")
    AppUserEntity getByEmail(String email);

    @Query("SELECT * FROM app_user")
    List<AppUserEntity> getAll();

    @Query("SELECT * FROM app_user WHERE is_synced = 0")
    List<AppUserEntity> getAllNotSynced();

    @Query("UPDATE app_user SET is_synced = :isSynced WHERE id = :id")
    void updateSyncStatus(String id, boolean isSynced);

    @Query("UPDATE app_user SET deleted = 1 WHERE id = :id")
    void softDelete(String id);
}