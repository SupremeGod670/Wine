package com.example.wine.ui.Users.Client.ByAdmin;

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

@android.annotation.SuppressLint("MissingPermission")
public class RegisterClientByAdminActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword;
    private EditText editTextPhone, editTextCity, editTextObservation;
    private TextView textLatitude, textLongitude;
    private Button buttonFinishRegister;

    private FusedLocationProviderClient fusedLocationClient;
    private RegisterClientByAdminController controller;

    private double currentLatitude = 0.0;
    private double currentLongitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);

        // Botão de voltar
        ImageButton backButton = findViewById(R.id.open);
        backButton.setOnClickListener(v -> finish());

        // Views
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.etClientPassword);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextCity = findViewById(R.id.editTextCity);
        editTextObservation = findViewById(R.id.editTextObservation);
        textLatitude = findViewById(R.id.textLatitude);
        textLongitude = findViewById(R.id.textLongitude);
        buttonFinishRegister = findViewById(R.id.buttonFinishRegister);

        controller = new RegisterClientByAdminController(this);

        // Localização
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        solicitarLocalizacao();

        // Ação de cadastro
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
                ToastUtils.showShort(this, "Preencha todos os campos obrigatórios.");
            }
        });
    }

    private void solicitarLocalizacao() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();
                textLatitude.setText(String.valueOf(currentLatitude));
                textLongitude.setText(String.valueOf(currentLongitude));
                ToastUtils.showShort(this, "Localização capturada com sucesso!");
            }
        });
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
