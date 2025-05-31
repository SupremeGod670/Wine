// Caminho: com.example.wine.data.datasource.sale/SaleLocalDataSource.java
package com.example.wine.data.datasource.sale; // Nova subpasta 'sale'

import com.example.wine.data.local.dao.SaleDao;
import com.example.wine.data.local.entity.SaleEntity;
import com.example.wine.domain.model.Sale;
import com.example.wine.utils.Mapper; // Será necessário estender Mapper para Sale
import java.util.ArrayList;
import java.util.List;

public class SaleLocalDataSource {
    private final SaleDao saleDao;

    public SaleLocalDataSource(SaleDao saleDao) {
        this.saleDao = saleDao;
    }

    public List<Sale> getAll() {
        List<SaleEntity> entities = saleDao.getAll();
        List<Sale> sales = new ArrayList<>();
        for (SaleEntity entity : entities) {
            sales.add(Mapper.toModel(entity));
        }
        return sales;
    }

    public Sale getById(String id) {
        SaleEntity entity = saleDao.getById(id);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    public List<Sale> getSalesByClientId(String clientId) {
        List<SaleEntity> entities = saleDao.getSalesByClientId(clientId);
        List<Sale> sales = new ArrayList<>();
        for (SaleEntity entity : entities) {
            sales.add(Mapper.toModel(entity));
        }
        return sales;
    }

    public List<Sale> getSalesByRepresentativeId(String representativeId) {
        List<SaleEntity> entities = saleDao.getSalesByRepresentativeId(representativeId);
        List<Sale> sales = new ArrayList<>();
        for (SaleEntity entity : entities) {
            sales.add(Mapper.toModel(entity));
        }
        return sales;
    }

    public void insert(Sale sale) {
        saleDao.insert(Mapper.toEntity(sale));
    }

    public void update(Sale sale) {
        saleDao.update(Mapper.toEntity(sale));
    }

    public void softDelete(String id) {
        saleDao.softDelete(id);
    }

    public List<Sale> getAllNotSynced() {
        List<SaleEntity> entities = saleDao.getAllNotSynced();
        List<Sale> sales = new ArrayList<>();
        for (SaleEntity entity : entities) {
            sales.add(Mapper.toModel(entity));
        }
        return sales;
    }

    public void updateSyncStatus(String id, boolean isSynced) {
        saleDao.updateSyncStatus(id, isSynced);
    }
}