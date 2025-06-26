package com.example.wine.ui.Users.Admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wine.R;
import com.example.wine.utils.ToastUtils;

public class RegisterAdminActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword;
    private Button buttonFinishRegister;
    private RegisterAdminController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_adm);

        // Configura botão de voltar com o ícone arrow_back_icon.xml
        ImageButton backButton = findViewById(R.id.open);
        backButton.setOnClickListener(v -> finish());

        // Inicializa os campos de entrada
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonFinishRegister = findViewById(R.id.buttonSave);

        // Controller responsável pelo cadastro do ADM
        controller = new RegisterAdminController(this);

        // Ação do botão "Criar ADM"
        buttonFinishRegister.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (controller.validateInput(name, email, password)) {
                controller.registerAdmin(name, email, password);
            } else {
                ToastUtils.showShort(this, "Preencha todos os campos corretamente.");
            }
        });
    }
}
