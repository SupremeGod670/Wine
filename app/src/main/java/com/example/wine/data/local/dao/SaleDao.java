// Caminho: com.example.wine.data.local.dao/SaleDao.java
package com.example.wine.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.wine.data.local.entity.SaleEntity;
import java.util.List;

@Dao
public interface SaleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SaleEntity sale);

    @Update
    void update(SaleEntity sale);

    @Query("SELECT * FROM sale WHERE id = :id")
    SaleEntity getById(String id);

    @Query("SELECT * FROM sale WHERE client_id = :clientId")
    List<SaleEntity> getSalesByClientId(String clientId); // Para buscar vendas de um cliente específico

    @Query("SELECT * FROM sale WHERE representative_id = :representativeId")
    List<SaleEntity> getSalesByRepresentativeId(String representativeId); // Para buscar vendas de um representante específico

    @Query("SELECT * FROM sale")
    List<SaleEntity> getAll();

    @Query("SELECT * FROM sale WHERE is_synced = 0")
    List<SaleEntity> getAllNotSynced();

    @Query("UPDATE sale SET is_synced = :isSynced WHERE id = :id")
    void updateSyncStatus(String id, boolean isSynced);

    @Query("UPDATE sale SET deleted = 1 WHERE id = :id")
    void softDelete(String id);
}