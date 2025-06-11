// Caminho: com.example.wine.data.local.entity/WineStockEntity.java
package com.example.wine.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "wine_stock",
        foreignKeys = {
                @ForeignKey(entity = WineEntity.class,
                        parentColumns = "id",
                        childColumns = "wine_id",
                        onDelete = ForeignKey.RESTRICT), // Não permite deletar o vinho se houver estoque
                @ForeignKey(entity = RepresentativeEntity.class,
                        parentColumns = "id",
                        childColumns = "representative_id",
                        onDelete = ForeignKey.CASCADE) // Se o representante for deletado, seu estoque de vinhos também
        },
        indices = {@androidx.room.Index(value = {"wine_id", "representative_id"}, unique = true)}) // Garante estoque único por vinho e representante
public class WineStockEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "wine_id")
    private String wineId;

    @ColumnInfo(name = "representative_id")
    private String representativeId;

    @ColumnInfo(name = "quantity")
    private int quantity;

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

    public String getWineId() {
        return wineId;
    }

    public void setWineId(String wineId) {
        this.wineId = wineId;
    }

    public String getRepresentativeId() {
        return representativeId;
    }

    public void setRepresentativeId(String representativeId) {
        this.representativeId = representativeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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