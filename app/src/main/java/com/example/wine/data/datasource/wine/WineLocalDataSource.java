package com.example.wine.data.datasource.wine;

import com.example.wine.data.local.dao.WineDao;
import com.example.wine.data.local.entity.WineEntity;
import com.example.wine.domain.datasource.WineDataSource;
import com.example.wine.domain.model.Wine;
import com.example.wine.utils.Mapper;
import java.util.ArrayList;
import java.util.List;

public class WineLocalDataSource implements WineDataSource {
    private final WineDao wineDao;

    public WineLocalDataSource(WineDao wineDao) {
        this.wineDao = wineDao;
    }

    @Override
    public List<Wine> getAll() {
        List<WineEntity> entities = wineDao.getAll();
        List<Wine> wines = new ArrayList<>();
        for (WineEntity entity : entities) {
            wines.add(Mapper.toModel(entity));
        }
        return wines;
    }

    @Override
    public Wine getById(String id) {
        WineEntity entity = wineDao.getById(id);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    @Override
    public void insert(Wine wine) {
        wineDao.insert(Mapper.toEntity(wine));
    }

    @Override
    public void update(Wine wine) {
        wineDao.update(Mapper.toEntity(wine));
    }

    @Override
    public void softDelete(String id) {
        wineDao.softDelete(id);
    }

    public List<Wine> getAllNotSynced() {
        List<WineEntity> entities = wineDao.getAllNotSynced();
        List<Wine> wines = new ArrayList<>();
        for (WineEntity entity : entities) {
            wines.add(Mapper.toModel(entity));
        }
        return wines;
    }

    public void updateSyncStatus(String id, boolean isSynced) {
        wineDao.updateSyncStatus(id, isSynced);
    }
}
