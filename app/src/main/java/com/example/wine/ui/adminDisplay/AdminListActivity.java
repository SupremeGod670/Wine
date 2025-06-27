package com.example.wine.ui.adminDisplay;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R;
import com.example.wine.data.local.AppDatabaseProvider;
import com.example.wine.data.datasource.user.AppUserLocalDataSource;
import com.example.wine.ui.adminDisplay.AdminAdapter;
import com.example.wine.ui.viewmodel.AdminListViewModel;
import com.example.wine.utils.NavigationUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class AdminListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminAdapter adapter;
    private AdminListViewModel viewModel;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton openMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);

        // Inicialização de componentes
        recyclerView = findViewById(R.id.recyclerViewAdmins);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        openMenuButton = findViewById(R.id.open_menu);

        // Configura RecyclerView
        adapter = new AdminAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Configura ViewModel
        AppUserLocalDataSource dataSource = new AppUserLocalDataSource(
                AppDatabaseProvider.getDatabase(this).appUserDao()
        );
        viewModel = new ViewModelProvider(this, new AdminListViewModel.Factory(dataSource))
                .get(AdminListViewModel.class);

        // Observa a lista de administradores
        viewModel.admins.observe(this, adminsList -> {
            adapter.updateList(adminsList); // método correto no adapter
        });

        // Menu lateral
        openMenuButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        NavigationUtils.setupNavigation(this, navigationView, drawerLayout);

        // Carrega os dados
        viewModel.loadAdmins();
    }
}
