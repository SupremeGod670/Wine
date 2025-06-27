package com.example.wine.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wine.R;
import com.example.wine.ui.SaleCreateDisplay.CreateSaleActivity;
import com.example.wine.ui.SaleDisplay.RouteOptimizationActivity;
import com.example.wine.ui.Users.Admin.RegisterAdminActivity;
import com.example.wine.ui.Users.Client.RegisterClientByAdminActivity;
import com.example.wine.ui.Users.Client.UnapprovedUsersActivity;
import com.example.wine.ui.Users.Representative.RegisterRepresentativeActivity;
import com.example.wine.ui.adminDisplay.AdminListActivity;
import com.example.wine.ui.representative.RepresentativeListActivity;
import com.example.wine.ui.wine.form.WineFormActivity;
import com.example.wine.ui.wine.list.WineListActivity;
import com.example.wine.ui.winery.form.WineryFormActivity;
import com.example.wine.ui.winery.list.WineryListActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NavigationUtils {

    // 🔗 Mapeamento dos itens do menu para suas Activities correspondentes
    private static final Map<Integer, Class<? extends Activity>> MENU_ACTIVITY_MAP = new HashMap<>();
    static {
        // Cadastros
        MENU_ACTIVITY_MAP.put(R.id.adicionar_admin, RegisterAdminActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.adicionar_representante, RegisterRepresentativeActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.adicionar_cliente, RegisterClientByAdminActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.aprovar_clientes, UnapprovedUsersActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.adicionar_vinho, WineFormActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.adicionar_vinicola, WineryFormActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.adicionar_pedido, CreateSaleActivity.class);

        // Visualização
        MENU_ACTIVITY_MAP.put(R.id.visualizar_admins, AdminListActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.visualizar_representantes, RepresentativeListActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.visualizar_vinhos, WineListActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.visualizar_vinicolas, WineryListActivity.class);
        // Adicione aqui sua activity de lista de pedidos se houver

        // Operações
        MENU_ACTIVITY_MAP.put(R.id.otimizar_rota, RouteOptimizationActivity.class);
    }

    // 🔐 Controle de acesso por perfil de usuário
    private static final Map<Integer, Set<String>> MENU_ACCESS_MAP = new HashMap<>();
    static {
        Set<String> adminOnly = new HashSet<>();
        adminOnly.add("ADMIN");

        Set<String> repOnly = new HashSet<>();
        repOnly.add("REPRESENTATIVE");

        Set<String> both = new HashSet<>();
        both.add("ADMIN");
        both.add("REPRESENTATIVE");

        // Cadastros
        MENU_ACCESS_MAP.put(R.id.adicionar_admin, adminOnly);
        MENU_ACCESS_MAP.put(R.id.adicionar_representante, adminOnly);
        MENU_ACCESS_MAP.put(R.id.adicionar_cliente, adminOnly);
        MENU_ACCESS_MAP.put(R.id.aprovar_clientes, both);

        MENU_ACCESS_MAP.put(R.id.adicionar_vinho, both);
        MENU_ACCESS_MAP.put(R.id.adicionar_vinicola, both);
        MENU_ACCESS_MAP.put(R.id.adicionar_pedido, both);

        // Visualizações
        MENU_ACCESS_MAP.put(R.id.visualizar_admins, adminOnly);
        MENU_ACCESS_MAP.put(R.id.visualizar_representantes, adminOnly);
        MENU_ACCESS_MAP.put(R.id.visualizar_clientes, both);
        MENU_ACCESS_MAP.put(R.id.visualizar_vinhos, both);
        MENU_ACCESS_MAP.put(R.id.visualizar_vinicolas, both);

        // Operações
        MENU_ACCESS_MAP.put(R.id.otimizar_rota, both);
    }

    // 🚀 Configura o Navigation Drawer
    public static void setupNavigation(final Activity activity, NavigationView navigationView, final DrawerLayout drawerLayout) {
        String userRole = AccessUtils.getUserRole(activity);

        // Oculta ou mostra itens do menu conforme o papel do usuário
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            MenuItem item = navigationView.getMenu().getItem(i);
            Set<String> allowedRoles = MENU_ACCESS_MAP.get(item.getItemId());
            item.setVisible(allowedRoles == null || allowedRoles.contains(userRole));
        }

        // Listener de clique nos itens
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Set<String> allowedRoles = MENU_ACCESS_MAP.get(itemId);

                if (allowedRoles != null && !allowedRoles.contains(userRole)) {
                    ToastUtils.showShort(activity, activity.getString(R.string.acesso_nao_disponivel));
                } else if (!MENU_ACTIVITY_MAP.containsKey(itemId)) {
                    ToastUtils.showShort(activity, activity.getString(R.string.funcao_em_desenvolvimento));
                } else {
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
