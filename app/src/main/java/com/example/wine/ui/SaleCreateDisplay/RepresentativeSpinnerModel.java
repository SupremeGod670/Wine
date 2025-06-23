package com.example.wine.ui.SaleCreateDisplay;

// Modelo para exibir representantes no Spinner
public class RepresentativeSpinnerModel {
    private String id;
    private String name; // Assumindo que o representante terá um nome associado (do AppUser)

    public RepresentativeSpinnerModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
