package com.example.wine.ui.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wine.R;
import com.example.wine.ui.login.LoginActivity;
import com.example.wine.utils.ToastUtils;

public class ClientRegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);

        Button buttonFinishRegister = findViewById(R.id.buttonFinishRegister);
        buttonFinishRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(ClientRegisterActivity.this,  "Cadastro enviado para análise. Aguarde a aprovação.");
                Intent intent = new Intent(ClientRegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
