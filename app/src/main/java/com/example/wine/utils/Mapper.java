package com.example.wine.utils;

import com.example.wine.data.local.entity.WineEntity;
import com.example.wine.domain.model.Wine;
import com.example.wine.data.local.entity.WineryEntity;
import com.example.wine.domain.model.Winery;

public class Mapper {

    // Wine: Entity to Model
    public static Wine toModel(WineEntity entity) {
        Wine wine = new Wine();
        wine.setId(entity.id);
        wine.setName(entity.name);
        wine.setHarvest(entity.harvest);
        wine.setType(entity.type);
        wine.setNotes(entity.notes);
        wine.setPairing(entity.pairing);
        wine.setImageUrl(entity.imageUrl);
        return wine;
    }

    // Wine: Model to Entity
    public static WineEntity toEntity(Wine model) {
        WineEntity entity = new WineEntity();
        entity.id = model.getId();
        entity.name = model.getName();
        entity.harvest = model.getHarvest();
        entity.type = model.getType();
        entity.notes = model.getNotes();
        entity.pairing = model.getPairing();
        entity.imageUrl = model.getImageUrl();
        return entity;
    }

    // Winery: Model to Entity
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
