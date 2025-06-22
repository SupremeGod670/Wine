package com.example.wine.ui.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wine.R;
import com.example.wine.utils.ToastUtils;

public class RegisterClientByAdminActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private RegisterClientByAdminController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.etClientPassword);
        buttonRegister = findViewById(R.id.buttonFinishRegister);

        controller = new RegisterClientByAdminController(this);

        buttonRegister.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (controller.validateInput(name, email, password)) {
                controller.registerClient(name, email, password);
            } else {
                ToastUtils.showShort(this, "Preencha todos os campos corretamente.");
            }
        });
    }
}
