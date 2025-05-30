package com.example.wine.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import com.example.wine.ui.cadastrar.CadastrarADMActivity;
import com.example.wine.ui.client.ClientRegisterActivity;
import com.example.wine.ui.cadastrar.CadastrarRepresentatesActivity;
import com.example.wine.ui.cadastrar.CadastrarVinhosActivity;
import com.example.wine.R;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class NavigationUtils {

    public static void setupNavigation(final Activity activity, NavigationView navigationView, final DrawerLayout drawerLayout) {
        String userRole = AccessUtils.getUserRole(activity); // "ADMIN", "REPRESENTATIVE", "PUBLIC"

        // Controle de visibilidade dos itens do menu de acordo com o papel do usuário, com checagem de null
        MenuItem admItem = navigationView.getMenu().findItem(R.id.adm);
        if (admItem != null && !"ADMIN".equals(userRole)) {
            admItem.setVisible(false); // Só admin vê o cadastro de admin
        }

        MenuItem representantesItem = navigationView.getMenu().findItem(R.id.representantes);
        if (representantesItem != null && !"REPRESENTATIVE".equals(userRole) && !"ADMIN".equals(userRole)) {
            representantesItem.setVisible(false); // Só representante ou admin
        }

        MenuItem vinhosItem = navigationView.getMenu().findItem(R.id.vinhos);
        if (vinhosItem != null) {
            // Se quiser restringir visibilidade para esse item, faça aqui
            // Exemplo: vinhosItem.setVisible("ADMIN".equals(userRole));
        }

        MenuItem clientesItem = navigationView.getMenu().findItem(R.id.clientes);
        if (clientesItem != null) {
            // Ajuste conforme necessário
        }

        MenuItem vRepresentantesItem = navigationView.getMenu().findItem(R.id.vrepresentantes);
        if (vRepresentantesItem != null) {
            // Ajuste conforme necessário
        }

        // Adicione outros itens do menu conforme sua necessidade, sempre checando null

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String role = AccessUtils.getUserRole(activity);
                int itemId = item.getItemId();

                if (itemId == R.id.adm) {
                    if (!"ADMIN".equals(role)) {
                        ToastUtils.showLong(activity, "Acesso restrito ao administrador.");
                    } else {
                        activity.startActivity(new Intent(activity, CadastrarADMActivity.class));
                    }
                } else if (itemId == R.id.vinhos) {
                    activity.startActivity(new Intent(activity, CadastrarVinhosActivity.class));
                } else if (itemId == R.id.clientes) {
                    activity.startActivity(new Intent(activity, ClientRegisterActivity.class));
                } else if (itemId == R.id.representantes) {
                    if (!"REPRESENTATIVE".equals(role) && !"ADMIN".equals(role)) {
                        ToastUtils.showLong(activity, "Acesso restrito aos representantes.");
                    } else {
                        activity.startActivity(new Intent(activity, CadastrarRepresentatesActivity.class));
                    }
                } else if (itemId == R.id.vrepresentantes) {
                    ToastUtils.showShort(activity, "Você já está na tela representantes");
                }
                // Fechar o drawer sempre após clicar
                drawerLayout.close();
                return false;
            }
        });
    }
}
