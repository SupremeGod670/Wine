package com.example.wine.adapter;

public class VinicolaModel {

    private String nome;
    private String pais;
    private String endereco;

    public VinicolaModel(String nome, String pais, String endereco) {
        this.nome = nome;
        this.pais = pais;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public String getPais() {
        return pais;
    }

    public String getEndereco() {
        return endereco;
    }
}
