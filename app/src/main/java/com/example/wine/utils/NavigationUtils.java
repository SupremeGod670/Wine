package com.example.wine.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.wine.R;
import com.example.wine.ui.admin.RegisterAdminActivity;
import com.example.wine.ui.admin.RegisterRepresentativeActivity;
import com.example.wine.ui.client.ClientRegisterActivity;
import com.example.wine.ui.winery.WineryFormActivity;
import com.example.wine.ui.wine.form.WineFormActivity;
import com.example.wine.ui.wine.list.WineListActivity;
import com.google.android.material.navigation.NavigationView;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NavigationUtils {

    private static final Map<Integer, Class<? extends Activity>> MENU_ACTIVITY_MAP = new HashMap<>();
    static {
        // Cadastrar
        MENU_ACTIVITY_MAP.put(R.id.adm, RegisterAdminActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.representantes, RegisterRepresentativeActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.clientes, ClientRegisterActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.vinhos, WineFormActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.CadastrarVinicula, WineryFormActivity.class);
        //MENU_ACTIVITY_MAP.put(R.id.CadastrarPedido, RegisterOrderActivity.class);

        // Visualizar
        MENU_ACTIVITY_MAP.put(R.id.vvinhos, WineListActivity.class);
        // MENU_ACTIVITY_MAP.put(R.id.vrepresentantes, ViewRepresentativesActivity.class); // se/quando existir
    }

    private static final Map<Integer, Set<String>> MENU_ACCESS_MAP = new HashMap<>();
    static {
        Set<String> ADMIN = setOf("ADMIN");
        Set<String> REP = setOf("REPRESENTATIVE");
        Set<String> CLIENT = setOf("CLIENT");
        Set<String> ADMIN_REP = setOf("ADMIN", "REPRESENTATIVE");
        Set<String> ALL_USERS = setOf("ADMIN", "REPRESENTATIVE", "CLIENT");

        // ADMIN-only
        MENU_ACCESS_MAP.put(R.id.adm, ADMIN);
        MENU_ACCESS_MAP.put(R.id.representantes, ADMIN);
        MENU_ACCESS_MAP.put(R.id.CadastrarVinicula, ADMIN);

        // REP + ADMIN
        MENU_ACCESS_MAP.put(R.id.clientes, ADMIN_REP);
        MENU_ACCESS_MAP.put(R.id.vinhos, ADMIN_REP);
        MENU_ACCESS_MAP.put(R.id.CadastrarPedido, ADMIN_REP);

        // Visualizações abertas a todos
        MENU_ACCESS_MAP.put(R.id.vvinhos, ALL_USERS);
        MENU_ACCESS_MAP.put(R.id.vrepresentantes, ADMIN);
        MENU_ACCESS_MAP.put(R.id.vadm, ADMIN);
        MENU_ACCESS_MAP.put(R.id.vpedido, ADMIN_REP);
    }

    private static Set<String> setOf(String... roles) {
        Set<String> set = new HashSet<>();
        for (String role : roles) set.add(role);
        return set;
    }

    public static void setupNavigation(final Activity activity, NavigationView navigationView, final DrawerLayout drawerLayout) {
        String userRole = AccessUtils.getUserRole(activity);

        // Aplica visibilidade por papel
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            MenuItem item = navigationView.getMenu().getItem(i);
            Set<String> allowedRoles = MENU_ACCESS_MAP.get(item.getItemId());
            item.setVisible(allowedRoles == null || allowedRoles.contains(userRole));
        }

        navigationView.setNavigationItemSelectedListener(item -> {
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
        });
    }
}
