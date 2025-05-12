package com.example.wine.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wine")
public class WineEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String harvest;
    public String type;
    public String notes;
    public String pairing;
    public String imageUrl;
}