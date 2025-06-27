package com.example.wine.ui.representative;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R;
import com.example.wine.data.datasource.representative.RepresentativeLocalDataSource;
import com.example.wine.data.datasource.user.AppUserLocalDataSource;
import com.example.wine.data.local.AppDatabaseProvider;
import com.example.wine.ui.viewmodel.RepresentativeListViewModel;
import com.example.wine.utils.NavigationUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class RepresentativeListActivity extends AppCompatActivity {

    private RepresentativeListViewModel viewModel;
    private RepresentativeAdapter adapter;
    private RecyclerView recyclerViewRepresentatives;

    private ImageButton menuButton;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative_list);

        // Inicializa DataSources e ViewModel
        AppUserLocalDataSource appUserLocalDataSource = new AppUserLocalDataSource(
                AppDatabaseProvider.getDatabase(this).appUserDao());
        RepresentativeLocalDataSource representativeLocalDataSource = new RepresentativeLocalDataSource(
                AppDatabaseProvider.getDatabase(this).representativeDao());

        viewModel = new ViewModelProvider(this, new RepresentativeListViewModel.Factory(
                representativeLocalDataSource, appUserLocalDataSource))
                .get(RepresentativeListViewModel.class);

        // Inicializa componentes da UI
        recyclerViewRepresentatives = findViewById(R.id.recyclerViewRepresentatives);
        menuButton = findViewById(R.id.open);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        // Configura menu lateral
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.menu_all);
        NavigationUtils.setupNavigation(this, navigationView, drawerLayout);

        // Abre menu ao clicar no ícone
        menuButton.setOnClickListener(v -> drawerLayout.open());

        // Configura RecyclerView
        adapter = new RepresentativeAdapter(new ArrayList<>());
        recyclerViewRepresentatives.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRepresentatives.setAdapter(adapter);

        // Observa dados
        viewModel.representatives.observe(this, representativesList -> {
            adapter.updateList(representativesList);
        });

        // Carrega dados
        viewModel.loadRepresentatives();
    }
}
