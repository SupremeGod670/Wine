package com.example.wine.domain.model;

import java.util.UUID;

public class Wine {
    private String id;
    private String wineryId;
    private String name;
    private int year;
    private String grape;
    private String category;
    private double alcoholPercentage;
    private double price;
    private int stock;
    private String tastingNotes;
    private double rating;
    private String agingTime;
    private String servingTemperature;
    private String acidity;
    private String pairing;
    private String commercialCategory;
    private boolean isSynced;
    private long updatedAt;
    private boolean deleted;

    // Construtor completo: para quando você já tem um ID (ex: carregando do DB para atualizar)
    public Wine(String id, String wineryId, String name, int year, String grape, String category,
                double alcoholPercentage, double price, int stock, String tastingNotes,
                double rating, String agingTime, String servingTemperature, String acidity,
                String pairing, String commercialCategory, boolean isSynced, long updatedAt,
                boolean deleted) {
        this.id = id != null ? id : UUID.randomUUID().toString(); // Garante que um ID é gerado se o modelo não tiver um
        this.wineryId = wineryId;
        this.name = name;
        this.year = year;
        this.grape = grape;
        this.category = category;
        this.alcoholPercentage = alcoholPercentage;
        this.price = price;
        this.stock = stock;
        this.tastingNotes = tastingNotes;
        this.rating = rating;
        this.agingTime = agingTime;
        this.servingTemperature = servingTemperature;
        this.acidity = acidity;
        this.pairing = pairing;
        this.commercialCategory = commercialCategory;
        this.isSynced = isSynced;
        this.updatedAt = updatedAt;
        this.deleted = deleted;
    }

    // Construtor para criação de um novo vinho: ID, isSynced e updatedAt serão gerados/preenchidos automaticamente
    public Wine(String wineryId, String name, int year, String grape, String category,
                double alcoholPercentage, double price, int stock, String tastingNotes,
                double rating, String agingTime, String servingTemperature, String acidity,
                String pairing, String commercialCategory) {
        this(null, wineryId, name, year, grape, category, alcoholPercentage, price, stock,
                tastingNotes, rating, agingTime, servingTemperature, acidity, pairing,
                commercialCategory, false, System.currentTimeMillis(), false);
    }

    public Wine() {

    }

    // Getters (já estão no seu arquivo)
    public String getId() { return id; }
    public String getWineryId() { return wineryId; }
    public String getName() { return name; }
    public int getYear() { return year; }
    public String getGrape() { return grape; }
    public String getCategory() { return category; }
    public double getAlcoholPercentage() { return alcoholPercentage; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public String getTastingNotes() { return tastingNotes; }
    public double getRating() { return rating; }
    public String getAgingTime() { return agingTime; }
    public String getServingTemperature() { return servingTemperature; }
    public String getAcidity() { return acidity; }
    public String getPairing() { return pairing; }
    public String getCommercialCategory() { return commercialCategory; }
    public boolean isSynced() { return isSynced; }
    public long getUpdatedAt() { return updatedAt; }
    public boolean isDeleted() { return deleted; }

    // Setters (já estão no seu arquivo)
    public void setId(String id) { this.id = id; }
    public void setWineryId(String wineryId) { this.wineryId = wineryId; }
    public void setName(String name) { this.name = name; }
    public void setYear(int year) { this.year = year; }
    public void setGrape(String grape) { this.grape = grape; }
    public void setCategory(String category) { this.category = category; }
    public void setAlcoholPercentage(double alcoholPercentage) { this.alcoholPercentage = alcoholPercentage; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
    public void setTastingNotes(String tastingNotes) { this.tastingNotes = tastingNotes; }
    public void setRating(double rating) { this.rating = rating; }
    public void setAgingTime(String agingTime) { this.agingTime = agingTime; }
    public void setServingTemperature(String servingTemperature) { this.servingTemperature = servingTemperature; }
    public void setAcidity(String acidity) { this.acidity = acidity; }
    public void setPairing(String pairing) { this.pairing = pairing; }
    public void setCommercialCategory(String commercialCategory) { this.commercialCategory = commercialCategory; }
    public void setSynced(boolean synced) { isSynced = synced; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}