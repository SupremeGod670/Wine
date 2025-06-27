package com.example.wine.ui.wine.list;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wine.R;
import com.example.wine.adapter.WineModel;
import com.example.wine.ui.wine.detail.WineDetailActivity;
import com.example.wine.utils.NavigationUtils;
import com.google.android.material.navigation.NavigationView;

public class WineListActivity extends AppCompatActivity {

    private ListView listView;
    private ImageButton menuButton;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SearchView searchView;
    private WineListController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_list);

        // Iniciar componentes
        listView = findViewById(R.id.lista);
        menuButton = findViewById(R.id.open);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        searchView = findViewById(R.id.searchView);

        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.menu_all);

        controller = new WineListController(this, listView, drawerLayout);

        setupSearchView();

        // Botão menu lateral
        menuButton.setOnClickListener(view -> controller.openMenu());

        // Clique curto - abrir detalhes
        listView.setOnItemClickListener((parent, view, position, id) -> {
            WineModel wine = (WineModel) parent.getItemAtPosition(position);
            Intent intent = new Intent(WineListActivity.this, WineDetailActivity.class);
            intent.putExtra("wine", wine);
            startActivity(intent);
        });

        // Clique longo - deletar
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            WineModel wine = (WineModel) parent.getItemAtPosition(position);

            new AlertDialog.Builder(WineListActivity.this)
                    .setTitle("Excluir Vinho")
                    .setMessage("Deseja excluir o vinho \"" + wine.getNome() + "\"?")
                    .setPositiveButton("Sim", (dialog, which) -> controller.deleteWine(wine))
                    .setNegativeButton("Cancelar", null)
                    .show();

            return true;
        });

        NavigationUtils.setupNavigation(this, navigationView, drawerLayout);
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (controller.getAdapter() != null) {
                    controller.getAdapter().getFilter().filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (controller.getAdapter() != null) {
                    controller.getAdapter().getFilter().filter(newText);
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        controller.loadWines();
    }
}
