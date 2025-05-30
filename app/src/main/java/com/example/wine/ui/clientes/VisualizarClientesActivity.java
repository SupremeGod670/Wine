package com.example.wine.ui.clientes;

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
import com.example.wine.ui.wine.EmissaoPedidosActivity;
import com.example.wine.ui.wine.PedidosComissoesActivity;
import com.example.wine.ui.wine.VinicolasActivity;
import com.google.android.material.navigation.NavigationView;

public class VisualizarClientesActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton open;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_representacao);

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

                if (itemId == R.id.clientes) {
                    Toast.makeText(VisualizarClientesActivity.this, "Você já está na tela clientes", Toast.LENGTH_SHORT).show();
                }

                if (itemId == R.id.pedidosecomissoes) {
                    Intent intent = new Intent(VisualizarClientesActivity.this, PedidosComissoesActivity.class);
                    startActivity(intent);
                }

                if (itemId == R.id.emissao) {
                    Intent intent = new Intent(VisualizarClientesActivity.this, EmissaoPedidosActivity.class);
                    startActivity(intent);
                }

                if (itemId == R.id.vinicola) {
                    Intent intent = new Intent(VisualizarClientesActivity.this, VinicolasActivity.class);
                    startActivity(intent);
                }

                drawerLayout.close();

                return false;
            }
        });

    }

}
