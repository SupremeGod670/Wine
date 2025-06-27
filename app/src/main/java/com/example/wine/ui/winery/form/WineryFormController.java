package com.example.wine.ui.winery.form;

import android.text.TextUtils;

import com.example.wine.data.datasource.winery.WineryLocalDataSource;
import com.example.wine.data.datasource.winery.WineryRemoteDataSource;
import com.example.wine.data.local.AppDatabase;
import com.example.wine.data.local.dao.WineryDao;
import com.example.wine.domain.model.Winery;
import com.example.wine.domain.repository.winery.WineryRepository;
import com.example.wine.domain.repository.winery.WineryRepositoryImpl;

public class WineryFormController {

    private final WineryFormActivity view;
    private final WineryRepository wineryRepository;

    public WineryFormController(WineryFormActivity view) {
        this.view = view;

        // Inicializa o repository conforme o padrão do projeto
        WineryDao wineryDao = AppDatabase.getDatabase(view.getApplicationContext()).wineryDao();
        WineryLocalDataSource localDataSource = new WineryLocalDataSource(wineryDao);
        WineryRemoteDataSource remoteDataSource = new WineryRemoteDataSource();

        this.wineryRepository = new WineryRepositoryImpl(localDataSource, remoteDataSource, view.getApplicationContext());
    }

    public void onSaveClicked(String name, String country, String region) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(country) || TextUtils.isEmpty(region)) {
            view.showError("Por favor, preencha todos os campos obrigatórios.");
            return;
        }

        Winery winery = new Winery();
        winery.setName(name);
        winery.setCountry(country);
        winery.setRegion(region);

        new Thread(() -> {
            wineryRepository.insert(winery);
            view.runOnUiThread(() -> view.showSuccess("Vinícola cadastrada com sucesso!"));
        }).start();
    }
}
