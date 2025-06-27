// Caminho: com.example.wine.ui.viewmodel/AdminListViewModel.java
package com.example.wine.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.wine.data.datasource.user.AppUserLocalDataSource;
import com.example.wine.domain.model.AppUser;
import com.example.wine.ui.adminDisplay.AdminDisplayModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdminListViewModel extends ViewModel {

    private final AppUserLocalDataSource appUserLocalDataSource;
    private final ExecutorService executorService;

    private final MutableLiveData<List<AdminDisplayModel>> _admins = new MutableLiveData<>();
    public LiveData<List<AdminDisplayModel>> admins = _admins;

    public AdminListViewModel(AppUserLocalDataSource appUserLocalDataSource) {
        this.appUserLocalDataSource = appUserLocalDataSource;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void loadAdmins() {
        executorService.execute(() -> {
            List<AppUser> allUsers = appUserLocalDataSource.getAll(); // Pega todos os usuários
            List<AdminDisplayModel> adminModels = new ArrayList<>();

            for (AppUser user : allUsers) {
                // Filtra por role = "ADMIN"
                if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                    adminModels.add(new AdminDisplayModel(user.getId(), user.getName(), user.getEmail(), user.getRole()));
                }
            }
            _admins.postValue(adminModels);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }

    // Factory para instanciar o ViewModel com as dependências
    public static class Factory implements ViewModelProvider.Factory {
        private final AppUserLocalDataSource appUserLocalDataSource;

        public Factory(AppUserLocalDataSource appUserLocalDataSource) {
            this.appUserLocalDataSource = appUserLocalDataSource;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(AdminListViewModel.class)) {
                return (T) new AdminListViewModel(appUserLocalDataSource);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}