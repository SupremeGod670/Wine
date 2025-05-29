package com.example.wine.data.datasource;

import com.example.wine.data.local.dao.WineryDao;
import com.example.wine.data.local.entity.WineryEntity;
import com.example.wine.domain.model.Winery;
import com.example.wine.utils.Mapper;
import java.util.ArrayList;
import java.util.List;

public class WineryLocalDataSource {
    private final WineryDao wineryDao;

    public WineryLocalDataSource(WineryDao wineryDao) {
        this.wineryDao = wineryDao;
    }

    public void insert(Winery winery) {
        wineryDao.insert(Mapper.toEntity(winery));
    }

    public void update(Winery winery) {
        wineryDao.update(Mapper.toEntity(winery));
    }

    public void updateSyncStatus(String id, boolean isSynced) {
        wineryDao.updateSyncStatus(id, isSynced);
    }

    public void softDelete(String id) {
        wineryDao.softDelete(id);
    }

    public List<Winery> getAll() {
        List<WineryEntity> entities = wineryDao.getAll();
        List<Winery> wineries = new ArrayList<>();
        for (WineryEntity entity : entities) {
            wineries.add(Mapper.toDomain(entity));
        }
        return wineries;
    }

    public Winery getById(String id) {
        WineryEntity entity = wineryDao.getById(id);
        return entity != null ? Mapper.toDomain(entity) : null;
    }

    public List<Winery> getAllNotSynced() {
        List<WineryEntity> entities = wineryDao.getAllNotSynced();
        List<Winery> wineries = new ArrayList<>();
        for (WineryEntity entity : entities) {
            wineries.add(Mapper.toDomain(entity));
        }
        return wineries;
    }
}
