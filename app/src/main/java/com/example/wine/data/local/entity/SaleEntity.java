// Caminho: com.example.wine.data.local.entity/SaleEntity.java
package com.example.wine.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "sale",
        foreignKeys = {
                @ForeignKey(entity = ClientEntity.class,
                        parentColumns = "id",
                        childColumns = "client_id",
                        onDelete = ForeignKey.RESTRICT), // Não permite deletar cliente com vendas associadas
                @ForeignKey(entity = RepresentativeEntity.class,
                        parentColumns = "id",
                        childColumns = "representative_id",
                        onDelete = ForeignKey.RESTRICT) // Não permite deletar representante com vendas associadas
        })
public class SaleEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "client_id", index = true)
    private String clientId;

    @ColumnInfo(name = "representative_id", index = true)
    private String representativeId;

    @ColumnInfo(name = "sale_date")
    private long saleDate;

    @ColumnInfo(name = "total")
    private double total;

    @ColumnInfo(name = "freight")
    private double freight;

    @ColumnInfo(name = "delivery_method")
    private String deliveryMethod;

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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRepresentativeId() {
        return representativeId;
    }

    public void setRepresentativeId(String representativeId) {
        this.representativeId = representativeId;
    }

    public long getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(long saleDate) {
        this.saleDate = saleDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
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