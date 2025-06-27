// Caminho: package com.example.wine.ui.viewmodel/CreateSaleViewModel.java
package com.example.wine.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.wine.data.datasource.client.ClientLocalDataSource;
import com.example.wine.data.datasource.representative.RepresentativeLocalDataSource;
import com.example.wine.data.datasource.sale.SaleLocalDataSource;
import com.example.wine.data.datasource.saleitem.SaleItemLocalDataSource;
import com.example.wine.data.datasource.user.AppUserLocalDataSource;
import com.example.wine.data.datasource.wine.WineLocalDataSource;
import com.example.wine.domain.model.AppUser;
import com.example.wine.domain.model.Client;
import com.example.wine.domain.model.Representative;
import com.example.wine.domain.model.Sale;
import com.example.wine.domain.model.SaleItem;
import com.example.wine.domain.model.Wine;
import com.example.wine.ui.SaleCreateDisplay.ClientSpinnerModel;
import com.example.wine.ui.SaleCreateDisplay.RepresentativeSpinnerModel;
import com.example.wine.ui.SaleCreateDisplay.WineSaleItemModel;
import com.example.wine.utils.Mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateSaleViewModel extends ViewModel {

    private final ClientLocalDataSource clientLocalDataSource;
    private final RepresentativeLocalDataSource representativeLocalDataSource;
    private final WineLocalDataSource wineLocalDataSource;
    private final SaleLocalDataSource saleLocalDataSource;
    private final SaleItemLocalDataSource saleItemLocalDataSource;
    private final AppUserLocalDataSource appUserLocalDataSource; // << NOVO: VARIÁVEL DE INSTÂNCIA
    private final ExecutorService executorService;

    private final MutableLiveData<List<ClientSpinnerModel>> _clients = new MutableLiveData<>();
    public LiveData<List<ClientSpinnerModel>> clients = _clients;

    private final MutableLiveData<List<RepresentativeSpinnerModel>> _representatives = new MutableLiveData<>();
    public LiveData<List<RepresentativeSpinnerModel>> representatives = _representatives;

    private final MutableLiveData<List<Wine>> _availableWines = new MutableLiveData<>();
    public LiveData<List<Wine>> availableWines = _availableWines;

    private final MutableLiveData<List<WineSaleItemModel>> _currentSaleItems = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<WineSaleItemModel>> currentSaleItems = _currentSaleItems;

    private final MutableLiveData<Double> _totalSaleValue = new MutableLiveData<>(0.0);
    public LiveData<Double> totalSaleValue = _totalSaleValue;

    // Evento para notificar a UI de sucesso ou erro na criação da venda
    private final MutableLiveData<Boolean> _saleSaved = new MutableLiveData<>();
    public LiveData<Boolean> saleSaved = _saleSaved;

    public CreateSaleViewModel(
            ClientLocalDataSource clientLocalDataSource,
            RepresentativeLocalDataSource representativeLocalDataSource,
            WineLocalDataSource wineLocalDataSource,
            SaleLocalDataSource saleLocalDataSource,
            SaleItemLocalDataSource saleItemLocalDataSource,
            AppUserLocalDataSource appUserLocalDataSource) { // << NOVO: RECEBENDO NO CONSTRUTOR
        this.clientLocalDataSource = clientLocalDataSource;
        this.representativeLocalDataSource = representativeLocalDataSource;
        this.wineLocalDataSource = wineLocalDataSource;
        this.saleLocalDataSource = saleLocalDataSource;
        this.saleItemLocalDataSource = saleItemLocalDataSource;
        this.appUserLocalDataSource = appUserLocalDataSource; // << NOVO: ATRIBUINDO
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void loadInitialData() {
        executorService.execute(() -> {
            // Carrega clientes
            List<Client> clientEntities = clientLocalDataSource.getAll();
            List<ClientSpinnerModel> clientModels = new ArrayList<>();
            for (Client client : clientEntities) {
                clientModels.add(Mapper.toClientSpinnerModel(client));
            }
            _clients.postValue(clientModels);

            // Carrega representantes e busca o nome do AppUser associado
            List<Representative> representativeEntities = representativeLocalDataSource.getAll();
            Log.d("CreateSaleVM_Rep", "Representantes do DB (contagem): " + representativeEntities.size());

            List<RepresentativeSpinnerModel> representativeModels = new ArrayList<>();
            for (Representative rep : representativeEntities) {
                // AQUI o appUserLocalDataSource será usado, e agora ele é uma variável de instância válida
                AppUser appUser = appUserLocalDataSource.getById(rep.getUserId());
                String repName;
                if (appUser != null) {
                    repName = appUser.getName();
                    Log.d("CreateSaleVM_Rep", "AppUser encontrado para ID " + rep.getUserId() + ": Nome = " + repName);
                } else {
                    // Melhorar o placeholder para que seja mais descritivo se o AppUser não for encontrado
                    repName = "Representante Desconhecido (ID: " + (rep.getUserId() != null ? rep.getUserId().substring(0, Math.min(4, rep.getUserId().length())) : "N/A") + ")";
                    Log.w("CreateSaleVM_Rep", "AppUser NÃO encontrado para ID " + rep.getUserId());
                }
                representativeModels.add(new RepresentativeSpinnerModel(rep.getId(), repName));
            }
            Log.d("CreateSaleVM_Rep", "Modelos de Representantes para Spinner (final): " + representativeModels.size());
            _representatives.postValue(representativeModels);

            // Carrega vinhos disponíveis
            List<Wine> wines = wineLocalDataSource.getAll();
            Log.d("CreateSaleVM_Wine", "Vinhos carregados: " + wines.size()); // Adicionado log para vinhos
            _availableWines.postValue(wines);
        });
    }

    public void addWineItem(Wine wine) {
        List<WineSaleItemModel> currentItems = _currentSaleItems.getValue();
        if (currentItems == null) {
            currentItems = new ArrayList<>();
        }
        // Verifica se o vinho já está na lista para não duplicar, apenas ajustar quantidade
        boolean found = false;
        for (WineSaleItemModel item : currentItems) {
            if (item.getWineId().equals(wine.getId())) {
                item.setQuantity(item.getQuantity() + 1); // Incrementa quantidade
                found = true;
                break;
            }
        }
        if (!found) {
            currentItems.add(new WineSaleItemModel(wine.getId(), wine.getName(), 1, wine.getPrice()));
        }
        _currentSaleItems.postValue(currentItems);
        calculateTotal();
    }

    public void removeWineItem(int position) {
        List<WineSaleItemModel> currentItems = _currentSaleItems.getValue();
        if (currentItems != null && position >= 0 && position < currentItems.size()) {
            currentItems.remove(position);
            _currentSaleItems.postValue(currentItems);
            calculateTotal();
        }
    }

    public void updateItemQuantity(int position, int quantity) {
        List<WineSaleItemModel> currentItems = _currentSaleItems.getValue();
        if (currentItems != null && position >= 0 && position < currentItems.size()) {
            currentItems.get(position).setQuantity(quantity);
            _currentSaleItems.postValue(currentItems);
            calculateTotal();
        }
    }

    public void updateItemUnitPrice(int position, double unitPrice) {
        List<WineSaleItemModel> currentItems = _currentSaleItems.getValue();
        if (currentItems != null && position >= 0 && position < currentItems.size()) {
            currentItems.get(position).setUnitPrice(unitPrice);
            _currentSaleItems.postValue(currentItems);
            calculateTotal();
        }
    }

    public void calculateTotal() {
        double total = 0.0;
        List<WineSaleItemModel> items = _currentSaleItems.getValue();
        if (items != null) {
            for (WineSaleItemModel item : items) {
                total += item.getItemTotal();
            }
        }
        // Adicionar frete se necessário aqui, mas o frete será inserido no momento de salvar
        _totalSaleValue.postValue(total);
    }

    public void saveSale(String clientId, String representativeId, double freight, String deliveryMethod) {
        executorService.execute(() -> {
            if (clientId == null || clientId.isEmpty() || representativeId == null || representativeId.isEmpty()) {
                _saleSaved.postValue(false); // Indica falha por dados incompletos
                Log.e("CreateSaleVM", "Erro ao salvar venda: Cliente ou Representante não selecionado.");
                return;
            }

            List<WineSaleItemModel> itemsToSave = _currentSaleItems.getValue();
            if (itemsToSave == null || itemsToSave.isEmpty()) {
                _saleSaved.postValue(false); // Indica falha por não ter itens
                Log.e("CreateSaleVM", "Erro ao salvar venda: Nenhum item adicionado.");
                return;
            }

            try {
                // 1. Criar e salvar a Venda (Sale)
                Sale newSale = new Sale();
                String saleId = UUID.randomUUID().toString();
                newSale.setId(saleId);
                newSale.setClientId(clientId);
                newSale.setRepresentativeId(representativeId);
                newSale.setSaleDate(new Date().getTime()); // Data atual
                newSale.setTotal(_totalSaleValue.getValue() != null ? _totalSaleValue.getValue() + freight : freight); // Total + frete
                newSale.setFreight(freight);
                newSale.setDeliveryMethod(deliveryMethod);
                newSale.setSynced(false); // Não sincronizada ainda
                newSale.setUpdatedAt(new Date().getTime());
                newSale.setDeleted(false);

                saleLocalDataSource.insert(newSale);
                Log.d("CreateSaleVM", "Venda salva com ID: " + saleId);

                // 2. Criar e salvar os Itens de Venda (SaleItem)
                for (WineSaleItemModel itemModel : itemsToSave) {
                    SaleItem saleItem = new SaleItem();
                    saleItem.setId(UUID.randomUUID().toString());
                    saleItem.setSaleId(saleId); // Vincula ao ID da venda criada
                    saleItem.setWineId(itemModel.getWineId());
                    saleItem.setQuantity(itemModel.getQuantity());
                    saleItem.setUnitPrice(itemModel.getUnitPrice());
                    saleItem.setSynced(false);
                    saleItem.setUpdatedAt(new Date().getTime());
                    saleItem.setDeleted(false);
                    saleItemLocalDataSource.insert(saleItem);
                    Log.d("CreateSaleVM", "Item de venda salvo: Vinho ID " + itemModel.getWineId() + ", Qtd " + itemModel.getQuantity());
                }
                _saleSaved.postValue(true); // Indica sucesso
            } catch (Exception e) {
                Log.e("CreateSaleVM", "Erro ao salvar venda: " + e.getMessage(), e); // Log mais detalhado
                _saleSaved.postValue(false); // Indica falha
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }

    // Factory para instanciar o ViewModel com dependências
    public static class Factory implements ViewModelProvider.Factory {
        private final ClientLocalDataSource clientLocalDataSource;
        private final RepresentativeLocalDataSource representativeLocalDataSource;
        private final WineLocalDataSource wineLocalDataSource;
        private final SaleLocalDataSource saleLocalDataSource;
        private final SaleItemLocalDataSource saleItemLocalDataSource;
        private final AppUserLocalDataSource appUserLocalDataSource; // << NOVO: DEPENDÊNCIA NA FACTORY

        public Factory(ClientLocalDataSource clientLocalDataSource,
                       RepresentativeLocalDataSource representativeLocalDataSource,
                       WineLocalDataSource wineLocalDataSource,
                       SaleLocalDataSource saleLocalDataSource,
                       SaleItemLocalDataSource saleItemLocalDataSource,
                       AppUserLocalDataSource appUserLocalDataSource) { // << NOVO: CONSTRUTOR DA FACTORY
            this.clientLocalDataSource = clientLocalDataSource;
            this.representativeLocalDataSource = representativeLocalDataSource;
            this.wineLocalDataSource = wineLocalDataSource;
            this.saleLocalDataSource = saleLocalDataSource;
            this.saleItemLocalDataSource = saleItemLocalDataSource;
            this.appUserLocalDataSource = appUserLocalDataSource; // << NOVO: ATRIBUIÇÃO
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(CreateSaleViewModel.class)) {
                return (T) new CreateSaleViewModel(
                        clientLocalDataSource,
                        representativeLocalDataSource,
                        wineLocalDataSource,
                        saleLocalDataSource,
                        saleItemLocalDataSource,
                        appUserLocalDataSource); // << NOVO: PASSANDO PARA O VIEWMODEL
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}