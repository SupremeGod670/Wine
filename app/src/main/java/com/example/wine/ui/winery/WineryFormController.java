package com.example.wine.ui.winery;

import android.text.TextUtils;
import com.example.wine.data.local.AppDatabase;
import com.example.wine.data.local.dao.WineryDao;
import com.example.wine.data.datasource.WineryLocalDataSource;
import com.example.wine.data.datasource.WineryRemoteDataSource;
import com.example.wine.domain.datasource.WineryDataSource;
import com.example.wine.domain.model.Winery;
import com.example.wine.domain.repository.WineryRepository;
import com.example.wine.domain.repository.WineryRepositoryImpl;

public class WineryFormController {

    private final WineryFormActivity view;
    private final WineryRepository wineryRepository;

    public WineryFormController(WineryFormActivity view) {
        this.view = view;

        // Inicializa o repository conforme o padrão do projeto
        AppDatabase db = AppDatabase.getInstance(view.getApplicationContext());
        WineryDao wineryDao = db.wineryDao();

        WineryDataSource localDataSource = new WineryLocalDataSource(wineryDao);
        WineryDataSource remoteDataSource = new WineryRemoteDataSource();

        this.wineryRepository = new WineryRepositoryImpl(localDataSource, remoteDataSource);
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
            wineryRepository.insertWinery(winery, new WineryRepository.RepositoryCallback() {
                @Override
                public void onSuccess() {
                    view.runOnUiThread(() -> view.showSuccess("Vinícola cadastrada com sucesso!"));
                }
                @Override
                public void onError(Throwable error) {
                    view.runOnUiThread(() -> view.showError("Erro ao salvar: " + error.getMessage()));
                }
            });
        }).start();
    }
}
