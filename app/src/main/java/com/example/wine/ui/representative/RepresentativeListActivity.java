package com.example.wine.ui.representative;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R; // Para acessar os recursos de layout e IDs
import com.example.wine.data.local.AppDatabaseProvider;
import com.example.wine.data.datasource.representative.RepresentativeLocalDataSource;
import com.example.wine.data.datasource.user.AppUserLocalDataSource;
import com.example.wine.ui.representative.RepresentativeAdapter;
import com.example.wine.ui.viewmodel.RepresentativeListViewModel;

import java.util.ArrayList;

public class RepresentativeListActivity extends AppCompatActivity {

    private RepresentativeListViewModel viewModel;
    private RepresentativeAdapter adapter;
    private RecyclerView recyclerViewRepresentatives;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative_list);

        // 1. Inicialização dos DataSources e ViewModel
        // É crucial que o AppUserDao exista e esteja no AppDatabase
        AppUserLocalDataSource appUserLocalDataSource = new AppUserLocalDataSource(AppDatabaseProvider.getDatabase(this).appUserDao());
        RepresentativeLocalDataSource representativeLocalDataSource = new RepresentativeLocalDataSource(AppDatabaseProvider.getDatabase(this).representativeDao());

        viewModel = new ViewModelProvider(this, new RepresentativeListViewModel.Factory(
                representativeLocalDataSource, appUserLocalDataSource))
                .get(RepresentativeListViewModel.class);

        // 2. Inicialização dos componentes da UI (Views)
        recyclerViewRepresentatives = findViewById(R.id.recyclerViewRepresentatives);
        backButton = findViewById(R.id.back_button);

        // 3. Configuração da RecyclerView
        adapter = new RepresentativeAdapter(new ArrayList<>()); // Começa com lista vazia
        recyclerViewRepresentatives.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRepresentatives.setAdapter(adapter);

        // 4. Observar os representantes do ViewModel
        viewModel.representatives.observe(this, representativesList -> {
            adapter.updateList(representativesList);
        });

        // 5. Configurar Listeners
        backButton.setOnClickListener(v -> onBackPressed());

        // 6. Carregar representantes iniciais
        viewModel.loadRepresentatives();
    }
}
