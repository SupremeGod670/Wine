package com.example.wine.domain.repository;

import com.example.wine.data.local.entity.WineryEntity;

import java.util.List;

public interface WineryRepository {
    void insertWinery(WineryEntity winery);
    void updateWinery(WineryEntity winery);
    void softDeleteWinery(String id);
    List<WineryEntity> getAllActiveWineries();
    WineryEntity getWineryById(String id);
}
