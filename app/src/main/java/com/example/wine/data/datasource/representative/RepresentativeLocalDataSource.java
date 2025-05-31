// Caminho: com.example.wine.data.datasource.representative/RepresentativeLocalDataSource.java
package com.example.wine.data.datasource.representative; // Nova subpasta 'representative'

import com.example.wine.data.local.dao.RepresentativeDao;
import com.example.wine.data.local.entity.RepresentativeEntity;
import com.example.wine.domain.model.Representative;
import com.example.wine.utils.Mapper; // Será necessário estender Mapper para Representative
import java.util.ArrayList;
import java.util.List;

public class RepresentativeLocalDataSource {
    private final RepresentativeDao representativeDao;

    public RepresentativeLocalDataSource(RepresentativeDao representativeDao) {
        this.representativeDao = representativeDao;
    }

    public List<Representative> getAll() {
        List<RepresentativeEntity> entities = representativeDao.getAll();
        List<Representative> representatives = new ArrayList<>();
        for (RepresentativeEntity entity : entities) {
            representatives.add(Mapper.toModel(entity));
        }
        return representatives;
    }

    public Representative getById(String id) {
        RepresentativeEntity entity = representativeDao.getById(id);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    public Representative getByUserId(String userId) {
        RepresentativeEntity entity = representativeDao.getByUserId(userId);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    public void insert(Representative representative) {
        representativeDao.insert(Mapper.toEntity(representative));
    }

    public void update(Representative representative) {
        representativeDao.update(Mapper.toEntity(representative));
    }

    public void softDelete(String id) {
        representativeDao.softDelete(id);
    }

    public List<Representative> getAllNotSynced() {
        List<RepresentativeEntity> entities = representativeDao.getAllNotSynced();
        List<Representative> representatives = new ArrayList<>();
        for (RepresentativeEntity entity : entities) {
            representatives.add(Mapper.toModel(entity));
        }
        return representatives;
    }

    public void updateSyncStatus(String id, boolean isSynced) {
        representativeDao.updateSyncStatus(id, isSynced);
    }
}