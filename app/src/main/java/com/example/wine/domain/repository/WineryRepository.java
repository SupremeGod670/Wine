package com.example.wine.domain.repository;


import com.example.wine.domain.callback.RepositoryCallback;
import com.example.wine.domain.model.Winery;
import java.util.List;

public interface WineryRepository {
    // Método save AGORA é assíncrono e usa um Callback
    void save(Winery winery, RepositoryCallback<Long> callback);

    List<Winery> getAll();
    Winery getById(int id);
    int update(Winery winery);
    int delete(int id);
}