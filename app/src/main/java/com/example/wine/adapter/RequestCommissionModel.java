package com.example.wine.adapter;

public class RequestCommissionModel {

    private String codigo;
    private String nome;
    private String total;
    private String comissao;

    public RequestCommissionModel(String codigo, String nome, String total, String comissao) {
        this.codigo = codigo;
        this.nome = nome;
        this.total = total;
        this.comissao = comissao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getTotal() {
        return total;
    }

    public String getComissao() {
        return comissao;
    }
}
