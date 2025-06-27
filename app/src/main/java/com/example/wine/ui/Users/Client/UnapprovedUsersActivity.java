package com.example.wine.ui.Users.Client;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R;
import com.example.wine.data.datasource.client.ClientLocalDataSource;
import com.example.wine.data.datasource.user.AppUserLocalDataSource;
import com.example.wine.data.local.AppDatabaseProvider;
import com.example.wine.data.local.dao.AppUserDao;
import com.example.wine.data.local.dao.ClientDao;
import com.example.wine.domain.model.AppUser;
import com.example.wine.domain.model.Client;
import com.example.wine.ui.Users.Client.adapter.UnapprovedUserAdapter;
import com.example.wine.utils.LogUtils;
import com.example.wine.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnapprovedUsersActivity extends AppCompatActivity {

    private ImageButton backButton;
    private RecyclerView recyclerView;
    private UnapprovedUserAdapter adapter;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    private AppUserLocalDataSource userDataSource;
    private ClientLocalDataSource clientDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unapproved_users);

        backButton = findViewById(R.id.open);
        recyclerView = findViewById(R.id.recyclerUnapprovedUsers);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButton.setOnClickListener(v -> finish());

        // Inicializa os data sources
        AppUserDao userDao = AppDatabaseProvider.getDatabase(getApplicationContext()).appUserDao();
        ClientDao clientDao = AppDatabaseProvider.getDatabase(getApplicationContext()).clientDao();
        userDataSource = new AppUserLocalDataSource(userDao);
        clientDataSource = new ClientLocalDataSource(clientDao);

        // Carrega e exibe
        loadUnapprovedUsers();
        logAllUsersToLogcat(); // <- Mostra todos os usuários no Logcat
    }

    private void loadUnapprovedUsers() {
        executor.execute(() -> {
            List<Client> unapprovedClients = clientDataSource.getAllNotApproved(); // ✅ novo método correto
            List<AppUser> users = new ArrayList<>();

            for (Client client : unapprovedClients) {
                AppUser user = userDataSource.getById(client.getUserId());
                if (user != null) {
                    users.add(user);
                }
            }

            uiHandler.post(() -> {
                adapter = new UnapprovedUserAdapter(unapprovedClients, users, (client, user) -> {
                    ToastUtils.showShort(this, "Aprovar: " + client.getName());
                });
                recyclerView.setAdapter(adapter);
            });
        });
    }



    private void logAllUsersToLogcat() {
        executor.execute(() -> {
            List<AppUser> allUsers = userDataSource.getAll();
            LogUtils.logAppUsers(allUsers);
        });
    }
}
