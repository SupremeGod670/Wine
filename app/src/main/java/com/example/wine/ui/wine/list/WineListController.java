package com.example.wine.ui.wine.list;

import android.content.Context;
import android.widget.ListView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wine.R;
import com.example.wine.adapter.WineAdapter;
import com.example.wine.adapter.WineModel;
import com.example.wine.data.datasource.wine.WineLocalDataSource;
import com.example.wine.data.local.AppDatabase;
import com.example.wine.data.local.dao.WineryDao;
import com.example.wine.data.local.entity.WineryEntity;
import com.example.wine.domain.model.Wine;
import com.example.wine.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class WineListController {

    private final Context context;
    private final ListView listView;
    private final DrawerLayout drawerLayout;
    private final WineLocalDataSource dataSource;
    private final WineryDao wineryDao;
    private WineAdapter adapter;

    public WineListController(Context context, ListView listView, DrawerLayout drawerLayout) {
        this.context = context;
        this.listView = listView;
        this.drawerLayout = drawerLayout;

        this.dataSource = new WineLocalDataSource(
                AppDatabase.getDatabase(context).wineDao()
        );

        this.wineryDao = AppDatabase.getDatabase(context).wineryDao();

        loadWines();
    }

    public WineAdapter getAdapter() {
        return adapter;
    }

    /**
     * Carrega a lista de vinhos do banco e associa ao adapter.
     * Agora mostra o nome da vinícola, não o ID.
     */
    public void loadWines() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Wine> wineList = dataSource.getAll();

            ArrayList<WineModel> wineModels = new ArrayList<>();
            for (Wine wine : wineList) {
                String wineryName = getWineryNameById(wine.getWineryId());

                wineModels.add(new WineModel(
                        wine.getName(),
                        wine.getGrape(),
                        wine.getCategory(),
                        String.valueOf(wine.getYear()),
                        wineryName // 👈 Exibe o nome da vinícola
                ));
            }

            ((WineListActivity) context).runOnUiThread(() -> {
                adapter = new WineAdapter((WineListActivity) context, wineModels);
                listView.setAdapter(adapter);
            });
        });
    }

    /**
     * Faz um soft delete (lógico) de um vinho.
     */
    public void deleteWine(WineModel wineModel) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Wine> wineList = dataSource.getAll();

            for (Wine wine : wineList) {
                if (wine.getName().equals(wineModel.getNome())) {
                    dataSource.softDelete(wine.getId());
                    break;
                }
            }

            ((WineListActivity) context).runOnUiThread(() -> {
                ToastUtils.showShort(context, "Vinho deletado com sucesso!");
                loadWines();
            });
        });
    }

    /**
     * Abre o menu lateral.
     */
    public void openMenu() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(androidx.core.view.GravityCompat.START);
        } else {
            ToastUtils.showShort(context, context.getString(R.string.erro_abrir_menu));
        }
    }

    /**
     * Busca o nome da vinícola pelo ID.
     * Retorna uma string vazia se não encontrar.
     */
    private String getWineryNameById(String wineryId) {
        if (wineryId == null) return "";

        WineryEntity winery = wineryDao.getById(wineryId);
        if (winery != null) {
            return winery.getName();
        } else {
            return "";
        }
    }
}
