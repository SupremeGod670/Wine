package com.example.wine.utils;
import com.example.wine.data.local.entity.WineEntity;
import com.example.wine.domain.model.Wine;

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
}