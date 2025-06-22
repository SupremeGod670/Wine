package com.example.wine.ui.SaleDisplay;

// Este modelo será usado para exibir os dados na RecyclerView
// Ele combina informações de Sale e Client
public class SaleDisplayModel {
    private String saleId;
    private String clientName;
    private String saleDate; // Formato de string para exibição
    private double total;
    private double clientLatitude;
    private double clientLongitude;
    private boolean isSelectedForRoute; // Para a checkbox na lista

    public SaleDisplayModel(String saleId, String clientName, String saleDate, double total, double clientLatitude, double clientLongitude) {
        this.saleId = saleId;
        this.clientName = clientName;
        this.saleDate = saleDate;
        this.total = total;
        this.clientLatitude = clientLatitude;
        this.clientLongitude = clientLongitude;
        this.isSelectedForRoute = false; // Valor padrão
    }

    public String getSaleId() {
        return saleId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public double getTotal() {
        return total;
    }

    public double getClientLatitude() {
        return clientLatitude;
    }

    public double getClientLongitude() {
        return clientLongitude;
    }

    public boolean isSelectedForRoute() {
        return isSelectedForRoute;
    }

    public void setSelectedForRoute(boolean selectedForRoute) {
        isSelectedForRoute = selectedForRoute;
    }
}