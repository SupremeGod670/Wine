// Caminho: com.example.wine.data.local.dao/RegionDao.java
package com.example.wine.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.wine.data.local.entity.RegionEntity;
import java.util.List;

@Dao
public interface RegionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RegionEntity region);

    @Update
    void update(RegionEntity region);

    @Query("SELECT * FROM region WHERE idregiao = :id")
    RegionEntity getById(String id);

    @Query("SELECT * FROM region")
    List<RegionEntity> getAll();

    // Como Region não possui campos 'is_synced' ou 'deleted' na definição,
    // não incluiremos métodos relacionados a sincronização/soft delete por enquanto.
    // Se precisar, me avise!
}