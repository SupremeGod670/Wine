package com.example.wine.ui.wine;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wine.R;
import com.example.wine.ui.publicaccess.PublicHomeActivity;
import com.google.android.material.navigation.NavigationView;

public class VinicolasActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton open;
    ListView lista;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_representacao3);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        open = findViewById(R.id.open);

        lista = findViewById(R.id.lista);

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
                    Intent intent = new Intent(VinicolasActivity.this, PublicHomeActivity.class);
                    startActivity(intent);
                }

                if (itemId == R.id.pedidosecomissoes) {
                    Intent intent = new Intent(VinicolasActivity.this, PedidosComissoesActivity.class);
                    startActivity(intent);
                }

                if (itemId == R.id.emissao) {
                    Intent intent = new Intent(VinicolasActivity.this, EmissaoPedidosActivity.class);
                    startActivity(intent);
                }

                if (itemId == R.id.vinicola) {
                    Toast.makeText(VinicolasActivity.this, "Você já está na tela vinicolas", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.close();

                return false;
            }
        });

    }

}
