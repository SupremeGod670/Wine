package com.example.wine.data.datasource.user;

import com.example.wine.data.local.dao.AppUserDao;
import com.example.wine.data.local.entity.AppUserEntity;
import com.example.wine.domain.datasource.AppUserDataSource;
import com.example.wine.domain.model.AppUser;
import com.example.wine.utils.Mapper;

import java.util.ArrayList;
import java.util.List;

public class AppUserLocalDataSource implements AppUserDataSource {

    private final AppUserDao appUserDao;

    public AppUserLocalDataSource(AppUserDao appUserDao) {
        this.appUserDao = appUserDao;
    }

    // Implementações da interface AppUserDataSource

    @Override
    public void insert(AppUser user) {
        appUserDao.insert(Mapper.toEntity(user));
    }

    @Override
    public List<AppUser> getAllAdmins() {
        List<AppUserEntity> entities = appUserDao.getAllAdmins();
        List<AppUser> users = new ArrayList<>();
        for (AppUserEntity entity : entities) {
            users.add(Mapper.toModel(entity));
        }
        return users;
    }

    @Override
    public AppUser login(String email, String passwordHash) {
        AppUserEntity entity = appUserDao.login(email, passwordHash);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    // Métodos auxiliares adicionais

    public List<AppUser> getAll() {
        List<AppUserEntity> entities = appUserDao.getAll();
        List<AppUser> users = new ArrayList<>();
        for (AppUserEntity entity : entities) {
            users.add(Mapper.toModel(entity));
        }
        return users;
    }

    public AppUser getById(String id) {
        AppUserEntity entity = appUserDao.getById(id);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    public AppUser getByEmail(String email) {
        AppUserEntity entity = appUserDao.getByEmail(email);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    public void update(AppUser user) {
        appUserDao.update(Mapper.toEntity(user));
    }

    public void softDelete(String id) {
        appUserDao.softDelete(id);
    }

    public List<AppUser> getAllNotSynced() {
        List<AppUserEntity> entities = appUserDao.getAllNotSynced();
        List<AppUser> users = new ArrayList<>();
        for (AppUserEntity entity : entities) {
            users.add(Mapper.toModel(entity));
        }
        return users;
    }

    public void updateSyncStatus(String id, boolean isSynced) {
        appUserDao.updateSyncStatus(id, isSynced);
    }
}
