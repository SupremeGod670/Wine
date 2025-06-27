package com.example.wine.data.datasource.user;

import com.example.wine.data.local.dao.AppUserDao;
import com.example.wine.data.local.entity.AppUserEntity;
import com.example.wine.domain.datasource.AppUserDataSource;
import com.example.wine.domain.model.AppUser;
import com.example.wine.utils.Mapper;

import java.util.ArrayList;
import java.util.List;

public class AppUserLocalDataSource implements AppUserDataSource { // << AGORA IMPLEMENTA A INTERFACE

    private final AppUserDao appUserDao;

    public AppUserLocalDataSource(AppUserDao appUserDao) {
        this.appUserDao = appUserDao;
    }

    // --- Implementações dos métodos da interface AppUserDataSource ---

    @Override
    public List<AppUser> getAll() { // Método 'getAll' agora é @Override
        List<AppUserEntity> entities = appUserDao.getAll();
        List<AppUser> users = new ArrayList<>();
        for (AppUserEntity entity : entities) {
            users.add(Mapper.toModel(entity));
        }
        return users;
    }

    @Override
    public AppUser getById(String id) { // Método 'getById' agora é @Override
        AppUserEntity entity = appUserDao.getById(id);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    @Override
    public AppUser getByEmail(String email) { // Método 'getByEmail' agora é @Override
        AppUserEntity entity = appUserDao.getByEmail(email);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    @Override
    public void insert(AppUser user) { // Método 'insert' agora é @Override
        appUserDao.insert(Mapper.toEntity(user));
    }

    @Override
    public void update(AppUser user) { // Método 'update' agora é @Override
        appUserDao.update(Mapper.toEntity(user));
    }

    @Override
    public void softDelete(String id) { // Método 'softDelete' agora é @Override
        appUserDao.softDelete(id);
    }

    @Override
    public AppUser login(String email, String passwordHash) { // Método 'login' agora é @Override
        AppUserEntity entity = appUserDao.login(email, passwordHash);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    @Override
    public List<AppUser> getAllAdmins() { // Método 'getAllAdmins' agora é @Override
        List<AppUserEntity> entities = appUserDao.getAllAdmins();
        List<AppUser> users = new ArrayList<>();
        for (AppUserEntity entity : entities) {
            users.add(Mapper.toModel(entity));
        }
        return users;
    }

    @Override
    public List<AppUser> getAllNotSynced() { // Método 'getAllNotSynced' agora é @Override
        List<AppUserEntity> entities = appUserDao.getAllNotSynced();
        List<AppUser> users = new ArrayList<>();
        for (AppUserEntity entity : entities) {
            users.add(Mapper.toModel(entity));
        }
        return users;
    }

    @Override
    public void updateSyncStatus(String id, boolean isSynced) { // Método 'updateSyncStatus' agora é @Override
        appUserDao.updateSyncStatus(id, isSynced);
    }
}