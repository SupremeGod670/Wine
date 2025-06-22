// Caminho: com.example.wine.ui.model/ClientSpinnerModel.java
package com.example.wine.ui.SaleCreateDisplay;

// Modelo para exibir clientes no Spinner
public class ClientSpinnerModel {
    private String id;
    private String name;

    public ClientSpinnerModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Importante para o Spinner exibir o nome correto
    @Override
    public String toString() {
        return name;
    }
}