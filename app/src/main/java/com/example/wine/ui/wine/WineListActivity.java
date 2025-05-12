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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_list);

        listView = findViewById(R.id.listWines);
        repository = new WineRepository(this);

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