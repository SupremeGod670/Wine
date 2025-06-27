package com.example.wine.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "wine")
public class WineEntity {
    @PrimaryKey
    @NonNull
    private String id = UUID.randomUUID().toString();

    @ColumnInfo(name = "winery_id")
    private String wineryId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "grape")
    private String grape;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "alcohol_percentage")
    private double alcoholPercentage;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "stock")
    private int stock;

    @ColumnInfo(name = "tasting_notes")
    private String tastingNotes;

    @ColumnInfo(name = "rating")
    private double rating;

    @ColumnInfo(name = "aging_time")
    private String agingTime;

    @ColumnInfo(name = "serving_temperature")
    private String servingTemperature;

    @ColumnInfo(name = "acidity")
    private String acidity;

    @ColumnInfo(name = "pairing")
    private String pairing;

    @ColumnInfo(name = "commercial_category")
    private String commercialCategory;

    @ColumnInfo(name = "is_synced")
    private boolean isSynced = false;

    @ColumnInfo(name = "updated_at")
    private long updatedAt = System.currentTimeMillis();

    @ColumnInfo(name = "deleted")
    private boolean deleted = false;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getWineryId() { return wineryId; }
    public void setWineryId(String wineryId) { this.wineryId = wineryId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getGrape() { return grape; }
    public void setGrape(String grape) { this.grape = grape; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getAlcoholPercentage() { return alcoholPercentage; }
    public void setAlcoholPercentage(double alcoholPercentage) { this.alcoholPercentage = alcoholPercentage; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getTastingNotes() { return tastingNotes; }
    public void setTastingNotes(String tastingNotes) { this.tastingNotes = tastingNotes; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getAgingTime() { return agingTime; }
    public void setAgingTime(String agingTime) { this.agingTime = agingTime; }

    public String getServingTemperature() { return servingTemperature; }
    public void setServingTemperature(String servingTemperature) { this.servingTemperature = servingTemperature; }

    public String getAcidity() { return acidity; }
    public void setAcidity(String acidity) { this.acidity = acidity; }

    public String getPairing() { return pairing; }
    public void setPairing(String pairing) { this.pairing = pairing; }

    public String getCommercialCategory() { return commercialCategory; }
    public void setCommercialCategory(String commercialCategory) { this.commercialCategory = commercialCategory; }

    public boolean isSynced() { return isSynced; }
    public void setSynced(boolean synced) { isSynced = synced; }

    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}
