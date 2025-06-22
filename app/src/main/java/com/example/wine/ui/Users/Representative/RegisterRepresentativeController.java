package com.example.wine.ui.Users.Representative;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.example.wine.data.datasource.representative.RepresentativeLocalDataSource;
import com.example.wine.data.datasource.user.AppUserLocalDataSource;
import com.example.wine.data.local.AppDatabaseProvider;
import com.example.wine.data.local.dao.AppUserDao;
import com.example.wine.data.local.dao.RepresentativeDao;
import com.example.wine.domain.model.AppUser;
import com.example.wine.domain.model.Representative;
import com.example.wine.utils.HashUtils;
import com.example.wine.utils.ToastUtils;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterRepresentativeController {

    private final Activity activity;
    private final AppUserLocalDataSource userDataSource;
    private final RepresentativeLocalDataSource representativeDataSource;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public RegisterRepresentativeController(Activity activity) {
        this.activity = activity;
        AppUserDao userDao = AppDatabaseProvider.getDatabase(activity.getApplicationContext()).appUserDao();
        RepresentativeDao representativeDao = AppDatabaseProvider.getDatabase(activity.getApplicationContext()).representativeDao();
        this.userDataSource = new AppUserLocalDataSource(userDao);
        this.representativeDataSource = new RepresentativeLocalDataSource(representativeDao);
    }

    public boolean validateInput(String name, String email, String password, String phone) {
        return !name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !phone.isEmpty();
    }

    public void registerRepresentative(String name, String email, String password, String phone) {
        executor.execute(() -> {
            try {
                String userId = UUID.randomUUID().toString();

                // Criação do AppUser
                AppUser user = new AppUser();
                user.setId(userId);
                user.setName(name);
                user.setEmail(email);
                user.setPasswordHash(HashUtils.sha256(password));
                user.setRole("REPRESENTATIVE");
                user.setApproved(true);
                user.setSynced(false);
                user.setUpdatedAt(System.currentTimeMillis());
                user.setDeleted(false);

                userDataSource.insert(user);

                // Criação do Representative
                Representative rep = new Representative();
                rep.setId(UUID.randomUUID().toString());
                rep.setUserId(userId);
                rep.setPhone(phone);
                rep.setSynced(false);
                rep.setUpdatedAt(System.currentTimeMillis());
                rep.setDeleted(false);

                representativeDataSource.insert(rep);

                uiHandler.post(() -> {
                    ToastUtils.showShort(activity, "Representante cadastrado com sucesso!");
                    activity.finish();
                });

            } catch (Exception e) {
                uiHandler.post(() ->
                        ToastUtils.showLong(activity, "Erro ao cadastrar representante: " + e.getMessage())
                );
            }
        });
    }
}
