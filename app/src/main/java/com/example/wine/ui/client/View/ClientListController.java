package com.example.wine.ui.client.View;

import com.example.wine.data.datasource.client.ClientLocalDataSource;
import com.example.wine.domain.model.Client;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ClientListController {

    private final ClientLocalDataSource dataSource;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ClientListController(ClientLocalDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void getApprovedClients(Consumer<List<Client>> callback) {
        executor.execute(() -> {
            List<Client> clients = dataSource.getAllApprovedClients();
            callback.accept(clients);
        });
    }
}
