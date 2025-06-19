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

    private final ArrayList<WineModel> listWine;
    private final Activity activity;

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
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.wine, viewGroup, false);
        }

        WineModel wine = listWine.get(position);

        ImageView imageWine = view.findViewById(R.id.wine);
        TextView nome = view.findViewById(R.id.nome);
        TextView varietal = view.findViewById(R.id.varietal);
        TextView doc = view.findViewById(R.id.doc);
        TextView safra = view.findViewById(R.id.safra);
        TextView vinicola = view.findViewById(R.id.vinicola);

        nome.setText(wine.getNome());
        varietal.setText(wine.getVarietal());
        doc.setText(wine.getDoc());
        safra.setText(wine.getSafra());
        vinicola.setText(wine.getVinicola());

        return view;
    }
}
