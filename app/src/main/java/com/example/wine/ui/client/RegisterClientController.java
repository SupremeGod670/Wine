package com.example.wine.ui.client;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.wine.data.datasource.client.ClientLocalDataSource;
import com.example.wine.data.datasource.user.AppUserLocalDataSource;
import com.example.wine.data.local.AppDatabaseProvider;
import com.example.wine.data.local.dao.AppUserDao;
import com.example.wine.data.local.dao.ClientDao;
import com.example.wine.domain.model.AppUser;
import com.example.wine.domain.model.Client;
import com.example.wine.utils.HashUtils;
import com.example.wine.utils.ToastUtils;
import com.example.wine.utils.LogUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterClientController {

    private static final String TAG = "RegisterClientController";

    private final Activity activity;
    private final AppUserLocalDataSource userDataSource;
    private final ClientLocalDataSource clientDataSource;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public RegisterClientController(Activity activity) {
        this.activity = activity;
        AppUserDao userDao = AppDatabaseProvider.getDatabase(activity.getApplicationContext()).appUserDao();
        ClientDao clientDao = AppDatabaseProvider.getDatabase(activity.getApplicationContext()).clientDao();
        this.userDataSource = new AppUserLocalDataSource(userDao);
        this.clientDataSource = new ClientLocalDataSource(clientDao);
    }

    public boolean validateInput(String name, String email, String password) {
        return !name.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }

    public void registerClient(String name, String email, String password) {
        executor.execute(() -> {
            Log.d(TAG, "Iniciando registro de cliente...");
            try {
                // Criação do AppUser
                String userId = UUID.randomUUID().toString();
                AppUser user = new AppUser();
                user.setId(userId);
                user.setName(name);
                user.setEmail(email);
                user.setPasswordHash(HashUtils.sha256(password));
                user.setRole("CLIENT");
                user.setApproved(false); // aguardando aprovação
                user.setSynced(false);
                user.setUpdatedAt(System.currentTimeMillis());
                user.setDeleted(false);

                userDataSource.insert(user);
                Log.d(TAG, "Usuário inserido com sucesso.");

                // Criação do Client
                Client client = new Client();
                client.setId(UUID.randomUUID().toString());
                client.setUserId(userId);
                client.setName(name);
                client.setEmail(email);
                client.setPhone("");
                client.setCity("");
                client.setRegionId(null);
                client.setLatitude(0.0);
                client.setLongitude(0.0);
                client.setObservation("");
                client.setApproved(false);
                client.setApprovedBy(null);
                client.setApprovedAt(null);
                client.setSynced(false);
                client.setUpdatedAt(System.currentTimeMillis());
                client.setDeleted(false);

                clientDataSource.insert(client);
                Log.d(TAG, "Cliente inserido com sucesso.");

                // Testes de Log
                Log.d(TAG, "→ Vai buscar todos os clientes...");
                List<Client> allClients = clientDataSource.getAll();
                Log.d(TAG, "→ Lista recuperada. Total de clientes: " + (allClients == null ? "null" : allClients.size()));

                if (allClients != null) {
                    LogUtils.logClients(allClients);
                }

                uiHandler.post(() -> {
                    ToastUtils.showShort(activity, "Cadastro enviado para análise. Aguarde a aprovação.");
                    activity.finish();
                });

            } catch (Exception e) {
                Log.e(TAG, "Erro no cadastro de cliente: ", e);
                uiHandler.post(() -> ToastUtils.showLong(activity, "Erro ao cadastrar cliente: " + e.getMessage()));
            }
        });
    }
}
