// Caminho: com.example.wine.data.datasource.region/RegionLocalDataSource.java
package com.example.wine.data.datasource.region; // Nova subpasta 'region'

import com.example.wine.data.local.dao.RegionDao;
import com.example.wine.data.local.entity.RegionEntity;
import com.example.wine.domain.model.Region;
import com.example.wine.utils.Mapper; // Será necessário estender Mapper para Region
import java.util.ArrayList;
import java.util.List;

public class RegionLocalDataSource {
    private final RegionDao regionDao;

    public RegionLocalDataSource(RegionDao regionDao) {
        this.regionDao = regionDao;
    }

    public List<Region> getAll() {
        List<RegionEntity> entities = regionDao.getAll();
        List<Region> regions = new ArrayList<>();
        for (RegionEntity entity : entities) {
            regions.add(Mapper.toModel(entity));
        }
        return regions;
    }

    public Region getById(String id) {
        RegionEntity entity = regionDao.getById(id);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    public void insert(Region region) {
        regionDao.insert(Mapper.toEntity(region));
    }

    public void update(Region region) {
        regionDao.update(Mapper.toEntity(region));
    }

    // Métodos de sincronização/soft delete não incluídos, pois a entidade Region
    // não possui os campos correspondentes na definição atual.
}