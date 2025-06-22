package com.example.wine.ui.wine.list;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ImageButton;

import androidx.appcompat.widget.SearchView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wine.R;
import com.example.wine.adapter.WineModel;
import com.example.wine.ui.wine.detail.WineDetailActivity;
import com.example.wine.utils.AccessUtils;
import com.example.wine.utils.NavigationUtils;
import com.google.android.material.navigation.NavigationView;

public class WineListActivity extends AppCompatActivity {

    private ListView listView;
    private ImageButton menuButton;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String userRole;
    private WineListController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_list);

        listView = findViewById(R.id.lista);
        menuButton = findViewById(R.id.open);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        userRole = AccessUtils.getUserRole(this);

        // Troca dinâmica do menu lateral
        if ("ADMIN".equals(userRole)) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_adm);
        } else if ("REPRESENTATIVE".equals(userRole)) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_representation);
        } else if ("CLIENT".equals(userRole)) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_clients);
        }

        // Inicializa o controller (passando o drawer se necessário)
        controller = new WineListController(this, listView, drawerLayout);

        SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                controller.getAdapter().getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                controller.getAdapter().getFilter().filter(newText);
                return false;
            }
        });

        // Botão de abrir menu
        menuButton.setOnClickListener(view -> controller.openMenu());

        listView.setOnItemClickListener((parent, view, position, id) -> {
            WineModel wine = (WineModel) parent.getItemAtPosition(position);

            Intent intent = new Intent(WineListActivity.this, WineDetailActivity.class);
            intent.putExtra("wine", wine); // <-- Envia o objeto inteiro
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            WineModel wine = (WineModel) parent.getItemAtPosition(position);

            new AlertDialog.Builder(WineListActivity.this)
                    .setTitle("Excluir Vinho")
                    .setMessage("Deseja excluir o vinho \"" + wine.getNome() + "\"?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        controller.deleteWine(wine);
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();

            return true;
        });




        // Configuração do comportamento dos cliques no menu
        NavigationUtils.setupNavigation(this, navigationView, drawerLayout);
    }
}
