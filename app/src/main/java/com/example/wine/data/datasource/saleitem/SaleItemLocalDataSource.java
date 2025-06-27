// Caminho: com.example.wine.data.datasource.saleitem/SaleItemLocalDataSource.java
package com.example.wine.data.datasource.saleitem; // Nova subpasta 'saleitem'

import com.example.wine.data.local.dao.SaleItemDao;
import com.example.wine.data.local.entity.SaleItemEntity;
import com.example.wine.domain.model.SaleItem;
import com.example.wine.utils.Mapper;

import java.util.ArrayList;
import java.util.List;

public class SaleItemLocalDataSource {
    private final SaleItemDao saleItemDao;

    public SaleItemLocalDataSource(SaleItemDao saleItemDao) {
        this.saleItemDao = saleItemDao;
    }

    public List<SaleItem> getAll() {
        List<SaleItemEntity> entities = saleItemDao.getAll();
        List<SaleItem> saleItems = new ArrayList<>();
        for (SaleItemEntity entity : entities) {
            saleItems.add(Mapper.toModel(entity));
        }
        return saleItems;
    }

    public SaleItem getById(String id) {
        SaleItemEntity entity = saleItemDao.getById(id);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    public List<SaleItem> getItemsBySaleId(String saleId) {
        List<SaleItemEntity> entities = saleItemDao.getItemsBySaleId(saleId);
        List<SaleItem> saleItems = new ArrayList<>();
        for (SaleItemEntity entity : entities) {
            saleItems.add(Mapper.toModel(entity));
        }
        return saleItems;
    }

    public List<SaleItem> getItemsByWineId(String wineId) {
        List<SaleItemEntity> entities = saleItemDao.getItemsByWineId(wineId);
        List<SaleItem> saleItems = new ArrayList<>();
        for (SaleItemEntity entity : entities) {
            saleItems.add(Mapper.toModel(entity));
        }
        return saleItems;
    }

    public void insert(SaleItem saleItem) {
        saleItemDao.insert(Mapper.toEntity(saleItem));
    }

    public void update(SaleItem saleItem) {
        saleItemDao.update(Mapper.toEntity(saleItem));
    }

    public void softDelete(String id) {
        saleItemDao.softDelete(id);
    }

    public List<SaleItem> getAllNotSynced() {
        List<SaleItemEntity> entities = saleItemDao.getAllNotSynced();
        List<SaleItem> saleItems = new ArrayList<>();
        for (SaleItemEntity entity : entities) {
            saleItems.add(Mapper.toModel(entity));
        }
        return saleItems;
    }

    public void updateSyncStatus(String id, boolean isSynced) {
        saleItemDao.updateSyncStatus(id, isSynced);
    }
}