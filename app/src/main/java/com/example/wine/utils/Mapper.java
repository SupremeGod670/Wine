package com.example.wine.utils;

import com.example.wine.data.local.entity.WineEntity;
import com.example.wine.domain.model.Wine;
import com.example.wine.data.local.entity.WineryEntity;
import com.example.wine.domain.model.Winery;

public class Mapper {

    // Wine: Entity to Model
    public static Wine toModel(WineEntity entity) {
        Wine wine = new Wine();
        wine.setId(entity.getId());
        wine.setWineryId(entity.getWineryId());
        wine.setName(entity.getName());
        wine.setYear(entity.getYear());
        wine.setGrape(entity.getGrape());
        wine.setCategory(entity.getCategory());
        wine.setAlcoholPercentage(entity.getAlcoholPercentage());
        wine.setPrice(entity.getPrice());
        wine.setStock(entity.getStock());
        wine.setTastingNotes(entity.getTastingNotes());
        wine.setRating(entity.getRating());
        wine.setAgingTime(entity.getAgingTime());
        wine.setServingTemperature(entity.getServingTemperature());
        wine.setAcidity(entity.getAcidity());
        wine.setPairing(entity.getPairing());
        wine.setCommercialCategory(entity.getCommercialCategory());
        wine.setSynced(entity.isSynced());
        wine.setUpdatedAt(entity.getUpdatedAt());
        wine.setDeleted(entity.isDeleted());
        return wine;
    }

    // Wine: Model to Entity
    public static WineEntity toEntity(Wine model) {
        WineEntity entity = new WineEntity();
        entity.setId(model.getId());
        entity.setWineryId(model.getWineryId());
        entity.setName(model.getName());
        entity.setYear(model.getYear());
        entity.setGrape(model.getGrape());
        entity.setCategory(model.getCategory());
        entity.setAlcoholPercentage(model.getAlcoholPercentage());
        entity.setPrice(model.getPrice());
        entity.setStock(model.getStock());
        entity.setTastingNotes(model.getTastingNotes());
        entity.setRating(model.getRating());
        entity.setAgingTime(model.getAgingTime());
        entity.setServingTemperature(model.getServingTemperature());
        entity.setAcidity(model.getAcidity());
        entity.setPairing(model.getPairing());
        entity.setCommercialCategory(model.getCommercialCategory());
        entity.setSynced(model.isSynced());
        entity.setUpdatedAt(model.getUpdatedAt());
        entity.setDeleted(model.isDeleted());
        return entity;
    }

    // Winery: Entity to Model
    public static Winery toDomain(WineryEntity entity) {
        Winery winery = new Winery();
        winery.setId(entity.getId());
        winery.setName(entity.getName());
        winery.setCountry(entity.getCountry());
        winery.setRegion(entity.getRegion());
        winery.setSynced(entity.isSynced());
        winery.setDeleted(entity.isDeleted());
        winery.setUpdatedAt(entity.getUpdatedAt());
        return winery;
    }

    // Winery: Model to Entity
    public static WineryEntity toEntity(Winery winery) {
        WineryEntity entity = new WineryEntity();
        entity.setId(winery.getId());
        entity.setName(winery.getName());
        entity.setCountry(winery.getCountry());
        entity.setRegion(winery.getRegion());
        entity.setSynced(winery.isSynced());
        entity.setDeleted(winery.isDeleted());
        entity.setUpdatedAt(winery.getUpdatedAt());
        return entity;
    }
}
