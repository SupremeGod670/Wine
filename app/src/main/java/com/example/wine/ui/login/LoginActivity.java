package com.example.wine.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wine.R;
import com.example.wine.data.local.AppDatabaseProvider;
import com.example.wine.data.local.dao.AppUserDao;
import com.example.wine.data.local.entity.AppUserEntity;
import com.example.wine.ui.client.ClientRegisterActivity;
import com.example.wine.ui.wine.list.WineListActivity;
import com.example.wine.utils.AccessUtils;
import com.example.wine.utils.HashUtils;
import com.example.wine.utils.ToastUtils;

import java.util.List;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private AppUserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailInput = findViewById(R.id.editTextEmail);
        EditText passwordInput = findViewById(R.id.editTextPassword);
        Button loginButton = findViewById(R.id.buttonLogin);
        Button registerButton = findViewById(R.id.buttonRegister);

        userDao = AppDatabaseProvider.getInstance(this).appUserDao();

        // ✅ Insere usuários de teste (REMOVA DEPOIS DOS TESTES)
        Executors.newSingleThreadExecutor().execute(() -> {
            seedTestUsers(userDao);
        });

        loginButton.setOnClickListener(view -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                ToastUtils.showShort(this, "Preencha todos os campos");
                return;
            }

            String hashed = HashUtils.sha256(password);

            Executors.newSingleThreadExecutor().execute(() -> {
                AppUserEntity user = userDao.login(email, hashed);

                runOnUiThread(() -> {
                    if (user == null) {
                        ToastUtils.showShort(this, "Credenciais inválidas");
                        return;
                    }

                    // ✅ Salva a sessão do usuário logado
                    AccessUtils.saveUserSession(this, user.getId(), user.getRole());

                    Intent intent = new Intent(this, WineListActivity.class);
                    ToastUtils.showShort(this, "Bem-vindo, " + user.getRole().toLowerCase() + "!");
                    startActivity(intent);
                    finish();
                });
            });
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ClientRegisterActivity.class);
            startActivity(intent);
        });
    }

    // ✅ Criação de usuários de teste com SHA-256
    private void seedTestUsers(AppUserDao userDao) {
        List<AppUserEntity> existingUsers = userDao.getAll();
        if (existingUsers != null && !existingUsers.isEmpty()) return;

        AppUserEntity admin = new AppUserEntity();
        admin.setId("ADM-TEST");
        admin.setName("Usuário Admin");
        admin.setEmail("admin@teste.com");
        admin.setPasswordHash(HashUtils.sha256("123"));
        admin.setRole("ADMIN");
        admin.setSynced(false);
        admin.setDeleted(false);
        admin.setUpdatedAt(System.currentTimeMillis());

        AppUserEntity rep = new AppUserEntity();
        rep.setId("REP-TEST");
        rep.setName("Usuário Representante");
        rep.setEmail("rep@teste.com");
        rep.setPasswordHash(HashUtils.sha256("123"));
        rep.setRole("REPRESENTATIVE");
        rep.setSynced(false);
        rep.setDeleted(false);
        rep.setUpdatedAt(System.currentTimeMillis());

        AppUserEntity client = new AppUserEntity();
        client.setId("CLI-TEST");
        client.setName("Usuário Cliente");
        client.setEmail("cliente@teste.com");
        client.setPasswordHash(HashUtils.sha256("123"));
        client.setRole("CLIENT");
        client.setSynced(false);
        client.setDeleted(false);
        client.setUpdatedAt(System.currentTimeMillis());

        userDao.insert(admin);
        userDao.insert(rep);
        userDao.insert(client);
    }
}
