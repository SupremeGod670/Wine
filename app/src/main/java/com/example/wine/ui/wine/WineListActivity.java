package com.example.wine.ui.wine;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wine.R;
import com.example.wine.data.local.entity.WineEntity;

import java.util.List;

public class WineListActivity extends AppCompatActivity {
/*
    private ListView listView;
    private WineRepository repository;
    private List<WineEntity> wines;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_clientes);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        open = findViewById(R.id.open);

        listView = findViewById(R.id.listWines);
        repository = new WineRepository(this);

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.vinhos) {
                    Toast.makeText(WineListActivity.this, "Você já está na tela vinhos", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.close();

                return false;
            }
        });

        loadWines();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            WineEntity selected = wines.get(position);
            Intent intent = new Intent(this, WineFormActivity.class);
            intent.putExtra("wine_id", selected.id);
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle("Excluir vinho")
                    .setMessage("Deseja realmente excluir este vinho?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        repository.delete(wines.get(position));
                        loadWines();
                        Toast.makeText(this, "Vinho excluído com sucesso", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Não", null)
                    .show();
            return true;
        });

        findViewById(R.id.btnAddWine).setOnClickListener(v -> {
            startActivity(new Intent(this, WineFormActivity.class));
        });
    }

    private void loadWines() {
        wines = repository.getAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                wines.stream().map(w -> w.name + " - " + w.harvest).toArray(String[]::new));
        listView.setAdapter(adapter);
    }

 */
}