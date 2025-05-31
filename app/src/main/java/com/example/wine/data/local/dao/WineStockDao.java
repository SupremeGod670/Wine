// Caminho: com.example.wine.data.local.dao/WineStockDao.java
package com.example.wine.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.wine.data.local.entity.WineStockEntity;
import java.util.List;

@Dao
public interface WineStockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WineStockEntity wineStock);

    @Update
    void update(WineStockEntity wineStock);

    @Query("SELECT * FROM wine_stock WHERE id = :id")
    WineStockEntity getById(String id);

    @Query("SELECT * FROM wine_stock WHERE wine_id = :wineId AND representative_id = :representativeId")
    WineStockEntity getByWineAndRepresentative(String wineId, String representativeId); // Útil para buscar um estoque específico

    @Query("SELECT * FROM wine_stock WHERE wine_id = :wineId")
    List<WineStockEntity> getStockByWineId(String wineId); // Estoque total de um vinho entre representantes

    @Query("SELECT * FROM wine_stock WHERE representative_id = :representativeId")
    List<WineStockEntity> getStockByRepresentativeId(String representativeId); // Estoque de vinhos de um representante

    @Query("SELECT * FROM wine_stock")
    List<WineStockEntity> getAll();

    @Query("SELECT * FROM wine_stock WHERE is_synced = 0")
    List<WineStockEntity> getAllNotSynced();

    @Query("UPDATE wine_stock SET is_synced = :isSynced WHERE id = :id")
    void updateSyncStatus(String id, boolean isSynced);

    @Query("UPDATE wine_stock SET deleted = 1 WHERE id = :id")
    void softDelete(String id);
}