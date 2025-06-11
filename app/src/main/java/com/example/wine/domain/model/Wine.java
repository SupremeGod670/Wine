package com.example.wine.domain.model;

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
    public void setSynced(boolean synced) { this.isSynced = synced; }

    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}
