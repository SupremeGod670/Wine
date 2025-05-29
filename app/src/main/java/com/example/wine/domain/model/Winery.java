package com.example.wine.domain.model;

import java.util.UUID;

public class Winery {
    private String id;
    private String name;
    private String country;
    private String region;
    private boolean isSynced;
    private boolean deleted;
    private long updatedAt;

    public Winery() {
        this.id = UUID.randomUUID().toString();
        this.isSynced = false;
        this.deleted = false;
        this.updatedAt = System.currentTimeMillis();
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public boolean isSynced() { return isSynced; }
    public void setSynced(boolean synced) { this.isSynced = synced; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}
