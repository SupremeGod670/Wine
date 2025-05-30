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
// Importe aqui outras Activities quando criar

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NavigationUtils {

    // Mapa de item do menu para Activity de destino
    private static final Map<Integer, Class<? extends Activity>> MENU_ACTIVITY_MAP = new HashMap<>();
    static {
        MENU_ACTIVITY_MAP.put(R.id.vinhos, WineListActivity.class);
        MENU_ACTIVITY_MAP.put(R.id.clientes, ClientRegisterActivity.class);
        // MENU_ACTIVITY_MAP.put(R.id.adm, CadastrarADMActivity.class); // Apenas admin
        // MENU_ACTIVITY_MAP.put(R.id.representantes, CadastrarRepresentanteActivity.class); // Apenas admin
        // MENU_ACTIVITY_MAP.put(R.id.vrepresentantes, VisualizarRepresentantesActivity.class);
        // MENU_ACTIVITY_MAP.put(R.id.pedidosecomissoes, PedidosComissoesActivity.class);
        // MENU_ACTIVITY_MAP.put(R.id.emissao, EmissaoPedidosActivity.class);
        // MENU_ACTIVITY_MAP.put(R.id.vinicola, VinicolasActivity.class);
    }

    // Mapa de permissões de acesso (por perfil) para cada item
    private static final Map<Integer, Set<String>> MENU_ACCESS_MAP = new HashMap<>();
    static {
        // Só ADMIN pode cadastrar outros ADMs
        Set<String> admPerm = new HashSet<>();
        admPerm.add("ADMIN");
        MENU_ACCESS_MAP.put(R.id.adm, admPerm);

        // Só ADMIN pode cadastrar representantes
        Set<String> repCadPerm = new HashSet<>();
        repCadPerm.add("ADMIN");
        MENU_ACCESS_MAP.put(R.id.representantes, repCadPerm);

        // Só REPRESENTANTE pode cadastrar/aprovar clientes
        Set<String> cliPerm = new HashSet<>();
        cliPerm.add("REPRESENTATIVE");
        MENU_ACCESS_MAP.put(R.id.clientes, cliPerm);

        // Todos podem acessar vinhos (ajuste se quiser restrição)
        Set<String> vinhosPerm = new HashSet<>();
        vinhosPerm.add("ADMIN");
        vinhosPerm.add("REPRESENTATIVE");
        vinhosPerm.add("CLIENT");
        MENU_ACCESS_MAP.put(R.id.vinhos, vinhosPerm);

        // Adicione para os outros menus conforme necessidade
    }

    public static void setupNavigation(final Activity activity, NavigationView navigationView, final DrawerLayout drawerLayout) {
        String userRole = AccessUtils.getUserRole(activity);

        // Controle de visibilidade conforme o perfil do usuário
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

                // 1. Validação de acesso
                if (allowedRoles != null && !allowedRoles.contains(userRole)) {
                    ToastUtils.showShort(activity, activity.getString(R.string.acesso_nao_disponivel));
                }
                // 2. Validação se tela está implementada
                else if (!MENU_ACTIVITY_MAP.containsKey(itemId)) {
                    ToastUtils.showShort(activity, activity.getString(R.string.funcao_em_desenvolvimento));
                }
                // 3. Validação se já está na mesma tela
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
