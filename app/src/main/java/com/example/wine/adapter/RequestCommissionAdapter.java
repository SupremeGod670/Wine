package com.example.wine.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wine.R;

import java.util.ArrayList;

public class RequestCommissionAdapter extends BaseAdapter {

    private ArrayList<RequestCommissionModel> listPedidoComissao;
    private Activity activity;

    public RequestCommissionAdapter(Activity activity, ArrayList<RequestCommissionModel> listPedidoComissao) {
        this.activity = activity;
        this.listPedidoComissao = listPedidoComissao;
    }

    @Override
    public int getCount() {
        return listPedidoComissao.size();
    }

    @Override
    public Object getItem(int position) {
        return listPedidoComissao.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.commission_requests, viewGroup, false);
        }

        RequestCommissionModel pedidoComissao = listPedidoComissao.get(position);
        TextView codigo = view.findViewById(R.id.codigo);
        TextView nome = view.findViewById(R.id.nome);
        TextView total = view.findViewById(R.id.total);
        TextView comissao = view.findViewById(R.id.comissao);

        codigo.setText(pedidoComissao.getCodigo());
        nome.setText(pedidoComissao.getNome());
        total.setText(pedidoComissao.getTotal());
        comissao.setText(pedidoComissao.getComissao());

        return view;
    }
}
