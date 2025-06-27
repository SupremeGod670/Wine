package com.example.wine.ui.winery.list;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R;
import com.example.wine.adapter.WineryModel;
import com.example.wine.data.datasource.winery.WineryLocalDataSource;
import com.example.wine.data.local.AppDatabase;
import com.example.wine.domain.model.Winery;
import com.example.wine.utils.NavigationUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class WineryListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WineryCardAdapter adapter;
    private WineryLocalDataSource dataSource;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winery_list);

        // Inicializa views do menu
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuButton = findViewById(R.id.open);

        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.menu_all);
        NavigationUtils.setupNavigation(this, navigationView, drawerLayout);

        // Abre menu ao clicar
        menuButton.setOnClickListener(v -> drawerLayout.open());

        // Inicializa RecyclerView
        recyclerView = findViewById(R.id.lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WineryCardAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        dataSource = new WineryLocalDataSource(
                AppDatabase.getDatabase(getApplicationContext()).wineryDao()
        );

        loadWineries();
    }

    private void loadWineries() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Winery> wineries = dataSource.getAll();

            ArrayList<WineryModel> models = new ArrayList<>();
            for (Winery w : wineries) {
                models.add(new WineryModel(w.getName(), w.getCountry(), w.getRegion()));
            }

            runOnUiThread(() -> adapter.updateList(models));
        });
    }
}
