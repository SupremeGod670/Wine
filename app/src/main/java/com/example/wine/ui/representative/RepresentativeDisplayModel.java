package com.example.wine.ui.representative;

// Modelo para exibir detalhes do representante na RecyclerView
public class RepresentativeDisplayModel {
    private String id;
    private String name; // Nome do AppUser associado
    private String phone;
    private String email; // E-mail do AppUser associado (útil para exibição)

    public RepresentativeDisplayModel(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}