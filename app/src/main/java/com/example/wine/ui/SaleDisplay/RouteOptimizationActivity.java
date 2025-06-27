package com.example.wine.ui.SaleDisplay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R;
import com.example.wine.data.local.AppDatabaseProvider; // Para obter a instância do DB
import com.example.wine.data.datasource.client.ClientLocalDataSource;
import com.example.wine.data.datasource.sale.SaleLocalDataSource;
import com.example.wine.ui.SaleDisplay.SaleRouteAdapter;
import com.example.wine.ui.SaleDisplay.SaleDisplayModel;
import com.example.wine.ui.viewmodel.RouteOptimizationViewModel;

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
    private ImageButton openMenuButton; // Se precisar do botão de menu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_optimization);

        // Inicialização dos DataSources e ViewModel
        ClientLocalDataSource clientLocalDataSource = new ClientLocalDataSource(AppDatabaseProvider.getDatabase(this).clientDao());
        SaleLocalDataSource saleLocalDataSource = new SaleLocalDataSource(AppDatabaseProvider.getDatabase(this).saleDao());

        viewModel = new ViewModelProvider(this, new RouteOptimizationViewModel.Factory(saleLocalDataSource, clientLocalDataSource))
                .get(RouteOptimizationViewModel.class);

        // Inicialização dos componentes da UI
        editTextFilterClient = findViewById(R.id.editTextFilterClient);
        editTextFilterDate = findViewById(R.id.editTextFilterDate);
        buttonApplyFilters = findViewById(R.id.buttonApplyFilters);
        buttonGenerateRoute = findViewById(R.id.buttonGenerateRoute);
        recyclerViewSales = findViewById(R.id.recyclerViewSales);
        openMenuButton = findViewById(R.id.open_menu); // Certifique-se de que o ID está correto no XML

        // Configuração da RecyclerView
        adapter = new SaleRouteAdapter(new ArrayList<>()); // Começa com lista vazia
        recyclerViewSales.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSales.setAdapter(adapter);

        // Observar as vendas do ViewModel
        viewModel.sales.observe(this, salesList -> {
            adapter.updateList(salesList);
        });

        // Ações dos botões
        buttonApplyFilters.setOnClickListener(v -> {
            String clientFilter = editTextFilterClient.getText().toString();
            String dateFilter = editTextFilterDate.getText().toString();
            viewModel.loadSales(clientFilter, dateFilter);
        });

        buttonGenerateRoute.setOnClickListener(v -> {
            generateRoute();
        });

        // TODO: Implementar a lógica do botão de menu se necessário
        openMenuButton.setOnClickListener(v -> {
            // Exemplo: Abrir um Navigation Drawer ou outra tela
            Toast.makeText(this, "Botão de menu clicado!", Toast.LENGTH_SHORT).show();
        });

        // Carregar vendas iniciais ao abrir a tela (sem filtros por padrão)
        viewModel.loadSales(null, null);
    }

    private void generateRoute() {
        List<SaleDisplayModel> selectedSales = adapter.getSelectedSales();

        if (selectedSales.isEmpty()) {
            Toast.makeText(this, "Selecione pelo menos um pedido para gerar a rota.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Construir a URL do Google Maps
        // Exemplo de URL com múltiplos destinos: https://www.google.com/maps/dir/?api=1&origin=Sua+Origem&destination=DestinoFinal&waypoints=Waypoint1|Waypoint2|...
        // Para simplificar, vamos abrir com múltiplos destinos (waypoints)
        StringBuilder uriBuilder = new StringBuilder("https://www.google.com/maps/dir/?api=1");

        // Você pode definir uma origem fixa (ex: seu armazém, localização atual do usuário)
        // Por enquanto, vamos omitir 'origin' para o Google Maps usar a localização atual do usuário como padrão.
        // uriBuilder.append("&origin=SUA_LATITUDE_ORIGEM,SUA_LONGITUDE_ORIGEM");

        List<String> waypoints = new ArrayList<>();
        // O último destino na lista se tornará o 'destination' final na URL
        String finalDestination = "";

        for (int i = 0; i < selectedSales.size(); i++) {
            SaleDisplayModel sale = selectedSales.get(i);
            String location = sale.getClientLatitude() + "," + sale.getClientLongitude();

            if (i == selectedSales.size() - 1) {
                finalDestination = location; // O último é o destino final
            } else {
                waypoints.add(location); // Os demais são waypoints
            }
        }

        if (finalDestination.isEmpty() && !waypoints.isEmpty()) {
            // Se só tiver um ponto, ele deve ser o destino, não um waypoint
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

        // Modo de viagem: "driving", "walking", "bicycling", "transit"
        uriBuilder.append("&travelmode=driving");

        // Abrir o Google Maps
        Uri gmmIntentUri = Uri.parse(uriBuilder.toString());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps"); // Tenta abrir no app do Google Maps

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Se o Google Maps não estiver instalado, abre no navegador
            Toast.makeText(this, "Aplicativo Google Maps não encontrado. Abrindo no navegador.", Toast.LENGTH_LONG).show();
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriBuilder.toString()));
            startActivity(webIntent);
        }
    }
}