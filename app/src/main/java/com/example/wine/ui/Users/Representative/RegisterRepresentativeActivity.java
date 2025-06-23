package com.example.wine.ui.Users.Representative;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wine.R;
import com.example.wine.utils.ToastUtils;

public class RegisterRepresentativeActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPhone;
    private Button buttonRegister;

    private RegisterRepresentativeController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_representatives);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonRegister = findViewById(R.id.buttonRegister);

        controller = new RegisterRepresentativeController(this);

        buttonRegister.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String phone = editTextPhone.getText().toString().trim(); // 🆕

            if (!controller.validateInput(name, email, password, phone)) {
                ToastUtils.showShort(this, "Preencha todos os campos.");
                return;
            }

            controller.registerRepresentative(name, email, password, phone);
        });
    }
}
