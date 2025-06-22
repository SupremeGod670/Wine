package com.example.wine.ui.Users.Admin;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.example.wine.data.datasource.user.AppUserLocalDataSource;
import com.example.wine.data.local.AppDatabaseProvider;
import com.example.wine.data.local.dao.AppUserDao;
import com.example.wine.domain.model.AppUser;
import com.example.wine.utils.HashUtils;
import com.example.wine.utils.ToastUtils;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterAdminController {

    private final Activity activity;
    private final AppUserLocalDataSource dataSource;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public RegisterAdminController(Activity activity) {
        this.activity = activity;
        AppUserDao dao = AppDatabaseProvider.getDatabase(activity.getApplicationContext()).appUserDao();
        this.dataSource = new AppUserLocalDataSource(dao);
    }

    public boolean validateInput(String name, String email, String password) {
        return !name.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }

    public void registerAdmin(String name, String email, String password) {
        executor.execute(() -> {
            try {
                AppUser user = new AppUser();
                user.setId(UUID.randomUUID().toString());
                user.setName(name);
                user.setEmail(email);
                user.setPasswordHash(HashUtils.sha256(password));
                user.setRole("ADMIN");
                user.setApproved(true);
                user.setSynced(false);
                user.setUpdatedAt(System.currentTimeMillis());
                user.setDeleted(false);

                dataSource.insert(user);

                uiHandler.post(() -> {
                    ToastUtils.showShort(activity, "Administrador cadastrado com sucesso!");
                    activity.finish();
                });

            } catch (Exception e) {
                uiHandler.post(() ->
                        ToastUtils.showLong(activity, "Erro ao cadastrar administrador: " + e.getMessage())
                );
            }
        });
    }
}
