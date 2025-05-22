package com.example.wine.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wine.R;

import java.util.ArrayList;

public class ClienteAdapter extends BaseAdapter {

    private ArrayList<ClienteModel> listCliente;
    private Activity activity;

    public ClienteAdapter(Activity activity, ArrayList<ClienteModel> listCliente) {
        this.activity = activity;
        this.listCliente = listCliente;
    }

    @Override
    public int getCount() {
        return listCliente.size();
    }

    @Override
    public Object getItem(int position) {
        return listCliente.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.clientes, viewGroup, false);
        }

        ClienteModel cliente = listCliente.get(position);
        ImageView conta = view.findViewById(R.id.conta);
        TextView nome = view.findViewById(R.id.nome);
        TextView responsavel = view.findViewById(R.id.responsavel);

        nome.setText(cliente.getNome());
        responsavel.setText(cliente.getResponsavel());

        return view;
    }
}
