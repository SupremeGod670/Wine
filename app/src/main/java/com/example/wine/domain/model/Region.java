// Caminho: com.example.wine.domain.model/Region.java
package com.example.wine.domain.model;

public class Region {
    private String id; // Mapeando idregiao para 'id'
    private String description; // Mapeando descricao para 'description'

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