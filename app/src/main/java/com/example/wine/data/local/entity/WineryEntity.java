package com.example.wine.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "winery")
public class WineryEntity {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String country;
    private String region;
    private boolean isSynced;
    private boolean deleted;
    private long updatedAt;

    public WineryEntity() {
        this.id = UUID.randomUUID().toString();
        this.isSynced = false;
        this.deleted = false;
        this.updatedAt = System.currentTimeMillis();
    }

    // Getters e Setters
    @NonNull
    public String getId() { return id; }
    public void setId(@NonNull String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public boolean isSynced() { return isSynced; }
    public void setSynced(boolean synced) { isSynced = synced; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}
