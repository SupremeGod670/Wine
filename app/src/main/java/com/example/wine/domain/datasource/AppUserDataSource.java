package com.example.wine.domain.datasource;

import com.example.wine.domain.model.AppUser;
import java.util.List;

public interface AppUserDataSource {

    // Métodos base
    List<AppUser> getAll();
    AppUser getById(String id);
    AppUser getByEmail(String email);
    void insert(AppUser user);
    void update(AppUser user);
    void softDelete(String id);
    List<AppUser> getAllNotSynced();
    void updateSyncStatus(String id, boolean isSynced);

    // Métodos adicionados
    List<AppUser> getAllAdmins();
    AppUser login(String email, String passwordHash);

}
