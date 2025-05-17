package com.example.wine.utils;
import com.example.wine.data.local.entity.WineEntity;
import com.example.wine.domain.model.Wine;
import com.example.wine.data.local.entity.WineryEntity;
import com.example.wine.domain.model.Winery;

public class Mapper {

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