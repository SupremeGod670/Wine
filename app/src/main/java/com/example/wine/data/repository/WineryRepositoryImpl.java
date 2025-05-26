package com.example.wine.data.repository;

import com.example.wine.data.local.dao.WineryDao;
import com.example.wine.data.local.entity.WineryEntity;
import com.example.wine.domain.repository.WineryRepository;

import java.util.List;

public class WineryRepositoryImpl implements WineryRepository {
    private final WineryDao wineryDao;

    public WineryRepositoryImpl(WineryDao wineryDao) {
        this.wineryDao = wineryDao;
    }

    @Override
    public void insertWinery(WineryEntity winery) {
        wineryDao.insertWinery(winery);
    }

    @Override
    public void updateWinery(WineryEntity winery) {
        winery.setUpdatedAt(System.currentTimeMillis());
        winery.setSynced(false);
        wineryDao.updateWinery(winery);
    }

    @Override
    public void softDeleteWinery(String id) {
        wineryDao.softDeleteWinery(id, System.currentTimeMillis());
    }

    @Override
    public List<WineryEntity> getAllActiveWineries() {
        return wineryDao.getAllActiveWineries();
    }

    @Override
    public WineryEntity getWineryById(String id) {
        return wineryDao.getWineryById(id);
    }
}
