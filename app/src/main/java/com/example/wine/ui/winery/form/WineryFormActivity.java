package com.example.wine.ui.winery.form;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

        etName = findViewById(R.id.etWineryName);
        etCountry = findViewById(R.id.etWineryCountry);
        etRegion = findViewById(R.id.etWineryRegion);
        btnSave = findViewById(R.id.btnSaveWinery);

        // Inicializa o controller passando o contexto da activity
        controller = new WineryFormController(this);

        btnSave.setOnClickListener(v -> controller.onSaveClicked(
                etName.getText().toString().trim(),
                etCountry.getText().toString().trim(),
                etRegion.getText().toString().trim()
        ));
    }

    // Métodos públicos para feedback visual (o Controller chama estes)
    public void showSuccess(String message) {
        ToastUtils.showShort(this, message);
        finish();
    }

    public void showError(String message) {
        ToastUtils.showShort(this, message);
    }
}
