package com.example.wine.adapter;

public class ClientModel {

    private String conta;
    private String nome;
    private String responsavel;

    public ClientModel(String conta, String nome, String responsavel) {
        this.conta = conta;
        this.nome = nome;
        this.responsavel = responsavel;
    }

    public String getConta() {
        return conta;
    }

    public String getNome() {
        return nome;
    }

    public String getResponsavel() {
        return responsavel;
    }

}
