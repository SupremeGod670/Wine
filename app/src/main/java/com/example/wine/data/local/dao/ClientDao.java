// Caminho: com.example.wine.data.local.dao/ClientDao.java
package com.example.wine.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.wine.data.local.entity.ClientEntity;
import java.util.List;

@Dao
public interface ClientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ClientEntity client);

    @Update
    void update(ClientEntity client);

    @Query("SELECT * FROM client WHERE id = :id")
    ClientEntity getById(String id);

    @Query("SELECT * FROM client WHERE email = :email")
    ClientEntity getByEmail(String email);

    @Query("SELECT * FROM client WHERE id_regiao = :regionId") // NOVO: Buscar clientes por região
    List<ClientEntity> getClientsByRegionId(String regionId);

    @Query("SELECT * FROM client")
    List<ClientEntity> getAll();

    @Query("SELECT * FROM client WHERE is_approved = 1")
    List<ClientEntity> getAllApprovedClients();

    @Query("SELECT * FROM client WHERE approved_by = :userId")
    List<ClientEntity> getClientsApprovedBy(String userId);

    @Query("SELECT * FROM client WHERE is_synced = 0")
    List<ClientEntity> getAllNotSynced();

    @Query("UPDATE client SET is_synced = :isSynced WHERE id = :id")
    void updateSyncStatus(String id, boolean isSynced);

    @Query("UPDATE client SET deleted = 1 WHERE id = :id")
    void softDelete(String id);
    @Query("SELECT * FROM client WHERE is_approved = 0")
    List<ClientEntity> getAllNotApprovedClients();
}