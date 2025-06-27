    package com.example.wine.domain.repository.wine;

    import android.content.Context;

    import com.example.wine.data.datasource.wine.WineLocalDataSource;
    import com.example.wine.data.datasource.wine.WineRemoteDataSource;
    import com.example.wine.domain.model.Wine;
    import com.example.wine.utils.NetworkUtils;

    import java.util.List;

    public class WineRepositoryImpl implements WineRepository {
        private final WineLocalDataSource localDataSource;
        private final WineRemoteDataSource remoteDataSource;
        private final Context context;

        public WineRepositoryImpl(WineLocalDataSource localDataSource, WineRemoteDataSource remoteDataSource, Context context) {
            this.localDataSource = localDataSource;
            this.remoteDataSource = remoteDataSource;
            this.context = context;
        }

        @Override
        public void insert(Wine wine) {
            wine.setSynced(false); // Sempre insere como não sincronizado
            localDataSource.insert(wine);

            if (NetworkUtils.hasInternet(context)) {
                try {
                    remoteDataSource.insert(wine);
                    // Se salvar remoto, marca como sincronizado
                    localDataSource.updateSyncStatus(wine.getId(), true);
                } catch (Exception e) {
                    // Continua como não sincronizado
                }
            }
        }

        @Override
        public List<Wine> getAll() {
            return localDataSource.getAll();
        }

        @Override
        public Wine getById(String id) {
            return localDataSource.getById(id);
        }

        @Override
        public void update(Wine wine) {
            localDataSource.update(wine);
            if (NetworkUtils.hasInternet(context)) {
                try {
                    remoteDataSource.update(wine);
                    localDataSource.updateSyncStatus(wine.getId(), true);
                } catch (Exception e) {
                    localDataSource.updateSyncStatus(wine.getId(), false);
                }
            } else {
                localDataSource.updateSyncStatus(wine.getId(), false);
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

        // Sincroniza todos os pendentes (chame quando tiver internet)
        public void syncPending() {
            List<Wine> pendentes = localDataSource.getAllNotSynced();
            for (Wine wine : pendentes) {
                if (NetworkUtils.hasInternet(context)) {
                    try {
                        remoteDataSource.insert(wine);
                        localDataSource.updateSyncStatus(wine.getId(), true);
                    } catch (Exception e) {
                        // Se falhar, tenta de novo depois
                    }
                }
            }
        }
    }
