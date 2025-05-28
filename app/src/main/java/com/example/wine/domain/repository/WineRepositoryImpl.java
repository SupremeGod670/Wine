package com.example.wine.domain.repository;

import com.example.wine.domain.datasource.WineDataSource;
import com.example.wine.domain.model.Wine;
import java.util.List;

public class WineRepositoryImpl implements WineRepository {
    private final WineDataSource wineDataSource;

    public WineRepositoryImpl(WineDataSource wineDataSource) {
        this.wineDataSource = wineDataSource;
    }

    @Override
    public List<Wine> getAll() {
        return wineDataSource.getAll();
    }

    @Override
    public Wine getById(String id) {
        return wineDataSource.getById(id);
    }

    @Override
    public void insert(Wine wine) {
        wineDataSource.insert(wine);
    }

    @Override
    public void update(Wine wine) {
        wineDataSource.update(wine);
    }

    @Override
    public void softDelete(String id) {
        wineDataSource.softDelete(id);
    }
}
