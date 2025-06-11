// Caminho: com.example.wine.data.local.entity/RegionEntity.java
package com.example.wine.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "region")
public class RegionEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idregiao") // Mapeando idregiao como PK
    private String id;

    @ColumnInfo(name = "descricao") // Mapeando descricao
    private String description;

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}