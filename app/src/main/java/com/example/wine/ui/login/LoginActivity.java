package com.example.wine.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wine.R;
import com.example.wine.ui.representative.RepresentativeDashboardActivity;
import com.example.wine.ui.client.ClientRegisterActivity;
import com.example.wine.ui.wine.list.WineListActivity;
import com.example.wine.utils.ToastUtils;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.buttonLogin);
        Button registerButton = findViewById(R.id.buttonRegister);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simula autenticação e nível de acesso
                String userRole = "REPRESENTATIVE"; // Altere para "REPRESENTATIVE" ou "ADMIN" para testar

                Intent intent;
                switch (userRole) {
                    case "ADMIN":
                        intent = new Intent(LoginActivity.this, WineListActivity.class);
                        ToastUtils.showShort(LoginActivity.this, "Bem-vindo, administrador!");
                        break;
                    case "REPRESENTATIVE":
                        intent = new Intent(LoginActivity.this, WineListActivity.class);
                        ToastUtils.showShort(LoginActivity.this, "Bem-vindo, representante!");
                        break;
                    case "PUBLIC":
                    default:
                        intent = new Intent(LoginActivity.this, WineListActivity.class);
                        ToastUtils.showShort(LoginActivity.this, "Bem-vindo!");
                        break;
                }
                startActivity(intent);
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ClientRegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
