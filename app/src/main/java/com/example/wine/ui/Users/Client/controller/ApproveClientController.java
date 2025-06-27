package com.example.wine.ui.Users.Client.controller;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.example.wine.data.datasource.client.ClientLocalDataSource;
import com.example.wine.data.datasource.user.AppUserLocalDataSource;
import com.example.wine.domain.model.AppUser;
import com.example.wine.domain.model.Client;
import com.example.wine.utils.AccessUtils;
import com.example.wine.utils.HashUtils;
import com.example.wine.utils.ToastUtils;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApproveClientController {

    private static final String TAG = "ApproveClientController";

    private final Activity activity;
    private final ClientLocalDataSource clientDataSource;
    private final AppUserLocalDataSource userDataSource;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public ApproveClientController(Activity activity, ClientLocalDataSource clientDataSource, AppUserLocalDataSource userDataSource) {
        this.activity = activity;
        this.clientDataSource = clientDataSource;
        this.userDataSource = userDataSource;
    }

    public void approve(Client client, AppUser existingUser, Runnable onSuccessCallback) {
        executor.execute(() -> {
            try {
                long now = System.currentTimeMillis();
                String approverId = AccessUtils.getLoggedUserId(activity);

                AppUser userToUse = existingUser;
                boolean userWasCreated = false;

                if (userToUse == null) {
                    String newUserId = client.getUserId() != null ? client.getUserId() : UUID.randomUUID().toString();

                    AppUser createdUser = new AppUser();
                    createdUser.setId(newUserId);
                    createdUser.setName(client.getName());
                    createdUser.setEmail(client.getEmail());
                    createdUser.setPasswordHash(HashUtils.sha256("123"));
                    createdUser.setRole("CLIENT");
                    createdUser.setApproved(true);
                    createdUser.setSynced(false);
                    createdUser.setUpdatedAt(now);
                    createdUser.setDeleted(false);

                    userDataSource.insert(createdUser);
                    userToUse = createdUser;
                    userWasCreated = true;
                }

                // Atualiza client
                client.setApproved(true);
                client.setApprovedBy(approverId);
                client.setApprovedAt(now);
                client.setUpdatedAt(now);
                clientDataSource.update(client);

                // Atualiza ou confirma user
                AppUser finalUser = userToUse;
                finalUser.setApproved(true);
                finalUser.setUpdatedAt(now);
                userDataSource.update(finalUser);

                boolean created = userWasCreated;

                uiHandler.post(() -> {
                    if (created) {
                        ToastUtils.showShort(activity, "Cliente e usuário criados e aprovados!");
                    } else {
                        ToastUtils.showShort(activity, "Cliente aprovado com sucesso!");
                    }
                    if (onSuccessCallback != null) onSuccessCallback.run();
                });

            } catch (Exception e) {
                uiHandler.post(() ->
                        ToastUtils.showLong(activity, "Erro ao aprovar cliente: " + e.getMessage()));
            }
        });
    }
}
