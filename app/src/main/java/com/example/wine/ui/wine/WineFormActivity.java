package com.example.wine.ui.wine;


import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wine.R;

public class WineFormActivity extends AppCompatActivity {
/*
    private EditText edtName, edtHarvest, edtType, edtNotes, edtPairing;
    private Button btnSave;
    private WineRepository repository;
    private int wineId = -1;
    private WineEntity wine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_form);

        edtName = findViewById(R.id.edtName);
        edtHarvest = findViewById(R.id.edtHarvest);
        edtType = findViewById(R.id.edtType);
        edtNotes = findViewById(R.id.edtNotes);
        edtPairing = findViewById(R.id.edtPairing);
        btnSave = findViewById(R.id.btnSave);
        repository = new WineRepository(this);

        wineId = getIntent().getIntExtra("wine_id", -1);
        if (wineId != -1) {
            wine = repository.getById(wineId);
            edtName.setText(wine.name);
            edtHarvest.setText(wine.harvest);
            edtType.setText(wine.type);
            edtNotes.setText(wine.notes);
            edtPairing.setText(wine.pairing);
        } else {
            wine = new WineEntity();
        }

        btnSave.setOnClickListener(v -> {
            wine.name = edtName.getText().toString();
            wine.harvest = edtHarvest.getText().toString();
            wine.type = edtType.getText().toString();
            wine.notes = edtNotes.getText().toString();
            wine.pairing = edtPairing.getText().toString();

            if (wineId == -1) {
                repository.insert(wine);
                Toast.makeText(this, "Vinho cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                repository.update(wine);
                Toast.makeText(this, "Vinho atualizado com sucesso", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }

 */
}