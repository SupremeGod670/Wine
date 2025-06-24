package com.example.wine.ui.Users.Client;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.wine.R;
import com.example.wine.data.datasource.client.ClientLocalDataSource;
import com.example.wine.data.datasource.user.AppUserLocalDataSource;
import com.example.wine.data.local.AppDatabaseProvider;
import com.example.wine.data.local.dao.AppUserDao;
import com.example.wine.data.local.dao.ClientDao;
import com.example.wine.domain.model.AppUser;
import com.example.wine.domain.model.Client;
import com.example.wine.utils.HashUtils;
import com.example.wine.utils.ToastUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterClientByAdminActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword, editTextPhone;
    private Button buttonRegister;

    private FusedLocationProviderClient fusedLocationClient;
    private double currentLatitude = 0.0;
    private double currentLongitude = 0.0;

    private AppUserLocalDataSource userDataSource;
    private ClientLocalDataSource clientDataSource;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.etClientPassword);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonRegister = findViewById(R.id.buttonFinishRegister);

        AppUserDao userDao = AppDatabaseProvider.getDatabase(this).appUserDao();
        ClientDao clientDao = AppDatabaseProvider.getDatabase(this).clientDao();
        userDataSource = new AppUserLocalDataSource(userDao);
        clientDataSource = new ClientLocalDataSource(clientDao);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        solicitarLocalizacao();

        buttonRegister.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String phone = editTextPhone.getText().toString().trim();

            if (validateInput(name, email, password, phone)) {
                registerClient(name, email, password, phone);
            } else {
                ToastUtils.showShort(this, "Preencha todos os campos corretamente.");
            }
        });
    }

    private boolean validateInput(String name, String email, String password, String phone) {
        return !name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !phone.isEmpty();
    }

    private void solicitarLocalizacao() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();
                    ToastUtils.showShort(this, "Localização capturada.");
                } else {
                    ToastUtils.showShort(this, "Não foi possível obter a localização.");
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                solicitarLocalizacao();
            } else {
                ToastUtils.showLong(this, "Permissão de localização negada.");
            }
        }
    }

    private void registerClient(String name, String email, String password, String phone) {
        executor.execute(() -> {
            try {
                // Criando AppUser
                String userId = UUID.randomUUID().toString();
                AppUser user = new AppUser();
                user.setId(userId);
                user.setName(name);
                user.setEmail(email);
                user.setPasswordHash(HashUtils.sha256(password));
                user.setRole("CLIENT");
                user.setApproved(true);
                user.setSynced(false);
                user.setUpdatedAt(System.currentTimeMillis());
                user.setDeleted(false);

                userDataSource.insert(user);

                // Criando Client com localização
                Client client = new Client();
                client.setId(UUID.randomUUID().toString());
                client.setUserId(userId);
                client.setName(name);
                client.setEmail(email);
                client.setPhone(phone);
                client.setCity("");
                client.setRegionId(null);
                client.setLatitude(currentLatitude);
                client.setLongitude(currentLongitude);
                client.setObservation("");
                client.setApproved(false);
                client.setApprovedBy(null);
                client.setApprovedAt(null);
                client.setSynced(false);
                client.setUpdatedAt(System.currentTimeMillis());
                client.setDeleted(false);

                clientDataSource.insert(client);

                uiHandler.post(() -> {
                    ToastUtils.showShort(this, "Cliente cadastrado com sucesso!");
                    finish();
                });

            } catch (Exception e) {
                uiHandler.post(() ->
                        ToastUtils.showLong(this, "Erro ao cadastrar cliente: " + e.getMessage())
                );
            }
        });
    }
}
