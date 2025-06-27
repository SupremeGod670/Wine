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
import com.example.wine.ui.Users.Client.controller.ApproveClientController;
import com.example.wine.utils.LogUtils;
import com.example.wine.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnapprovedUsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UnapprovedUserAdapter adapter;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    private AppUserLocalDataSource userDataSource;
    private ClientLocalDataSource clientDataSource;
    private ApproveClientController approveController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unapproved_users);

        ImageButton backButton = findViewById(R.id.open);
        recyclerView = findViewById(R.id.recyclerUnapprovedUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButton.setOnClickListener(v -> finish());

        AppUserDao userDao = AppDatabaseProvider.getDatabase(getApplicationContext()).appUserDao();
        ClientDao clientDao = AppDatabaseProvider.getDatabase(getApplicationContext()).clientDao();
        userDataSource = new AppUserLocalDataSource(userDao);
        clientDataSource = new ClientLocalDataSource(clientDao);
        approveController = new ApproveClientController(this, clientDataSource, userDataSource);

        loadUnapprovedUsers();
        logAllUsersToLogcat();
    }

    private void loadUnapprovedUsers() {
        executor.execute(() -> {
            List<Client> unapprovedClients = clientDataSource.getAllNotApproved();
            List<AppUser> users = new ArrayList<>();

            for (Client client : unapprovedClients) {
                AppUser user = userDataSource.getById(client.getUserId());
                if (user != null) users.add(user);
            }

            uiHandler.post(() -> {
                adapter = new UnapprovedUserAdapter(unapprovedClients, users, (client, user) -> {
                    approveController.approve(client, user, this::loadUnapprovedUsers); // atualiza após aprovar
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
