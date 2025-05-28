package com.example.wine.domain.datasource;

import com.example.wine.domain.model.Wine;
import java.util.List;

public interface WineDataSource {
    List<Wine> getAll();
    Wine getById(String id);
    void insert(Wine wine);
    void update(Wine wine);
    void softDelete(String id);
}