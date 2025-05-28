package com.example.wine.ui.wine.form;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wine.R;
import com.example.wine.domain.model.Wine;
import com.example.wine.utils.InputUtils;
import java.util.UUID;

public class WineFormActivity extends AppCompatActivity {
    private EditText nameEditText, yearEditText, grapeEditText, categoryEditText, alcoholEditText, priceEditText, stockEditText, tastingNotesEditText, ratingEditText, agingEditText, tempEditText, acidityEditText, pairingEditText, commercialCategoryEditText;
    private Button saveButton;

    private WineFormController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_form);

        nameEditText = findViewById(R.id.editTextWineName);
        yearEditText = findViewById(R.id.editTextWineYear);
        grapeEditText = findViewById(R.id.editTextWineGrape);
        categoryEditText = findViewById(R.id.editTextWineCategory);
        alcoholEditText = findViewById(R.id.editTextWineAlcohol);
        priceEditText = findViewById(R.id.editTextWinePrice);
        stockEditText = findViewById(R.id.editTextWineStock);
        tastingNotesEditText = findViewById(R.id.editTextWineTastingNotes);
        ratingEditText = findViewById(R.id.editTextWineRating);
        agingEditText = findViewById(R.id.editTextWineAging);
        tempEditText = findViewById(R.id.editTextWineTemp);
        acidityEditText = findViewById(R.id.editTextWineAcidity);
        pairingEditText = findViewById(R.id.editTextWinePairing);
        commercialCategoryEditText = findViewById(R.id.editTextWineCommercialCategory);

        saveButton = findViewById(R.id.buttonSaveWine);

        controller = new WineFormController(this);

        saveButton.setOnClickListener(view -> {
            Wine wine = new Wine();
            wine.setId(UUID.randomUUID().toString()); // Gera um UUID como ID
            wine.setName(nameEditText.getText().toString());
            wine.setYear(InputUtils.safeParseInt(yearEditText.getText().toString()));
            wine.setGrape(grapeEditText.getText().toString());
            wine.setCategory(categoryEditText.getText().toString());
            wine.setAlcoholPercentage(InputUtils.safeParseDouble(alcoholEditText.getText().toString()));
            wine.setPrice(InputUtils.safeParseDouble(priceEditText.getText().toString()));
            wine.setStock(InputUtils.safeParseInt(stockEditText.getText().toString()));
            wine.setTastingNotes(tastingNotesEditText.getText().toString());
            wine.setRating(InputUtils.safeParseDouble(ratingEditText.getText().toString()));
            wine.setAgingTime(agingEditText.getText().toString());
            wine.setServingTemperature(tempEditText.getText().toString());
            wine.setAcidity(acidityEditText.getText().toString());
            wine.setPairing(pairingEditText.getText().toString());
            wine.setCommercialCategory(commercialCategoryEditText.getText().toString());

            controller.saveWine(wine);
        });
    }

    public void showSuccessMessage() {
        Toast.makeText(this, getString(R.string.wine_saved_success), Toast.LENGTH_LONG).show();
        finish();
    }

    public void showErrorMessage(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
