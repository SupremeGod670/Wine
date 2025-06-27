package com.example.wine.ui.client.View;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R;
import com.example.wine.data.datasource.client.ClientLocalDataSource;
import com.example.wine.data.local.AppDatabase;
import com.example.wine.utils.NavigationUtils;
import com.example.wine.ui.client.View.ClientListController;
import com.google.android.material.navigation.NavigationView;

public class ClientListActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageButton menuButton;
    private RecyclerView recyclerViewClients;
    private ClientListAdapter adapter;
    private ClientListController controller;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        // Inicialização dos componentes
        drawerLayout = findViewById(R.id.drawer_layout);
        menuButton = findViewById(R.id.open);
        recyclerViewClients = findViewById(R.id.lista);
        navigationView = findViewById(R.id.navigation_view);

        // Garante que o menu será exibido corretamente
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.menu_all);

        // Ativa controle de acesso e redirecionamento via NavigationUtils
        NavigationUtils.setupNavigation(this, navigationView, drawerLayout);

        // Abre o menu ao clicar no botão
        menuButton.setOnClickListener(v -> drawerLayout.open());

        // Configura RecyclerView com Adapter
        adapter = new ClientListAdapter();
        recyclerViewClients.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewClients.setAdapter(adapter);

        // Controller + data source
        ClientLocalDataSource dataSource = new ClientLocalDataSource(
                AppDatabase.getDatabase(this).clientDao()
        );
        controller = new ClientListController(dataSource);

        loadApprovedClients();
    }

    private void loadApprovedClients() {
        controller.getApprovedClients(clients -> runOnUiThread(() -> adapter.setClientList(clients)));
    }
}
