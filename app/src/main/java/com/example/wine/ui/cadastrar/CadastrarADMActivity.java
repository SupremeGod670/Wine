package com.example.wine.ui.cadastrar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wine.R;
import com.example.wine.ui.representantes.VisualizarRepresentantesActivity;
import com.google.android.material.navigation.NavigationView;

public class CadastrarADMActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton open;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_adm);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        open = findViewById(R.id.open);

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.adm) {
                    Toast.makeText(CadastrarADMActivity.this, "Você já está na tela adm", Toast.LENGTH_SHORT).show();
                }

                if (itemId == R.id.vinhos) {
                    Intent intent = new Intent(CadastrarADMActivity.this, CadastrarVinhosActivity.class);
                    startActivity(intent);
                }

                if (itemId == R.id.clientes) {
                    Intent intent = new Intent(CadastrarADMActivity.this, CadastrarClientesActivity.class);
                    startActivity(intent);
                }

                if (itemId == R.id.representantes) {
                    Intent intent = new Intent(CadastrarADMActivity.this, CadastrarRepresentatesActivity.class);
                    startActivity(intent);
                }

                if (itemId == R.id.vrepresentantes) {
                    Intent intent = new Intent(CadastrarADMActivity.this, VisualizarRepresentantesActivity.class);
                    startActivity(intent);
                }

                drawerLayout.close();

                return false;
            }
        });

    }
}
