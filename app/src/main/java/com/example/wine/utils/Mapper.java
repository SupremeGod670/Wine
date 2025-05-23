package com.example.wine.utils;

import com.example.wine.data.local.entity.WineEntity;
import com.example.wine.domain.model.Wine;
import com.example.wine.data.local.entity.WineryEntity;
import com.example.wine.domain.model.Winery;

public class Mapper {

    public static Wine toModel(WineEntity entity) {
        Wine wine = new Wine();
        wine.setId(entity.getId());
        wine.setName(entity.getName());
        wine.setHarvest(entity.getHarvest());
        wine.setType(entity.getType());
        wine.setNotes(entity.getNotes());
        wine.setPairing(entity.getPairing());
        wine.setImageUrl(entity.getImageUrl());
        return wine;
    }

    public static WineEntity toEntity(Wine model) {
        WineEntity entity = new WineEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setHarvest(model.getHarvest());
        entity.setType(model.getType());
        entity.setNotes(model.getNotes());
        entity.setPairing(model.getPairing());
        entity.setImageUrl(model.getImageUrl());
        return entity;
    }

    public static WineryEntity toEntity(Winery winery) {
        WineryEntity entity = new WineryEntity(
                winery.getName(),
                winery.getAddress(),
                winery.getCity(),
                winery.getCountry(),
                winery.getLatitude(),
                winery.getLongitude()
        );
        entity.setId(winery.getId());
        return entity;
    }

    public static Winery toDomain(WineryEntity entity) {
        return new Winery(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getCity(),
                entity.getCountry(),
                entity.getLatitude(),
                entity.getLongitude()
        );
    }
}
