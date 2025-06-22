package com.example.wine.ui.Users.Client;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.example.wine.data.datasource.client.ClientLocalDataSource;
import com.example.wine.data.datasource.user.AppUserLocalDataSource;
import com.example.wine.data.local.AppDatabaseProvider;
import com.example.wine.data.local.dao.AppUserDao;
import com.example.wine.data.local.dao.ClientDao;
import com.example.wine.domain.model.AppUser;
import com.example.wine.domain.model.Client;
import com.example.wine.utils.HashUtils;
import com.example.wine.utils.ToastUtils;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterClientByAdminController {

    private final Activity activity;
    private final AppUserLocalDataSource userDataSource;
    private final ClientLocalDataSource clientDataSource;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public RegisterClientByAdminController(Activity activity) {
        this.activity = activity;
        AppUserDao userDao = AppDatabaseProvider.getDatabase(activity.getApplicationContext()).appUserDao();
        ClientDao clientDao = AppDatabaseProvider.getDatabase(activity.getApplicationContext()).clientDao();
        this.userDataSource = new AppUserLocalDataSource(userDao);
        this.clientDataSource = new ClientLocalDataSource(clientDao);
    }

    public boolean validateInput(String name, String email, String password, String phone) {
        return !name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !phone.isEmpty();
    }

    public void registerClient(String name, String email, String password, String phone) {
        executor.execute(() -> {
            try {
                // Criação do AppUser
                String userId = UUID.randomUUID().toString();
                AppUser user = new AppUser();
                user.setId(userId);
                user.setName(name);
                user.setEmail(email);
                user.setPasswordHash(HashUtils.sha256(password));
                user.setRole("CLIENT");
                user.setApproved(true);
                user.setSynced(false);
                user.setUpdatedAt(System.currentTimeMillis());
                user.setDeleted(false);

                userDataSource.insert(user);

                // Criação do Client
                Client client = new Client();
                client.setId(UUID.randomUUID().toString());
                client.setUserId(userId);
                client.setName(name);
                client.setEmail(email);
                client.setPhone(phone);
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

                uiHandler.post(() -> {
                    ToastUtils.showShort(activity, "Cliente cadastrado com sucesso!");
                    activity.finish();
                });

            } catch (Exception e) {
                uiHandler.post(() ->
                        ToastUtils.showLong(activity, "Erro ao cadastrar cliente: " + e.getMessage())
                );
            }
        });
    }
}
