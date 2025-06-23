package com.example.wine.ui.adminDisplay;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R; // Para acessar os recursos de layout e IDs
import com.example.wine.data.local.AppDatabaseProvider;
import com.example.wine.data.datasource.user.AppUserLocalDataSource;
import com.example.wine.ui.adminDisplay.AdminAdapter;
import com.example.wine.ui.viewmodel.AdminListViewModel;

import java.util.ArrayList;

public class AdminListActivity extends AppCompatActivity {

    private AdminListViewModel viewModel;
    private AdminAdapter adapter;
    private RecyclerView recyclerViewAdmins;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);

        // 1. Inicialização do DataSource e ViewModel
        AppUserLocalDataSource appUserLocalDataSource = new AppUserLocalDataSource(AppDatabaseProvider.getDatabase(this).appUserDao());

        viewModel = new ViewModelProvider(this, new AdminListViewModel.Factory(appUserLocalDataSource))
                .get(AdminListViewModel.class);

        // 2. Inicialização dos componentes da UI (Views)
        recyclerViewAdmins = findViewById(R.id.recyclerViewAdmins);
        backButton = findViewById(R.id.back_button);

        // 3. Configuração da RecyclerView
        adapter = new AdminAdapter(new ArrayList<>()); // Começa com lista vazia
        recyclerViewAdmins.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdmins.setAdapter(adapter);

        // 4. Observar os administradores do ViewModel
        viewModel.admins.observe(this, adminsList -> {
            adapter.updateList(adminsList);
        });

        // 5. Configurar Listeners
        backButton.setOnClickListener(v -> onBackPressed());

        // 6. Carregar administradores iniciais
        viewModel.loadAdmins();
    }
}