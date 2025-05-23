package com.example.wine.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wine")
public class WineEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "harvest")
    private String harvest;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "pairing")
    private String pairing;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    // Construtor vazio necessário para o Room
    public WineEntity() { }

    // Construtor com todos os campos, exceto o ID (auto-gerado)
    public WineEntity(String name, String harvest, String type, String notes, String pairing, String imageUrl) {
        this.name = name;
        this.harvest = harvest;
        this.type = type;
        this.notes = notes;
        this.pairing = pairing;
        this.imageUrl = imageUrl;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getHarvest() { return harvest; }
    public void setHarvest(String harvest) { this.harvest = harvest; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getPairing() { return pairing; }
    public void setPairing(String pairing) { this.pairing = pairing; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
