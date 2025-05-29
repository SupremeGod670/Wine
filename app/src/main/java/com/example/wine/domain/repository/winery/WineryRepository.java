package com.example.wine.domain.repository.winery;

import com.example.wine.domain.model.Winery;
import java.util.List;

public interface WineryRepository {
    void insert(Winery winery);
    void update(Winery winery);
    void softDelete(String id);
    Winery getById(String id);
    List<Winery> getAll();

}
