package com.example.wine.ui.Users.Admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonFinishRegister = findViewById(R.id.buttonSave);

        controller = new RegisterAdminController(this);

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
