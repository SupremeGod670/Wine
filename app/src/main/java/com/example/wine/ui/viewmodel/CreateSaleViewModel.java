package com.example.wine.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.wine.data.datasource.client.ClientLocalDataSource;
import com.example.wine.data.datasource.representative.RepresentativeLocalDataSource;
import com.example.wine.data.datasource.sale.SaleLocalDataSource;
import com.example.wine.data.datasource.saleitem.SaleItemLocalDataSource;
import com.example.wine.data.datasource.wine.WineLocalDataSource; // Para buscar vinhos
import com.example.wine.domain.model.Client;
import com.example.wine.domain.model.Representative;
import com.example.wine.domain.model.Sale;
import com.example.wine.domain.model.SaleItem;
import com.example.wine.domain.model.Wine;
import com.example.wine.ui.SaleCreateDisplay.ClientSpinnerModel;
import com.example.wine.ui.SaleCreateDisplay.RepresentativeSpinnerModel;
import com.example.wine.ui.SaleCreateDisplay.WineSaleItemModel;

import com.example.wine.utils.Mapper; // Será necessário ajustar este Mapper
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
            SaleItemLocalDataSource saleItemLocalDataSource) {
        this.clientLocalDataSource = clientLocalDataSource;
        this.representativeLocalDataSource = representativeLocalDataSource;
        this.wineLocalDataSource = wineLocalDataSource;
        this.saleLocalDataSource = saleLocalDataSource;
        this.saleItemLocalDataSource = saleItemLocalDataSource;
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

            // Carrega representantes (assumindo que o nome do representante vem do AppUser associado)
            List<Representative> representativeEntities = representativeLocalDataSource.getAll();
            List<RepresentativeSpinnerModel> representativeModels = new ArrayList<>();
            // Isso é um pouco mais complexo, pois Representative tem userId, mas não o nome diretamente
            // Idealmente, você buscaria o AppUser para pegar o nome
            // Por simplicidade, vou usar o ID como nome por enquanto, ou você precisaria de um ClientLocalDataSource.getById() para AppUserEntity
            for (Representative rep : representativeEntities) {
                // Aqui você precisaria buscar o AppUser pelo rep.getUserId() e pegar o nome
                // Por agora, vou usar o ID ou um placeholder
                representativeModels.add(new RepresentativeSpinnerModel(rep.getId(), "Rep. " + rep.getUserId().substring(0,4))); // Placeholder
            }
            _representatives.postValue(representativeModels);

            // Carrega vinhos disponíveis
            List<Wine> wines = wineLocalDataSource.getAll();
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
                return;
            }

            List<WineSaleItemModel> itemsToSave = _currentSaleItems.getValue();
            if (itemsToSave == null || itemsToSave.isEmpty()) {
                _saleSaved.postValue(false); // Indica falha por não ter itens
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
                }
                _saleSaved.postValue(true); // Indica sucesso
            } catch (Exception e) {
                // Log do erro: Log.e("CreateSaleVM", "Erro ao salvar venda", e);
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

        public Factory(ClientLocalDataSource clientLocalDataSource,
                       RepresentativeLocalDataSource representativeLocalDataSource,
                       WineLocalDataSource wineLocalDataSource,
                       SaleLocalDataSource saleLocalDataSource,
                       SaleItemLocalDataSource saleItemLocalDataSource) {
            this.clientLocalDataSource = clientLocalDataSource;
            this.representativeLocalDataSource = representativeLocalDataSource;
            this.wineLocalDataSource = wineLocalDataSource;
            this.saleLocalDataSource = saleLocalDataSource;
            this.saleItemLocalDataSource = saleItemLocalDataSource;
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
                        saleItemLocalDataSource);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}