package com.example.wine.adapter;

public class ClienteModel {

    private String conta;
    private String nome;
    private String responsavel;

    public ClienteModel(String conta, String nome, String responsavel) {
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
