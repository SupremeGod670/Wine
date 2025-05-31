package com.example.wine.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wine.R;

import java.util.ArrayList;

public class RepresentativeAdapter extends BaseAdapter {

    private ArrayList<RepresentativeModel> listRepresentante;
    private Activity activity;

    public RepresentativeAdapter(Activity activity, ArrayList<RepresentativeModel> listRepresentante) {
        this.activity = activity;
        this.listRepresentante = listRepresentante;
    }

    @Override
    public int getCount() {
        return listRepresentante.size();
    }

    @Override
    public Object getItem(int position) {
        return listRepresentante.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.representatives, viewGroup, false);
        }

        RepresentativeModel representante = listRepresentante.get(position);
        ImageView conta = view.findViewById(R.id.conta);
        TextView nome = view.findViewById(R.id.nome);
        TextView email = view.findViewById(R.id.email);

        nome.setText(representante.getNome());
        email.setText(representante.getEmail());

        return view;
    }
}
