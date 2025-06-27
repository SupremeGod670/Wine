package com.example.wine.ui.Users.Client.controller;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.example.wine.data.datasource.client.ClientLocalDataSource;
import com.example.wine.data.datasource.user.AppUserLocalDataSource;
import com.example.wine.domain.model.AppUser;
import com.example.wine.domain.model.Client;
import com.example.wine.utils.AccessUtils;
import com.example.wine.utils.ToastUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApproveClientController {

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

    public void approve(Client client, AppUser user) {
        executor.execute(() -> {
            try {
                long now = System.currentTimeMillis();
                String approverId = AccessUtils.getLoggedUserId(activity);

                // Atualiza Client
                client.setApproved(true);
                client.setApprovedBy(approverId);
                client.setApprovedAt(now);
                client.setUpdatedAt(now);
                clientDataSource.update(client);

                // Atualiza AppUser
                user.setApproved(true);
                user.setUpdatedAt(now);
                userDataSource.update(user);

                uiHandler.post(() -> ToastUtils.showShort(activity, "Cliente aprovado com sucesso!"));

            } catch (Exception e) {
                uiHandler.post(() -> ToastUtils.showLong(activity, "Erro ao aprovar cliente: " + e.getMessage()));
            }
        });
    }
}
