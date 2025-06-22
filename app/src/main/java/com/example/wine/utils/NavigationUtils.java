package com.example.wine.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wine.R;
import com.example.wine.ui.SaleCreateDisplay.CreateSaleActivity;
import com.example.wine.ui.Users.Admin.RegisterAdminActivity;
import com.example.wine.ui.Users.Client.RegisterClientByAdminActivity;
import com.example.wine.ui.Users.Representative.RegisterRepresentativeActivity;
import com.example.wine.ui.wine.form.WineFormActivity;
import com.example.wine.ui.winery.form.WineryFormActivity;
import com.example.wine.ui.winery.list.WineryListActivity;
import com.google.android.material.navigation.NavigationView;
import com.example.wine.ui.representative.RepresentativeListActivity;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NavigationUtils {

    // Mapeamento dos itens do menu para suas respectivas Activities
    private static final Map<Integer, Class<? extends Activity>> MENU_ACTIVITY_MAP = new HashMap<>();
    static {
        MENU_ACTIVITY_MAP.put(R.id.vinhos, WineFormActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.adm, RegisterAdminActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.clientes, RegisterClientByAdminActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.representantes, RegisterRepresentativeActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.CadastrarPedido, CreateSaleActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.cadastro_vinicola, WineryFormActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.lista_vinicola, WineryListActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.vrepresentantes, RepresentativeListActivity.class);
    }

    // Controle de acesso por perfil de usuário
    private static final Map<Integer, Set<String>> MENU_ACCESS_MAP = new HashMap<>();
    static {
        Set<String> adminOnly = new HashSet<>();
        adminOnly.add("ADMIN");

        Set<String> repOnly = new HashSet<>();
        repOnly.add("REPRESENTATIVE");

        Set<String> both = new HashSet<>();
        both.add("ADMIN");
        both.add("REPRESENTATIVE");

        // Acesso exclusivo do admin
        MENU_ACCESS_MAP.put(R.id.adm, adminOnly);
        MENU_ACCESS_MAP.put(R.id.representantes, adminOnly);
        MENU_ACCESS_MAP.put(R.id.vrepresentantes, adminOnly);
        MENU_ACCESS_MAP.put(R.id.clientes, adminOnly);

        // Acesso compartilhado
        MENU_ACCESS_MAP.put(R.id.vinhos, both);
    }

    public static void setupNavigation(final Activity activity, NavigationView navigationView, final DrawerLayout drawerLayout) {
        String userRole = AccessUtils.getUserRole(activity);

        // Exibe ou oculta itens do menu conforme o papel do usuário
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            MenuItem item = navigationView.getMenu().getItem(i);
            Set<String> allowedRoles = MENU_ACCESS_MAP.get(item.getItemId());
            item.setVisible(allowedRoles == null || allowedRoles.contains(userRole));
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Set<String> allowedRoles = MENU_ACCESS_MAP.get(itemId);

                // Verifica permissão
                if (allowedRoles != null && !allowedRoles.contains(userRole)) {
                    ToastUtils.showShort(activity, activity.getString(R.string.acesso_nao_disponivel));
                }
                // Função ainda não implementada
                else if (!MENU_ACTIVITY_MAP.containsKey(itemId)) {
                    ToastUtils.showShort(activity, activity.getString(R.string.funcao_em_desenvolvimento));
                }
                // Abre nova tela
                else {
                    Class<? extends Activity> targetActivity = MENU_ACTIVITY_MAP.get(itemId);
                    if (!targetActivity.isInstance(activity)) {
                        activity.startActivity(new Intent(activity, targetActivity));
                    } else {
                        ToastUtils.showShort(activity, activity.getString(R.string.nao_pode_abrir_tela_repetida));
                    }
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}
