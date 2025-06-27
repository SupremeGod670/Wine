package com.example.wine.ui.winery.list;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R;
import com.example.wine.adapter.WineryModel;
import com.example.wine.data.datasource.winery.WineryLocalDataSource;
import com.example.wine.data.local.AppDatabase;
import com.example.wine.domain.model.Winery;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class WineryListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WineryCardAdapter adapter;
    private WineryLocalDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winery_list);

        // Inicializa componentes
        recyclerView = findViewById(R.id.lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WineryCardAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Fonte de dados
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
