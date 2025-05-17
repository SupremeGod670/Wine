package com.example.wine.ui.winery;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.wine.R;
import com.example.wine.data.repository.WineryRepositoryImpl;
import com.example.wine.domain.model.Winery;
import com.example.wine.domain.repository.WineryRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class WineryFormActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private EditText edtName, edtAddress, edtCity, edtCountry;
    private Button btnSave;

    private double latitude = 0.0;
    private double longitude = 0.0;

    private FusedLocationProviderClient fusedLocationClient;
    private WineryRepository wineryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winery_form);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        wineryRepository = new WineryRepositoryImpl(getApplicationContext());

        edtName = findViewById(R.id.edtWineryName);
        edtAddress = findViewById(R.id.edtWineryAddress);
        edtCity = findViewById(R.id.edtWineryCity);
        edtCountry = findViewById(R.id.edtWineryCountry);
        btnSave = findViewById(R.id.btnSaveWinery);

        requestLocationPermission();

        btnSave.setOnClickListener(v -> saveWinery());
    }

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLastLocation();
        }
    }

    private void getLastLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                });
    }

    private void saveWinery() {
        String name = edtName.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String city = edtCity.getText().toString().trim();
        String country = edtCountry.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty() || city.isEmpty() || country.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        Winery winery = new Winery(0, name, address, city, country, latitude, longitude);
        long id = wineryRepository.save(winery);

        if (id > 0) {
            Toast.makeText(this, "Vinícola cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao cadastrar vinícola.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        }
    }
}