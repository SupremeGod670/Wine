package com.example.wine.domain.repository.wine;

import com.example.wine.domain.model.Wine;

import java.util.List;

public interface WineRepository {
    List<Wine> getAll();
    Wine getById(String id);
    void insert(Wine wine);
    void update(Wine wine);
    void softDelete(String id);
}
