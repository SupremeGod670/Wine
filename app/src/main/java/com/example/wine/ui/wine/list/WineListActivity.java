package com.example.wine.ui.wine.list;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.wine.R;
import com.example.wine.utils.AccessUtils;

public class WineListActivity extends AppCompatActivity {

    private ListView listView;
    private ImageButton menuButton;
    private String userRole;
    private WineListController controller;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_list);

        listView = findViewById(R.id.lista);
        menuButton = findViewById(R.id.open);
        drawerLayout = findViewById(R.id.drawer_layout);
        com.google.android.material.navigation.NavigationView navigationView = findViewById(R.id.navigation_view);
        com.example.wine.utils.NavigationUtils.setupNavigation(this, navigationView, drawerLayout);
        userRole = AccessUtils.getUserRole(this);
        if (userRole == null) {
            userRole = getIntent().getStringExtra("role");
        }

        controller = new WineListController(this, listView, drawerLayout);



        if ("CLIENT".equals(userRole)) {
            menuButton.setVisibility(View.GONE);
        } else {
            menuButton.setOnClickListener(view -> controller.openMenu());
        }
        menuButton.setOnClickListener(view -> controller.openMenu());
    }

}
