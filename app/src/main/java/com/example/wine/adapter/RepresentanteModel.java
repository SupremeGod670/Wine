package com.example.wine.adapter;

public class RepresentanteModel {

    private String conta;
    private String nome;
    private String email;

    public RepresentanteModel(String conta, String nome, String email) {
        this.conta = conta;
        this.nome = nome;
        this.email = email;
    }

    public String getConta() {
        return conta;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

}
