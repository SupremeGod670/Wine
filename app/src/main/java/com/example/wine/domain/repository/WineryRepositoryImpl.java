package com.example.wine.domain.repository;

import com.example.wine.domain.datasource.WineryDataSource;
import com.example.wine.domain.model.Winery;
import java.util.List;

public class WineryRepositoryImpl implements WineryRepository {

    private final WineryDataSource localDataSource;
    private final WineryDataSource remoteDataSource;

    public WineryRepositoryImpl(WineryDataSource localDataSource, WineryDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void insertWinery(Winery winery, RepositoryCallback callback) {
        localDataSource.insertWinery(winery, new WineryDataSource.Callback() {
            @Override
            public void onSuccess() {
                // Futuro: sincronizar remoto aqui
                callback.onSuccess();
            }
            @Override
            public void onError(Throwable error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void updateWinery(Winery winery, RepositoryCallback callback) {
        localDataSource.updateWinery(winery, new WineryDataSource.Callback() {
            @Override
            public void onSuccess() {
                // Futuro: sincronizar remoto aqui
                callback.onSuccess();
            }
            @Override
            public void onError(Throwable error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void softDeleteWinery(String id, RepositoryCallback callback) {
        localDataSource.deleteWinery(id, new WineryDataSource.Callback() {
            @Override
            public void onSuccess() {
                // Futuro: sincronizar remoto aqui
                callback.onSuccess();
            }
            @Override
            public void onError(Throwable error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void getAllActiveWineries(GetAllWineriesCallback callback) {
        localDataSource.getAllWineries(new WineryDataSource.GetAllWineriesCallback() {
            @Override
            public void onSuccess(List<Winery> wineries) {
                callback.onSuccess(wineries);
            }
            @Override
            public void onError(Throwable error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void getWineryById(String id, GetWineryCallback callback) {
        localDataSource.getWineryById(id, new WineryDataSource.GetWineryCallback() {
            @Override
            public void onSuccess(Winery winery) {
                callback.onSuccess(winery);
            }
            @Override
            public void onError(Throwable error) {
                callback.onError(error);
            }
        });
    }
}
