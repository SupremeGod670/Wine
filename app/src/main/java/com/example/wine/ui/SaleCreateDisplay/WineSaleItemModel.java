package com.example.wine.ui.SaleCreateDisplay;

// Modelo para representar um item de vinho na lista de itens da venda (RecyclerView)
public class WineSaleItemModel {
    private String wineId;
    private String wineName;
    private int quantity;
    private double unitPrice;

    public WineSaleItemModel(String wineId, String wineName, int quantity, double unitPrice) {
        this.wineId = wineId;
        this.wineName = wineName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getWineId() {
        return wineId;
    }

    public String getWineName() {
        return wineName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getItemTotal() {
        return quantity * unitPrice;
    }
}