package com.example.wine.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wine.R;
import com.example.wine.ui.client.ClientRegisterActivity;
import com.example.wine.ui.wine.list.WineListActivity;
// Importe suas outras Activities aqui
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

public class NavigationUtils {

    private static final Map<Integer, Class<? extends Activity>> MENU_ACTIVITY_MAP = new HashMap<>();
    static {
        MENU_ACTIVITY_MAP.put(R.id.vinhos, WineListActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.clientes, ClientRegisterActivity.class);
        // MENU_ACTIVITY_MAP.put(R.id.adm, CadastrarADMActivity.class); // Descomente e crie caso exista
        // MENU_ACTIVITY_MAP.put(R.id.representantes, SuaActivityDeRepresentantes.class);
        // MENU_ACTIVITY_MAP.put(R.id.vrepresentantes, VisualizarRepresentantesActivity.class);
        // MENU_ACTIVITY_MAP.put(R.id.pedidosecomissoes, PedidosComissoesActivity.class);
        // MENU_ACTIVITY_MAP.put(R.id.emissao, EmissaoPedidosActivity.class);
        // MENU_ACTIVITY_MAP.put(R.id.vinicola, VinicolasActivity.class);
    }

    public static void setupNavigation(final Activity activity, NavigationView navigationView, final DrawerLayout drawerLayout) {
        String userRole = AccessUtils.getUserRole(activity);

        if (navigationView.getMenu().findItem(R.id.adm) != null)
            navigationView.getMenu().findItem(R.id.adm).setVisible("ADMIN".equals(userRole));

        if (navigationView.getMenu().findItem(R.id.representantes) != null)
            navigationView.getMenu().findItem(R.id.representantes).setVisible("REPRESENTATIVE".equals(userRole) || "ADMIN".equals(userRole));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Class<? extends Activity> targetActivity = MENU_ACTIVITY_MAP.get(itemId);

                if (targetActivity != null) {
                    if (!targetActivity.isInstance(activity)) {
                        activity.startActivity(new Intent(activity, targetActivity));
                    } else {
                        ToastUtils.showShort(activity, activity.getString(R.string.nao_pode_abrir_tela_repetida));
                    }
                } else {
                    ToastUtils.showShort(activity, activity.getString(R.string.acesso_nao_disponivel));
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}
