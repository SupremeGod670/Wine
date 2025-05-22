package com.example.wine.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wine.R;

import java.util.ArrayList;

public class WineAdapter extends BaseAdapter {

    private ArrayList<WineModel> listWine;
    private Activity activity;

    public WineAdapter(Activity activity, ArrayList<WineModel> listWine) {
        this.activity = activity;
        this.listWine = listWine;
    }

    @Override
    public int getCount() {
        return listWine.size();
    }

    @Override
    public Object getItem(int position) {
        return listWine.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.wine, viewGroup, false);
        }

        WineModel wine = listWine.get(position);
        ImageView iwine = view.findViewById(R.id.wine);
        TextView nome = view.findViewById(R.id.nome);
        TextView varietal = view.findViewById(R.id.varietal);
        TextView doc = view.findViewById(R.id.doc);
        TextView safra = view.findViewById(R.id.safra);

        nome.setText(wine.getNome());
        varietal.setText(wine.getVarietal());
        doc.setText(wine.getDoc());
        safra.setText(wine.getSafra());

        return null;
    }
}
