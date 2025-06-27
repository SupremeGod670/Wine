package com.example.wine.ui.wine.form;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

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

    private EditText nameEditText, yearEditText, grapeEditText, categoryEditText,
            alcoholEditText, priceEditText, stockEditText, tastingNotesEditText,
            ratingEditText, agingEditText, tempEditText, acidityEditText,
            pairingEditText, commercialCategoryEditText;

    private Spinner spinnerWinery;
    private ArrayAdapter<String> wineryAdapter;
    private List<String> wineryNames = new ArrayList<>();
    private Button saveButton;
    private WineFormController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_form);

        // 🔙 Botão de voltar
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // Verifica se há vinícolas cadastradas
        Executors.newSingleThreadExecutor().execute(() -> {
            WineryLocalDataSource wineryDataSource = new WineryLocalDataSource(
                    AppDatabase.getDatabase(getApplicationContext()).wineryDao()
            );

            List<Winery> wineries = wineryDataSource.getAll();
            if (wineries == null || wineries.isEmpty()) {
                runOnUiThread(() -> {
                    new AlertDialog.Builder(WineFormActivity.this)
                            .setTitle("Nenhuma vinícola cadastrada")
                            .setMessage("Você precisa cadastrar uma vinícola antes de adicionar um vinho. Deseja ir para o cadastro agora?")
                            .setPositiveButton("Cadastrar", (dialog, which) -> {
                                Intent intent = new Intent(WineFormActivity.this, com.example.wine.ui.winery.form.WineryFormActivity.class);
                                startActivity(intent);
                                finish();
                            })
                            .setNegativeButton("Cancelar", (dialog, which) -> finish())
                            .setCancelable(false)
                            .show();
                });
            }
        });

        controller = new WineFormController(this);

        // Inicialização dos componentes
        nameEditText = findViewById(R.id.edit_text_nome_vinho);
        yearEditText = findViewById(R.id.edit_text_safra);
        grapeEditText = findViewById(R.id.edit_text_varietal);
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
        commercialCategoryEditText = findViewById(R.id.edit_text_doc);
        spinnerWinery = findViewById(R.id.spinnerWinery);
        saveButton = findViewById(R.id.button_salvar_vinho);

        loadWineries();

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

                    Wine wine = new Wine(
                            null,
                            wineryId,
                            nameEditText.getText().toString(),
                            InputUtils.safeParseInt(yearEditText.getText().toString()),
                            grapeEditText.getText().toString(),
                            categoryEditText.getText().toString(),
                            InputUtils.safeParseDouble(alcoholEditText.getText().toString()),
                            InputUtils.safeParseDouble(priceEditText.getText().toString()),
                            InputUtils.safeParseInt(stockEditText.getText().toString()),
                            tastingNotesEditText.getText().toString(),
                            InputUtils.safeParseDouble(ratingEditText.getText().toString()),
                            agingEditText.getText().toString(),
                            tempEditText.getText().toString(),
                            acidityEditText.getText().toString(),
                            pairingEditText.getText().toString(),
                            commercialCategoryEditText.getText().toString(),
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

    public void showSuccessMessage() {
        ToastUtils.showShort(this, getString(R.string.wine_saved_success));
        finish();
    }

    public void showErrorMessage(String error) {
        ToastUtils.showShort(this, error);
    }
}
