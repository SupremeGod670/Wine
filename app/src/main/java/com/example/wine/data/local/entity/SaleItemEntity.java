// Caminho: com.example.wine.data.local.entity/SaleItemEntity.java
package com.example.wine.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "sale_item",
        foreignKeys = {
                @ForeignKey(entity = SaleEntity.class,
                        parentColumns = "id",
                        childColumns = "sale_id",
                        onDelete = ForeignKey.CASCADE), // Se a venda for deletada, seus itens também são.
                @ForeignKey(entity = WineEntity.class,
                        parentColumns = "id",
                        childColumns = "wine_id",
                        onDelete = ForeignKey.RESTRICT) // Não permite deletar o vinho se houver itens de venda associados.
        })
public class SaleItemEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "sale_id", index = true) // Índice para otimizar busca por ID da venda
    private String saleId;

    @ColumnInfo(name = "wine_id", index = true) // Índice para otimizar busca por ID do vinho
    private String wineId;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "unit_price")
    private double unitPrice;

    @ColumnInfo(name = "is_synced")
    private boolean isSynced;

    @ColumnInfo(name = "updated_at")
    private long updatedAt;

    @ColumnInfo(name = "deleted")
    private boolean deleted;

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getWineId() {
        return wineId;
    }

    public void setWineId(String wineId) {
        this.wineId = wineId;
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

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}