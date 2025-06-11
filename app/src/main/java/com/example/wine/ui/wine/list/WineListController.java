package com.example.wine.ui.wine.list;

import android.content.Context;
import android.widget.ListView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wine.R;
import com.example.wine.utils.ToastUtils;

public class WineListController {

    private Context context;
    private ListView listView;
    private DrawerLayout drawerLayout;

    public WineListController(Context context, ListView listView, DrawerLayout drawerLayout) {
        this.context = context;
        this.listView = listView;
        this.drawerLayout = drawerLayout;
    }

    public void openMenu() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(androidx.core.view.GravityCompat.START);
        } else {
            ToastUtils.showShort(context, context.getString(R.string.erro_abrir_menu));
        }
    }


}
