package com.example.wine.ui.wine.list;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wine.R;
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

        // Botão de abrir menu
        menuButton.setOnClickListener(view -> controller.openMenu());

        // Configuração do comportamento dos cliques no menu
        NavigationUtils.setupNavigation(this, navigationView, drawerLayout);
    }
}
