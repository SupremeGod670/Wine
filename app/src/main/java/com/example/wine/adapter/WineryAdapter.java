package com.example.wine.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wine.R;

import java.util.ArrayList;

public class WineryAdapter extends BaseAdapter {
    private final ArrayList<WineryModel> listVinicola;
    private final Activity activity;

    public WineryAdapter(Activity activity, ArrayList<WineryModel> listVinicola) {
        this.activity = activity;
        this.listVinicola = listVinicola;
    }

    @Override
    public int getCount() {
        return listVinicola.size();
    }

    @Override
    public Object getItem(int position) {
        return listVinicola.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.winery, viewGroup, false);
        }

        WineryModel vinicola = listVinicola.get(position);
        TextView nome = view.findViewById(R.id.nome);
        TextView pais = view.findViewById(R.id.pais);
        TextView endereco = view.findViewById(R.id.endereco);

        nome.setText(vinicola.getNome());
        pais.setText(vinicola.getPais());
        endereco.setText(vinicola.getEndereco());

        return view;
    }
}
