// Caminho: com.example.wine.ui.viewmodel/RepresentativeListViewModel.java
package com.example.wine.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.wine.data.datasource.representative.RepresentativeLocalDataSource;
import com.example.wine.data.datasource.user.AppUserLocalDataSource; // Para buscar o nome/email do AppUser
import com.example.wine.domain.model.Representative;
import com.example.wine.domain.model.AppUser;
import com.example.wine.ui.representative.RepresentativeDisplayModel;
import com.example.wine.utils.Mapper; // Precisaremos adicionar métodos de mapeamento aqui
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RepresentativeListViewModel extends ViewModel {

    private final RepresentativeLocalDataSource representativeLocalDataSource;
    private final AppUserLocalDataSource appUserLocalDataSource;
    private final ExecutorService executorService;

    private final MutableLiveData<List<RepresentativeDisplayModel>> _representatives = new MutableLiveData<>();
    public LiveData<List<RepresentativeDisplayModel>> representatives = _representatives;

    public RepresentativeListViewModel(RepresentativeLocalDataSource representativeLocalDataSource, AppUserLocalDataSource appUserLocalDataSource) {
        this.representativeLocalDataSource = representativeLocalDataSource;
        this.appUserLocalDataSource = appUserLocalDataSource;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void loadRepresentatives() {
        executorService.execute(() -> {
            List<Representative> allRepresentatives = representativeLocalDataSource.getAll();
            List<RepresentativeDisplayModel> displayModels = new ArrayList<>();

            for (Representative rep : allRepresentatives) {
                AppUser appUser = appUserLocalDataSource.getById(rep.getUserId());
                String name = (appUser != null) ? appUser.getName() : "Nome Desconhecido";
                String email = (appUser != null) ? appUser.getEmail() : "Email Desconhecido";
                displayModels.add(new RepresentativeDisplayModel(rep.getId(), name, rep.getPhone(), email));
            }
            _representatives.postValue(displayModels);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }

    // Factory para instanciar o ViewModel com as dependências
    public static class Factory implements ViewModelProvider.Factory {
        private final RepresentativeLocalDataSource representativeLocalDataSource;
        private final AppUserLocalDataSource appUserLocalDataSource;

        public Factory(RepresentativeLocalDataSource representativeLocalDataSource, AppUserLocalDataSource appUserLocalDataSource) {
            this.representativeLocalDataSource = representativeLocalDataSource;
            this.appUserLocalDataSource = appUserLocalDataSource;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(RepresentativeListViewModel.class)) {
                return (T) new RepresentativeListViewModel(representativeLocalDataSource, appUserLocalDataSource);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}