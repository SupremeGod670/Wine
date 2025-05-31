// Caminho: com.example.wine.data.local.dao/SaleItemDao.java
package com.example.wine.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.wine.data.local.entity.SaleItemEntity;
import java.util.List;

@Dao
public interface SaleItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SaleItemEntity saleItem);

    @Update
    void update(SaleItemEntity saleItem);

    @Query("SELECT * FROM sale_item WHERE id = :id")
    SaleItemEntity getById(String id);

    @Query("SELECT * FROM sale_item WHERE sale_id = :saleId")
    List<SaleItemEntity> getItemsBySaleId(String saleId); // Para buscar todos os itens de uma venda específica

    @Query("SELECT * FROM sale_item WHERE wine_id = :wineId")
    List<SaleItemEntity> getItemsByWineId(String wineId); // Pode ser útil para verificar onde um vinho foi vendido

    @Query("SELECT * FROM sale_item")
    List<SaleItemEntity> getAll();

    @Query("SELECT * FROM sale_item WHERE is_synced = 0")
    List<SaleItemEntity> getAllNotSynced();

    @Query("UPDATE sale_item SET is_synced = :isSynced WHERE id = :id")
    void updateSyncStatus(String id, boolean isSynced);

    @Query("UPDATE sale_item SET deleted = 1 WHERE id = :id")
    void softDelete(String id);
}