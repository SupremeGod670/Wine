package com.example.wine.domain.datasource;

import com.example.wine.domain.model.Winery;

import java.util.List;

public interface WineryDataSource {
    void insertWinery(Winery winery, Callback callback);
    void updateWinery(Winery winery, Callback callback);
    void deleteWinery(String id, Callback callback);
    void getWineryById(String id, GetWineryCallback callback);
    void getAllWineries(GetAllWineriesCallback callback);

    interface Callback {
        void onSuccess();
        void onError(Throwable error);
    }
    interface GetWineryCallback {
        void onSuccess(Winery winery);
        void onError(Throwable error);
    }
    interface GetAllWineriesCallback {
        void onSuccess(List<Winery> wineries);
        void onError(Throwable error);
    }
}
