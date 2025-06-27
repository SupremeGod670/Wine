package com.example.wine.ui.winery.form;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wine.R;
import com.example.wine.utils.ToastUtils;

public class WineryFormActivity extends AppCompatActivity {

    private EditText etName, etCountry, etRegion;
    private Button btnSave;
    private WineryFormController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winery_form);

        // Seta de voltar (⬅️)
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // Campos de entrada
        etName = findViewById(R.id.etWineryName);
        etCountry = findViewById(R.id.etWineryCountry);
        etRegion = findViewById(R.id.etWineryRegion);
        btnSave = findViewById(R.id.btnSaveWinery);

        // Controller de lógica
        controller = new WineryFormController(this);

        // Ação de salvar
        btnSave.setOnClickListener(v -> controller.onSaveClicked(
                etName.getText().toString().trim(),
                etCountry.getText().toString().trim(),
                etRegion.getText().toString().trim()
        ));
    }

    // Feedback positivo (encerrar Activity)
    public void showSuccess(String message) {
        ToastUtils.showShort(this, message);
        finish();
    }

    // Feedback de erro
    public void showError(String message) {
        ToastUtils.showShort(this, message);
    }
}
