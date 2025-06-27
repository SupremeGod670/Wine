package com.example.wine.ui.client;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.wine.data.datasource.client.ClientLocalDataSource;
import com.example.wine.data.local.AppDatabaseProvider;
import com.example.wine.data.local.dao.ClientDao;
import com.example.wine.domain.model.Client;
import com.example.wine.utils.LogUtils;
import com.example.wine.utils.ToastUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterClientController {

    private static final String TAG = "RegisterClientController";

    private final Activity activity;
    private final ClientLocalDataSource clientDataSource;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public RegisterClientController(Activity activity) {
        this.activity = activity;
        ClientDao clientDao = AppDatabaseProvider.getDatabase(activity.getApplicationContext()).clientDao();
        this.clientDataSource = new ClientLocalDataSource(clientDao);
    }

    public boolean validateInput(String name, String email, String password) {
        return !name.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }

    public void registerClient(String name, String email, String password, double latitude, double longitude) {
        executor.execute(() -> {
            try {
                Log.d(TAG, "Iniciando cadastro de cliente...");

                Client client = new Client();
                client.setId(UUID.randomUUID().toString());
                client.setUserId(null);
                client.setName(name);
                client.setEmail(email);
                client.setPhone("");
                client.setCity("");
                client.setRegionId(null);
                client.setLatitude(latitude);
                client.setLongitude(longitude);
                client.setObservation("");
                client.setApproved(false);
                client.setApprovedBy(null);
                client.setApprovedAt(null);
                client.setSynced(false);
                client.setUpdatedAt(System.currentTimeMillis());
                client.setDeleted(false);

                clientDataSource.insert(client);

                List<Client> allClients = clientDataSource.getAll();
                if (allClients.isEmpty()) {
                    Log.d(TAG, "Nenhum cliente cadastrado.");
                } else {
                    Log.d(TAG, "=== Lista de clientes cadastrados ===");
                    for (Client c : allClients) {
                        Log.d(TAG, "ID: " + c.getId()
                                + " | Nome: " + c.getName()
                                + " | Email: " + c.getEmail()
                                + " | Aprovado: " + c.isApproved()
                                + " | userId: " + c.getUserId());
                    }
                }

                uiHandler.post(() -> {
                    ToastUtils.showShort(activity, "Cadastro enviado para análise.");
                    activity.finish();
                });

            } catch (Exception e) {
                LogUtils.error(TAG, "Erro ao cadastrar cliente: " + e.getMessage());
                uiHandler.post(() ->
                        ToastUtils.showLong(activity, "Erro ao cadastrar cliente: " + e.getMessage())
                );
            }
        });
    }
}
