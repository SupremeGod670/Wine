package com.example.wine.ui.SaleDisplay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R;
import com.example.wine.data.local.AppDatabaseProvider;
import com.example.wine.data.datasource.client.ClientLocalDataSource;
import com.example.wine.data.datasource.sale.SaleLocalDataSource;
import com.example.wine.ui.viewmodel.RouteOptimizationViewModel;
import com.example.wine.utils.NavigationUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class RouteOptimizationActivity extends AppCompatActivity {

    private RouteOptimizationViewModel viewModel;
    private SaleRouteAdapter adapter;
    private EditText editTextFilterClient;
    private EditText editTextFilterDate;
    private Button buttonApplyFilters;
    private Button buttonGenerateRoute;
    private RecyclerView recyclerViewSales;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton openMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_optimization);

        // Menu lateral
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        openMenuButton = findViewById(R.id.open_menu);

        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.menu_all);
        NavigationUtils.setupNavigation(this, navigationView, drawerLayout);

        openMenuButton.setOnClickListener(v -> drawerLayout.open());

        // DataSources e ViewModel
        ClientLocalDataSource clientLocalDataSource = new ClientLocalDataSource(AppDatabaseProvider.getDatabase(this).clientDao());
        SaleLocalDataSource saleLocalDataSource = new SaleLocalDataSource(AppDatabaseProvider.getDatabase(this).saleDao());

        viewModel = new ViewModelProvider(this, new RouteOptimizationViewModel.Factory(saleLocalDataSource, clientLocalDataSource))
                .get(RouteOptimizationViewModel.class);

        // UI components
        editTextFilterClient = findViewById(R.id.editTextFilterClient);
        editTextFilterDate = findViewById(R.id.editTextFilterDate);
        buttonApplyFilters = findViewById(R.id.buttonApplyFilters);
        buttonGenerateRoute = findViewById(R.id.buttonGenerateRoute);
        recyclerViewSales = findViewById(R.id.recyclerViewSales);

        adapter = new SaleRouteAdapter(new ArrayList<>());
        recyclerViewSales.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSales.setAdapter(adapter);

        // Ações dos botões
        buttonApplyFilters.setOnClickListener(v -> {
            String clientFilter = editTextFilterClient.getText().toString();
            String dateFilter = editTextFilterDate.getText().toString();
            viewModel.loadSales(clientFilter, dateFilter);
        });

        buttonGenerateRoute.setOnClickListener(v -> generateRoute());

        viewModel.sales.observe(this, salesList -> adapter.updateList(salesList));

        viewModel.loadSales(null, null); // Sem filtros inicialmente
    }

    private void generateRoute() {
        List<SaleDisplayModel> selectedSales = adapter.getSelectedSales();

        if (selectedSales.isEmpty()) {
            Toast.makeText(this, "Selecione pelo menos um pedido para gerar a rota.", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder uriBuilder = new StringBuilder("https://www.google.com/maps/dir/?api=1");

        List<String> waypoints = new ArrayList<>();
        String finalDestination = "";

        for (int i = 0; i < selectedSales.size(); i++) {
            SaleDisplayModel sale = selectedSales.get(i);
            String location = sale.getClientLatitude() + "," + sale.getClientLongitude();

            if (i == selectedSales.size() - 1) {
                finalDestination = location;
            } else {
                waypoints.add(location);
            }
        }

        if (finalDestination.isEmpty() && !waypoints.isEmpty()) {
            finalDestination = waypoints.remove(0);
        }

        if (!waypoints.isEmpty()) {
            uriBuilder.append("&waypoints=");
            for (int i = 0; i < waypoints.size(); i++) {
                uriBuilder.append(waypoints.get(i));
                if (i < waypoints.size() - 1) {
                    uriBuilder.append("|");
                }
            }
        }

        if (!finalDestination.isEmpty()) {
            uriBuilder.append("&destination=").append(finalDestination);
        }

        uriBuilder.append("&travelmode=driving");

        Uri gmmIntentUri = Uri.parse(uriBuilder.toString());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Aplicativo Google Maps não encontrado. Abrindo no navegador.", Toast.LENGTH_LONG).show();
            Intent webIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            startActivity(webIntent);
        }
    }
}
