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
// Não precisa importar AppDatabaseProvider na Activity diretamente, apenas o Repositório
// import com.example.wine.data.local.AppDatabaseProvider;
import com.example.wine.data.repository.WineryRepositoryImpl; // Importe a implementação
import com.example.wine.domain.model.Winery;
import com.example.wine.domain.repository.WineryRepository; // Importe a interface
import com.example.wine.domain.callback.RepositoryCallback; // Importe o Callback

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class WineryFormActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private EditText edtName, edtAddress, edtCity, edtCountry;
    private Button btnSave;

    private double latitude = 0.0;
    private double longitude = 0.0;

    private FusedLocationProviderClient fusedLocationClient;
    private WineryRepository wineryRepository; // Use a interface

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winery_form);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // --- MUDANÇA AQUI: Instancie o Repositório ---
        // O Repositório AGORA OBTÉM o DB Singleton internamente
        wineryRepository = new WineryRepositoryImpl(getApplicationContext());
        // ----------------------------------------------

        edtName = findViewById(R.id.edtWineryName);
        edtAddress = findViewById(R.id.edtWineryAddress);
        edtCity = findViewById(R.id.edtWineryCity);
        edtCountry = findViewById(R.id.edtWineryCountry);
        btnSave = findViewById(R.id.btnSaveWinery);

        requestLocationPermission();

        btnSave.setOnClickListener(v -> saveWinery());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // IMPORTANTE: Chame shutdown no Executor do repositório ao sair da Activity
        // Se o repositório for usado em um escopo maior (ex: Application), o shutdown deve ser lá.
        if (wineryRepository instanceof WineryRepositoryImpl) {
            ((WineryRepositoryImpl) wineryRepository).shutdown();
        }
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
        // Verifique a permissão novamente (boa prática)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return; // Sem permissão, sai
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        // Opcional: Toast ou log para confirmar que a localização foi obtida
                    } else {
                        // Toast.makeText(this, "Não foi possível obter a última localização.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(this, e -> {
                    // Toast.makeText(this, "Erro ao obter localização: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

        // Crie o objeto Winery
        // O construtor sem ID inicial é apropriado para um novo item que o Room gerará o ID
        Winery winery = new Winery(0, name, address, city, country, latitude, longitude); // Usando o construtor com ID 0 que será ignorado pelo Room na inserção

        // --- MUDANÇA CRUCIAL AQUI: Chame o método save assíncrono e passe um Callback ---
        wineryRepository.save(winery, new RepositoryCallback<Long>() {
            @Override
            public void onSuccess(Long id) {
                // Esta parte PODE NÃO ESTAR RODANDO NA THREAD PRINCIPAL por padrão!
                // Use runOnUiThread para garantir que as operações de UI aconteçam na thread correta.
                runOnUiThread(() -> {
                    if (id > 0) {
                        Toast.makeText(WineryFormActivity.this, "Vinícola cadastrada com sucesso! ID: " + id, Toast.LENGTH_SHORT).show();
                        finish(); // Fecha a Activity
                    } else {
                        // Isso não deve acontecer com insert retornando long > 0, mas é bom tratar
                        Toast.makeText(WineryFormActivity.this, "Erro desconhecido ao cadastrar vinícola.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                // Esta parte PODE NÃO ESTAR RODANDO NA THREAD PRINCIPAL por padrão!
                runOnUiThread(() -> {
                    Toast.makeText(WineryFormActivity.this, "Erro ao cadastrar vinícola: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace(); // Log do erro para debug
                });
            }
        });
        // -----------------------------------------------------------------------------

        // A Activity NÃO ESPERA MAIS pela operação de save aqui. Ela continua executando.
        // O resultado é tratado DENTRO do Callback.
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation(); // Permissão concedida, tenta obter localização
            } else {
                Toast.makeText(this, "Permissão de localização negada. A localização da vinícola não será salva.", Toast.LENGTH_LONG).show();
            }
        }
    }
}