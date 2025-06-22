package com.example.wine.ui.adminDisplay;

// Modelo para exibir detalhes do administrador na RecyclerView
public class AdminDisplayModel {
    private String id;
    private String name;
    private String email;
    private String role; // Para confirmar que é ADMIN

    public AdminDisplayModel(String id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}