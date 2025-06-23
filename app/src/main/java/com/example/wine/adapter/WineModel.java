package com.example.wine.adapter;

import java.io.Serializable;

public class WineModel implements Serializable {

    private String nome;
    private String varietal;
    private String doc;
    private String safra;
    private String vinicola;

    public WineModel(String nome, String varietal, String doc, String safra, String vinicola) {
        this.nome = nome;
        this.varietal = varietal;
        this.doc = doc;
        this.safra = safra;
        this.vinicola = vinicola;
    }

    public String getNome() {
        return nome;
    }

    public String getVarietal() {
        return varietal;
    }

    public String getDoc() {
        return doc;
    }

    public String getSafra() {
        return safra;
    }

    public String getVinicola() {
        return vinicola;
    }
}
