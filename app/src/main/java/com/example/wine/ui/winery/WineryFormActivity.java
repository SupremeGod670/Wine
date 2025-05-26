package com.example.wine.ui.winery;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wine.R;
import com.example.wine.data.local.AppDatabase;
import com.example.wine.data.local.dao.WineryDao;
import com.example.wine.data.local.entity.WineryEntity;
import com.example.wine.data.repository.WineryRepositoryImpl;
import com.example.wine.utils.ToastUtils; // IMPORT UTIL

public class WineryFormActivity extends AppCompatActivity {

    private EditText etName, etCountry, etRegion;
    private Button btnSave;
    private WineryRepositoryImpl wineryRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winery_form);

        etName = findViewById(R.id.etWineryName);
        etCountry = findViewById(R.id.etWineryCountry);
        etRegion = findViewById(R.id.etWineryRegion);
        btnSave = findViewById(R.id.btnSaveWinery);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        WineryDao wineryDao = db.wineryDao();
        wineryRepository = new WineryRepositoryImpl(wineryDao);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String country = etCountry.getText().toString().trim();
            String region = etRegion.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(country) || TextUtils.isEmpty(region)) {
                ToastUtils.showLong(this, getString(R.string.msg_campos_obrigatorios));
                return;
            }

            WineryEntity winery = new WineryEntity();
            winery.setName(name);
            winery.setCountry(country);
            winery.setRegion(region);

            new Thread(() -> {
                wineryRepository.insertWinery(winery);
                runOnUiThread(() -> {
                    ToastUtils.showShort(this, getString(R.string.msg_vinicola_cadastrada_sucesso));
                    finish();
                });
            }).start();
        });
    }
}
