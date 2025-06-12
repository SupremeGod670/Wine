package com.example.wine.ui.wine.form;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wine.R;
import com.example.wine.domain.model.Wine;
import com.example.wine.utils.InputUtils;
import com.example.wine.utils.ToastUtils;

public class WineFormActivity extends AppCompatActivity {
    // Declaração de todos os EditTexts com os IDs do XML
    private EditText nameEditText, yearEditText, grapeEditText, categoryEditText,
            alcoholEditText, priceEditText, stockEditText, tastingNotesEditText,
            ratingEditText, agingEditText, tempEditText, acidityEditText,
            pairingEditText, commercialCategoryEditText, wineryIdEditText;

    private Button saveButton;
    private WineFormController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_form); // Certifique-se que o nome do layout está correto

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
        wineryIdEditText = findViewById(R.id.edit_text_vinicola); // Mapeado para vinícola

        saveButton = findViewById(R.id.button_salvar_vinho); // ID do botão salvar

        saveButton.setOnClickListener(v -> {
            // Coletar dados dos EditTexts
            String name = nameEditText.getText().toString();
            int year = InputUtils.safeParseInt(yearEditText.getText().toString());
            String grape = grapeEditText.getText().toString(); // Varietal
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
            String commercialCategory = commercialCategoryEditText.getText().toString(); // DOC
            String wineryId = wineryIdEditText.getText().toString(); // Vinícola

            // TODO: Adicionar validações de campos obrigatórios e formato aqui

            // Criar um objeto Wine usando o construtor completo do modelo de domínio
            // O ID será gerado automaticamente pelo construtor do Wine se for um novo vinho (null).
            // isSynced e updatedAt também serão tratados pelo construtor.
            Wine wine = new Wine(
                    null, // ID nulo para um novo vinho, será gerado no construtor de Wine
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
                    false, // isSynced
                    System.currentTimeMillis(), // updatedAt
                    false // deleted
            );

            controller.saveWine(wine);
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