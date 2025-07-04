package com.example.wine.ui.client;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.wine.R;
import com.example.wine.utils.ToastUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class ClientRegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword;
    private EditText editTextPhone, editTextCity, editTextObservation;
    private TextView textLatitude, textLongitude;
    private Button buttonFinishRegister;
    private ImageButton backButton;

    private FusedLocationProviderClient fusedLocationClient;
    private double currentLatitude = 0.0;
    private double currentLongitude = 0.0;

    private RegisterClientController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);

        // Inicialização das views
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.etClientPassword);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextCity = findViewById(R.id.editTextCity);
        editTextObservation = findViewById(R.id.editTextObservation);
        textLatitude = findViewById(R.id.textLatitude);
        textLongitude = findViewById(R.id.textLongitude);
        buttonFinishRegister = findViewById(R.id.buttonFinishRegister);
        backButton = findViewById(R.id.open);

        // Controller e localização
        controller = new RegisterClientController(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        backButton.setOnClickListener(v -> finish());

        buttonFinishRegister.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String phone = editTextPhone.getText().toString().trim();
            String city = editTextCity.getText().toString().trim();
            String observation = editTextObservation.getText().toString().trim();

            if (controller.validateInput(name, email, password)) {
                controller.registerClient(name, email, password, phone, city, observation, currentLatitude, currentLongitude);
            } else {
                ToastUtils.showShort(this, "Preencha todos os campos obrigatórios corretamente.");
            }
        });

        solicitarLocalizacao();
    }

    private void solicitarLocalizacao() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }

        fusedLocationClient.requestLocationUpdates(
                com.google.android.gms.location.LocationRequest.create()
                        .setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(5000)
                        .setFastestInterval(2000),
                new com.google.android.gms.location.LocationCallback() {
                    @Override
                    public void onLocationResult(com.google.android.gms.location.LocationResult locationResult) {
                        if (locationResult == null || locationResult.getLastLocation() == null) return;

                        android.location.Location location = locationResult.getLastLocation();
                        currentLatitude = location.getLatitude();
                        currentLongitude = location.getLongitude();

                        textLatitude.setText(String.valueOf(currentLatitude));
                        textLongitude.setText(String.valueOf(currentLongitude));

                        fusedLocationClient.removeLocationUpdates(this);
                    }
                },
                getMainLooper()
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            solicitarLocalizacao();
        } else {
            ToastUtils.showLong(this, "Permissão de localização negada.");
        }
    }
}
