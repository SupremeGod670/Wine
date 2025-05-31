// Caminho: com.example.wine.data.datasource.user/AppUserLocalDataSource.java
package com.example.wine.data.datasource.user; // Note a nova subpasta 'user'

import com.example.wine.data.local.dao.AppUserDao;
import com.example.wine.data.local.entity.AppUserEntity;
import com.example.wine.domain.model.AppUser;
import com.example.wine.utils.Mapper; // Será necessário estender Mapper para AppUser
import java.util.ArrayList;
import java.util.List;

// Embora não tenhamos uma interface AppUserDataSource definida,
// esta classe segue o padrão de WineLocalDataSource.
public class AppUserLocalDataSource {
    private final AppUserDao appUserDao;

    public AppUserLocalDataSource(AppUserDao appUserDao) {
        this.appUserDao = appUserDao;
    }

    public List<AppUser> getAll() {
        List<AppUserEntity> entities = appUserDao.getAll();
        List<AppUser> users = new ArrayList<>();
        for (AppUserEntity entity : entities) {
            // Assumindo que Mapper terá um método toModel(AppUserEntity)
            users.add(Mapper.toModel(entity));
        }
        return users;
    }

    public AppUser getById(String id) {
        AppUserEntity entity = appUserDao.getById(id);
        // Assumindo que Mapper terá um método toModel(AppUserEntity)
        return entity != null ? Mapper.toModel(entity) : null;
    }

    public AppUser getByEmail(String email) {
        AppUserEntity entity = appUserDao.getByEmail(email);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    public void insert(AppUser user) {
        // Assumindo que Mapper terá um método toEntity(AppUser)
        appUserDao.insert(Mapper.toEntity(user));
    }

    public void update(AppUser user) {
        // Assumindo que Mapper terá um método toEntity(AppUser)
        appUserDao.update(Mapper.toEntity(user));
    }

    public void softDelete(String id) {
        appUserDao.softDelete(id);
    }

    public List<AppUser> getAllNotSynced() {
        List<AppUserEntity> entities = appUserDao.getAllNotSynced();
        List<AppUser> users = new ArrayList<>();
        for (AppUserEntity entity : entities) {
            // Assumindo que Mapper terá um método toModel(AppUserEntity)
            users.add(Mapper.toModel(entity));
        }
        return users;
    }

    public void updateSyncStatus(String id, boolean isSynced) {
        appUserDao.updateSyncStatus(id, isSynced);
    }
}