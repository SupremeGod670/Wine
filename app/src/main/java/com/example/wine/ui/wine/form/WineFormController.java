package com.example.wine.ui.wine.form;

import com.example.wine.data.datasource.WineLocalDataSource;
import com.example.wine.data.datasource.WineRemoteDataSource;
import com.example.wine.data.local.AppDatabase;
import com.example.wine.data.local.dao.WineDao;
import com.example.wine.domain.model.Wine;
import com.example.wine.domain.repository.WineRepository;
import com.example.wine.domain.repository.WineRepositoryImpl;
import com.example.wine.utils.InputUtils;

public class WineFormController {
    private final WineFormActivity activity;
    private final WineRepository repository;

    public WineFormController(WineFormActivity activity) {
        this.activity = activity;
        WineDao wineDao = AppDatabase.getInstance(activity.getApplicationContext()).wineDao();
        WineLocalDataSource localDataSource = new WineLocalDataSource(wineDao);
        WineRemoteDataSource remoteDataSource = new WineRemoteDataSource();
        this.repository = new WineRepositoryImpl(localDataSource, remoteDataSource, activity.getApplicationContext());
    }

    public void saveWine(Wine wine) {
        // Validações
        if (!InputUtils.isNotEmpty(wine.getName())) {
            activity.showErrorMessage("Nome do vinho é obrigatório.");
            return;
        }
        if (wine.getYear() <= 0) {
            activity.showErrorMessage("Safra inválida.");
            return;
        }
        if (!InputUtils.isNotEmpty(wine.getGrape())) {
            activity.showErrorMessage("Varietal (uva) é obrigatório.");
            return;
        }
        if (!InputUtils.isNotEmpty(wine.getCategory())) {
            activity.showErrorMessage("Tipo é obrigatório.");
            return;
        }
        if (wine.getAlcoholPercentage() < 0.1) {
            activity.showErrorMessage("Teor alcoólico inválido.");
            return;
        }
        if (wine.getPrice() < 0.1) {
            activity.showErrorMessage("Preço inválido.");
            return;
        }
        if (wine.getStock() < 0) {
            activity.showErrorMessage("Estoque inválido.");
            return;
        }

        new Thread(() -> {
            repository.insert(wine);
            activity.runOnUiThread(activity::showSuccessMessage);
        }).start();
    }
}
