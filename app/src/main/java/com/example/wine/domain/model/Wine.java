package com.example.wine.domain.model;

public class Wine {
    private int id;
    private String name;
    private String harvest;
    private String type;
    private String notes;
    private String pairing;
    private String imageUrl;
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
