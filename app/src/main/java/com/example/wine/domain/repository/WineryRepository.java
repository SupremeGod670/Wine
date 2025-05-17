package com.example.wine.domain.repository;


import com.example.wine.domain.model.Winery;
import java.util.List;

public interface WineryRepository {
    long save(Winery winery);
    List<Winery> getAll();
    Winery getById(int id);
    int update(Winery winery);
    int delete(int id);
}