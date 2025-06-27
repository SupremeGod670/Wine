package com.example.wine.domain.repository.winery;

import android.content.Context;

import com.example.wine.data.datasource.winery.WineryLocalDataSource;
import com.example.wine.data.datasource.winery.WineryRemoteDataSource;
import com.example.wine.domain.model.Winery;
import com.example.wine.utils.NetworkUtils;

import java.util.List;

public class WineryRepositoryImpl implements WineryRepository {
    private final WineryLocalDataSource localDataSource;
    private final WineryRemoteDataSource remoteDataSource;
    private final Context context;

    public WineryRepositoryImpl(WineryLocalDataSource localDataSource, WineryRemoteDataSource remoteDataSource, Context context) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
        this.context = context;
    }

    @Override
    public void insert(Winery winery) {
        winery.setSynced(false);
        localDataSource.insert(winery);
        if (NetworkUtils.hasInternet(context)) {
            try {
                remoteDataSource.insert(winery);
                localDataSource.updateSyncStatus(winery.getId(), true);
            } catch (Exception e) {
                // Continua como não sincronizado
            }
        }
    }

    @Override
    public List<Winery> getAll() {
        return localDataSource.getAll();
    }

    @Override
    public Winery getById(String id) {
        return localDataSource.getById(id);
    }

    @Override
    public void update(Winery winery) {
        localDataSource.update(winery);
        if (NetworkUtils.hasInternet(context)) {
            try {
                remoteDataSource.update(winery);
                localDataSource.updateSyncStatus(winery.getId(), true);
            } catch (Exception e) {
                localDataSource.updateSyncStatus(winery.getId(), false);
            }
        } else {
            localDataSource.updateSyncStatus(winery.getId(), false);
        }
    }

    @Override
    public void softDelete(String id) {
        localDataSource.softDelete(id);
        if (NetworkUtils.hasInternet(context)) {
            try {
                remoteDataSource.softDelete(id);
                localDataSource.updateSyncStatus(id, true);
            } catch (Exception e) {
                localDataSource.updateSyncStatus(id, false);
            }
        } else {
            localDataSource.updateSyncStatus(id, false);
        }
    }

    // Método extra para sincronizar pendências, se quiser usar
    public void syncPending() {
        List<Winery> pendentes = localDataSource.getAllNotSynced();
        for (Winery winery : pendentes) {
            if (NetworkUtils.hasInternet(context)) {
                try {
                    remoteDataSource.insert(winery);
                    localDataSource.updateSyncStatus(winery.getId(), true);
                } catch (Exception e) {}
            }
        }
    }
}
