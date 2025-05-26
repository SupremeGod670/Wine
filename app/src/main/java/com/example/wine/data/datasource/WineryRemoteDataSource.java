package com.example.wine.data.datasource;

import com.example.wine.domain.datasource.WineryDataSource;
import com.example.wine.domain.model.Winery;
import java.util.ArrayList;
import java.util.List;

public class WineryRemoteDataSource implements WineryDataSource {
    @Override
    public void insertWinery(Winery winery, Callback callback) {
        // Futuro: chamada API/Retrofit
        callback.onSuccess();
    }

    @Override
    public void updateWinery(Winery winery, Callback callback) {
        callback.onSuccess();
    }

    @Override
    public void deleteWinery(String id, Callback callback) {
        callback.onSuccess();
    }

    @Override
    public void getWineryById(String id, GetWineryCallback callback) {
        callback.onSuccess(null);
    }

    @Override
    public void getAllWineries(GetAllWineriesCallback callback) {
        callback.onSuccess(new ArrayList<>());
    }
}
