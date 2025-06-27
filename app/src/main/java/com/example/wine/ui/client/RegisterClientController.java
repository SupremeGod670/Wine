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

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterClientController {

    private final Activity activity;
    private final AppUserLocalDataSource userDataSource;
    private final ClientLocalDataSource clientDataSource;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public RegisterClientController(Activity activity) {
        this.activity = activity;
        AppUserDao userDao = AppDatabaseProvider.getDatabase(activity).appUserDao();
        ClientDao clientDao = AppDatabaseProvider.getDatabase(activity).clientDao();
        this.userDataSource = new AppUserLocalDataSource(userDao);
        this.clientDataSource = new ClientLocalDataSource(clientDao);
    }

    public boolean validateInput(String name, String email, String password) {
        return !name.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }

    public void registerClient(String name, String email, String password,
                               String phone, String city, String observation,
                               double latitude, double longitude) {
        executor.execute(() -> {
            try {
                String userId = UUID.randomUUID().toString();
                long now = System.currentTimeMillis();

                // Criação do AppUser
                AppUser user = new AppUser();
                user.setId(userId);
                user.setName(name);
                user.setEmail(email);
                user.setPasswordHash(HashUtils.sha256(password));
                user.setRole("CLIENT");
                user.setApproved(false);
                user.setSynced(false);
                user.setUpdatedAt(now);
                user.setDeleted(false);
                userDataSource.insert(user);

                // Criação do Client
                Client client = new Client();
                client.setId(UUID.randomUUID().toString());
                client.setUserId(userId);
                client.setName(name);
                client.setEmail(email);
                client.setPhone(phone);
                client.setCity(city);
                client.setObservation(observation);
                client.setRegionId(null);
                client.setLatitude(latitude);
                client.setLongitude(longitude);
                client.setApproved(false);
                client.setApprovedBy(null);
                client.setApprovedAt(null);
                client.setSynced(false);
                client.setUpdatedAt(now);
                client.setDeleted(false);
                clientDataSource.insert(client);

                // LOG DETALHADO
                List<Client> allClients = clientDataSource.getAll();
                Log.d("CLIENTS_LOG", "---- Lista de Clientes ----");
                for (Client c : allClients) {
                    Log.d("CLIENTS_LOG", "Nome: " + c.getName()
                            + " | Email: " + c.getEmail()
                            + " | Phone: " + c.getPhone()
                            + " | Cidade: " + c.getCity()
                            + " | Obs: " + c.getObservation()
                            + " | Latitude: " + c.getLatitude()
                            + " | Longitude: " + c.getLongitude()
                            + " | Approved: " + c.isApproved());
                }

                uiHandler.post(() -> {
                    ToastUtils.showShort(activity, "Cadastro realizado com sucesso! Aguarde aprovação.");
                    activity.finish();
                });

            } catch (Exception e) {
                uiHandler.post(() ->
                        ToastUtils.showLong(activity, "Erro ao cadastrar cliente: " + e.getMessage()));
            }
        });
    }
}
