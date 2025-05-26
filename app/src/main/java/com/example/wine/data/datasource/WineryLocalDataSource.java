package com.example.wine.data.datasource;

import com.example.wine.domain.datasource.WineryDataSource;
import com.example.wine.domain.model.Winery;
import com.example.wine.data.local.dao.WineryDao;
import com.example.wine.data.local.entity.WineryEntity;
import com.example.wine.utils.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class WineryLocalDataSource implements WineryDataSource {
    private final WineryDao wineryDao;

    public WineryLocalDataSource(WineryDao wineryDao) {
        this.wineryDao = wineryDao;
    }

    @Override
    public void insertWinery(Winery winery, Callback callback) {
        try {
            WineryEntity entity = Mapper.toEntity(winery);
            wineryDao.insertWinery(entity);
            callback.onSuccess();
        } catch (Exception e) {
            callback.onError(e);
        }
    }

    @Override
    public void updateWinery(Winery winery, Callback callback) {
        try {
            WineryEntity entity = Mapper.toEntity(winery);
            wineryDao.updateWinery(entity);
            callback.onSuccess();
        } catch (Exception e) {
            callback.onError(e);
        }
    }

    @Override
    public void deleteWinery(String id, Callback callback) {
        try {
            wineryDao.softDeleteWinery(id, System.currentTimeMillis());
            callback.onSuccess();
        } catch (Exception e) {
            callback.onError(e);
        }
    }

    @Override
    public void getWineryById(String id, GetWineryCallback callback) {
        try {
            WineryEntity entity = wineryDao.getWineryById(id);
            callback.onSuccess(Mapper.toDomain(entity));
        } catch (Exception e) {
            callback.onError(e);
        }
    }

    @Override
    public void getAllWineries(GetAllWineriesCallback callback) {
        try {
            List<Winery> list = wineryDao.getAllActiveWineries().stream()
                    .map(Mapper::toDomain)
                    .collect(Collectors.toList());
            callback.onSuccess(list);
        } catch (Exception e) {
            callback.onError(e);
        }
    }
}
