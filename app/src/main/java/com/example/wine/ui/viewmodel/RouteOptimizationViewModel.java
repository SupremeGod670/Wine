// Caminho: com.example.wine.ui.viewmodel/RouteOptimizationViewModel.java
package com.example.wine.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.wine.data.datasource.client.ClientLocalDataSource;
import com.example.wine.data.datasource.sale.SaleLocalDataSource;
import com.example.wine.domain.model.Sale;
import com.example.wine.domain.model.Client;
import com.example.wine.ui.SaleDisplay.SaleDisplayModel;
import com.example.wine.utils.Mapper; // Precisaremos adicionar métodos de mapeamento aqui
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RouteOptimizationViewModel extends ViewModel {

    private final SaleLocalDataSource saleLocalDataSource;
    private final ClientLocalDataSource clientLocalDataSource;
    private final ExecutorService executorService; // Para executar operações de DB em background

    private final MutableLiveData<List<SaleDisplayModel>> _sales = new MutableLiveData<>();
    public LiveData<List<SaleDisplayModel>> sales = _sales;

    public RouteOptimizationViewModel(SaleLocalDataSource saleLocalDataSource, ClientLocalDataSource clientLocalDataSource) {
        this.saleLocalDataSource = saleLocalDataSource;
        this.clientLocalDataSource = clientLocalDataSource;
        this.executorService = Executors.newSingleThreadExecutor(); // Um thread para operações de DB
    }

    public void loadSales(String clientFilter, String dateFilter) {
        executorService.execute(() -> {
            List<Sale> allSales = saleLocalDataSource.getAll();
            List<Client> allClients = clientLocalDataSource.getAll(); // Carrega todos os clientes para mapeamento

            List<SaleDisplayModel> filteredSales = new ArrayList<>();

            for (Sale sale : allSales) {
                // Encontrar o cliente correspondente à venda
                Client client = null;
                for (Client c : allClients) {
                    if (c.getId().equals(sale.getClientId())) {
                        client = c;
                        break;
                    }
                }

                if (client != null) {
                    boolean matchesClient = (clientFilter == null || clientFilter.isEmpty()) ||
                            client.getName().toLowerCase(Locale.getDefault()).contains(clientFilter.toLowerCase(Locale.getDefault()));

                    boolean matchesDate = (dateFilter == null || dateFilter.isEmpty());
                    if (!matchesDate) {
                        try {
                            // Formato da data de entrada AAAA-MM-DD
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            Date filterDate = inputFormat.parse(dateFilter);
                            // Comparar apenas a data, ignorando a hora
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            matchesDate = dateFormat.format(new Date(sale.getSaleDate())).equals(dateFormat.format(filterDate));
                        } catch (Exception e) {
                            // Tratar erro de formato de data se necessário, por enquanto, considera não compatível
                            matchesDate = false;
                        }
                    }

                    if (matchesClient && matchesDate) {
                        filteredSales.add(Mapper.toSaleDisplayModel(sale, client));
                    }
                }
            }
            _sales.postValue(filteredSales); // Atualiza a LiveData na thread principal
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown(); // Garante que o executor seja desligado
    }

    // Factory para instanciar o ViewModel com as dependências
    public static class Factory implements ViewModelProvider.Factory {
        private final SaleLocalDataSource saleLocalDataSource;
        private final ClientLocalDataSource clientLocalDataSource;

        public Factory(SaleLocalDataSource saleLocalDataSource, ClientLocalDataSource clientLocalDataSource) {
            this.saleLocalDataSource = saleLocalDataSource;
            this.clientLocalDataSource = clientLocalDataSource;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(RouteOptimizationViewModel.class)) {
                return (T) new RouteOptimizationViewModel(saleLocalDataSource, clientLocalDataSource);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}