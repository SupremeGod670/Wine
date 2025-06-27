package com.example.wine.ui.wine.form;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wine.R;
import com.example.wine.data.datasource.winery.WineryLocalDataSource;
import com.example.wine.data.local.AppDatabase;
import com.example.wine.domain.model.Wine;
import com.example.wine.domain.model.Winery;
import com.example.wine.utils.InputUtils;
import com.example.wine.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class WineFormActivity extends AppCompatActivity {
    // Declaração de todos os EditTexts com os IDs do XML
    private EditText nameEditText, yearEditText, grapeEditText, categoryEditText,
            alcoholEditText, priceEditText, stockEditText, tastingNotesEditText,
            ratingEditText, agingEditText, tempEditText, acidityEditText,
            pairingEditText, commercialCategoryEditText, wineryIdEditText;
    Spinner spinnerWinery;
    ArrayAdapter<String> wineryAdapter;
    List<String> wineryNames = new ArrayList<>();
    private Button saveButton;
    private WineFormController controller;
    private String getWineryIdByName(String name) {
        WineryLocalDataSource wineryDataSource = new WineryLocalDataSource(
                AppDatabase.getDatabase(getApplicationContext()).wineryDao()
        );
        List<Winery> wineries = wineryDataSource.getAll();
        for (Winery w : wineries) {
            if (w.getName().equals(name)) {
                return w.getId();
            }
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wine_form); // Certifique-se que o nome do layout está correto
        Executors.newSingleThreadExecutor().execute(() -> {
            WineryLocalDataSource wineryDataSource = new WineryLocalDataSource(
                    AppDatabase.getDatabase(getApplicationContext()).wineryDao()
            );

            List<Winery> wineries = wineryDataSource.getAll();

            if (wineries == null || wineries.isEmpty()) {
                runOnUiThread(() -> {
                    ToastUtils.showLong(this, "É necessário cadastrar uma vinícola antes de adicionar um vinho.");
                    Intent intent = new Intent(this, com.example.wine.ui.winery.form.WineryFormActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        });

        // Inicializar o controller
        controller = new WineFormController(this);

        // Inicializar os elementos da UI com os IDs atualizados do XML
        nameEditText = findViewById(R.id.edit_text_nome_vinho);
        yearEditText = findViewById(R.id.edit_text_safra);
        grapeEditText = findViewById(R.id.edit_text_varietal); // Mapeado para varietal
        categoryEditText = findViewById(R.id.edit_text_tipo);
        alcoholEditText = findViewById(R.id.edit_text_teor_alcoolico);
        priceEditText = findViewById(R.id.edit_text_price);
        stockEditText = findViewById(R.id.edit_text_stock);
        tastingNotesEditText = findViewById(R.id.edit_text_notas_degustacao);
        ratingEditText = findViewById(R.id.edit_text_rating);
        agingEditText = findViewById(R.id.edit_text_aging_time);
        tempEditText = findViewById(R.id.edit_text_serving_temperature);
        acidityEditText = findViewById(R.id.edit_text_acidity);
        pairingEditText = findViewById(R.id.edit_text_harmonizacoes);
        commercialCategoryEditText = findViewById(R.id.edit_text_doc); // Mapeado para doc
        spinnerWinery = findViewById(R.id.spinnerWinery);

        loadWineries();
        saveButton = findViewById(R.id.button_salvar_vinho); // ID do botão salvar

        saveButton.setOnClickListener(v -> {
            Object selectedItem = spinnerWinery.getSelectedItem();
            if (selectedItem == null) {
                ToastUtils.showLong(this, "Por favor, selecione uma vinícola válida.");
                return;
            }
            String selectedWineryName = selectedItem.toString();

            Executors.newSingleThreadExecutor().execute(() -> {
                String wineryId = getWineryIdByName(selectedWineryName);

                runOnUiThread(() -> {
                    if (wineryId == null) {
                        showErrorMessage("Erro ao obter a vinícola selecionada.");
                        return;
                    }

                    // Coletar outros dados normalmente (dentro da UI thread)
                    String name = nameEditText.getText().toString();
                    int year = InputUtils.safeParseInt(yearEditText.getText().toString());
                    String grape = grapeEditText.getText().toString();
                    String category = categoryEditText.getText().toString();
                    double alcoholPercentage = InputUtils.safeParseDouble(alcoholEditText.getText().toString());
                    double price = InputUtils.safeParseDouble(priceEditText.getText().toString());
                    int stock = InputUtils.safeParseInt(stockEditText.getText().toString());
                    String tastingNotes = tastingNotesEditText.getText().toString();
                    double rating = InputUtils.safeParseDouble(ratingEditText.getText().toString());
                    String agingTime = agingEditText.getText().toString();
                    String servingTemperature = tempEditText.getText().toString();
                    String acidity = acidityEditText.getText().toString();
                    String pairing = pairingEditText.getText().toString();
                    String commercialCategory = commercialCategoryEditText.getText().toString();

                    Wine wine = new Wine(
                            null,
                            wineryId,
                            name,
                            year,
                            grape,
                            category,
                            alcoholPercentage,
                            price,
                            stock,
                            tastingNotes,
                            rating,
                            agingTime,
                            servingTemperature,
                            acidity,
                            pairing,
                            commercialCategory,
                            false,
                            System.currentTimeMillis(),
                            false
                    );

                    controller.saveWine(wine);
                });
            });
        });

    }

    private void loadWineries() {
        Executors.newSingleThreadExecutor().execute(() -> {
            WineryLocalDataSource wineryDataSource = new WineryLocalDataSource(
                    AppDatabase.getDatabase(getApplicationContext()).wineryDao()
            );
            List<Winery> wineries = wineryDataSource.getAll();

            wineryNames.clear();
            for (Winery w : wineries) {
                wineryNames.add(w.getName());
            }

            runOnUiThread(() -> {
                wineryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, wineryNames);
                wineryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerWinery.setAdapter(wineryAdapter);
            });
        });
    }

    public void showSuccessMessage() {
        ToastUtils.showShort(this, getString(R.string.wine_saved_success));
        finish(); // Fechar a Activity após salvar
    }

    public void showErrorMessage(String error) {
        ToastUtils.showShort(this, error);
    }
}