// Caminho: com.example.wine.data.datasource.winestock/WineStockLocalDataSource.java
package com.example.wine.data.datasource.winestock; // Nova subpasta 'winestock'

import com.example.wine.data.local.dao.WineStockDao;
import com.example.wine.data.local.entity.WineStockEntity;
import com.example.wine.domain.model.WineStock;
import com.example.wine.utils.Mapper; // Será necessário estender Mapper para WineStock
import java.util.ArrayList;
import java.util.List;

public class WineStockLocalDataSource {
    private final WineStockDao wineStockDao;

    public WineStockLocalDataSource(WineStockDao wineStockDao) {
        this.wineStockDao = wineStockDao;
    }

    public List<WineStock> getAll() {
        List<WineStockEntity> entities = wineStockDao.getAll();
        List<WineStock> wineStocks = new ArrayList<>();
        for (WineStockEntity entity : entities) {
            wineStocks.add(Mapper.toModel(entity));
        }
        return wineStocks;
    }

    public WineStock getById(String id) {
        WineStockEntity entity = wineStockDao.getById(id);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    public WineStock getByWineAndRepresentative(String wineId, String representativeId) {
        WineStockEntity entity = wineStockDao.getByWineAndRepresentative(wineId, representativeId);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    public List<WineStock> getStockByWineId(String wineId) {
        List<WineStockEntity> entities = wineStockDao.getStockByWineId(wineId);
        List<WineStock> wineStocks = new ArrayList<>();
        for (WineStockEntity entity : entities) {
            wineStocks.add(Mapper.toModel(entity));
        }
        return wineStocks;
    }

    public List<WineStock> getStockByRepresentativeId(String representativeId) {
        List<WineStockEntity> entities = wineStockDao.getStockByRepresentativeId(representativeId);
        List<WineStock> wineStocks = new ArrayList<>();
        for (WineStockEntity entity : entities) {
            wineStocks.add(Mapper.toModel(entity));
        }
        return wineStocks;
    }

    public void insert(WineStock wineStock) {
        wineStockDao.insert(Mapper.toEntity(wineStock));
    }

    public void update(WineStock wineStock) {
        wineStockDao.update(Mapper.toEntity(wineStock));
    }

    public void softDelete(String id) {
        wineStockDao.softDelete(id);
    }

    public List<WineStock> getAllNotSynced() {
        List<WineStockEntity> entities = wineStockDao.getAllNotSynced();
        List<WineStock> wineStocks = new ArrayList<>();
        for (WineStockEntity entity : entities) {
            wineStocks.add(Mapper.toModel(entity));
        }
        return wineStocks;
    }

    public void updateSyncStatus(String id, boolean isSynced) {
        wineStockDao.updateSyncStatus(id, isSynced);
    }
}