package com.example.wine.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wine.R;

import java.util.ArrayList;

public class WineAdapter extends BaseAdapter implements Filterable {
    private final Activity activity;
    private ArrayList<WineModel> listWine;
    private ArrayList<WineModel> filteredList;

    public WineAdapter(Activity activity, ArrayList<WineModel> listWine) {
        this.activity = activity;
        this.listWine = listWine;
        this.filteredList = new ArrayList<>(listWine);
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
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

        WineModel wine = filteredList.get(position);

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

    @Override
    public Filter getFilter() {
        return filter;
    }

    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<WineModel> filtered = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filtered.addAll(listWine);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (WineModel item : listWine) {
                    if (item.getNome().toLowerCase().contains(filterPattern) ||
                            item.getVarietal().toLowerCase().contains(filterPattern) ||
                            item.getVinicola().toLowerCase().contains(filterPattern) ||
                            item.getDoc().toLowerCase().contains(filterPattern) ||
                            item.getSafra().toLowerCase().contains(filterPattern)) {
                        filtered.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filtered;
            results.count = filtered.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList.clear();
            filteredList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
