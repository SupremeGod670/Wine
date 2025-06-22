package com.example.wine.ui.admin;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.wine.data.datasource.user.AppUserLocalDataSource;
import com.example.wine.data.local.AppDatabaseProvider;
import com.example.wine.data.local.dao.AppUserDao;
import com.example.wine.domain.model.AppUser;
import com.example.wine.utils.ToastUtils;
import com.example.wine.utils.LogUtils;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterAdminController {

    private final AppUserLocalDataSource dataSource;
    private final Context context;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public RegisterAdminController(Context context) {
        this.context = context.getApplicationContext();
        AppUserDao dao = AppDatabaseProvider.getDatabase(context).appUserDao();
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
                user.setPasswordHash(String.valueOf(password.hashCode())); // SHA-256 futuramente
                user.setRole("ADMIN");
                user.setSynced(false);
                user.setUpdatedAt(System.currentTimeMillis());
                user.setDeleted(false);

                dataSource.insert(user);

                // Loga os dados no Logcat
                //LogUtils.logAppUser(user);

                // Toast na thread principal
                uiHandler.post(() ->
                        ToastUtils.showShort(context, "Administrador cadastrado com sucesso!")
                );
            } catch (Exception e) {
                uiHandler.post(() ->
                        ToastUtils.showLong(context, "Erro ao cadastrar administrador: " + e.getMessage())
                );
            }
        });
    }
}
