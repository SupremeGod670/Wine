package com.example.wine.ui.winery.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R;
import com.example.wine.adapter.WineryModel;

import java.util.List;

public class WineryCardAdapter extends RecyclerView.Adapter<WineryCardAdapter.WineryViewHolder> {

    private List<WineryModel> wineryList;

    public WineryCardAdapter(List<WineryModel> wineryList) {
        this.wineryList = wineryList;
    }

    public void updateList(List<WineryModel> newList) {
        this.wineryList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WineryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_winery, parent, false);
        return new WineryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WineryViewHolder holder, int position) {
        WineryModel winery = wineryList.get(position);
        holder.name.setText(winery.getNome());
        holder.country.setText(winery.getPais());
        holder.region.setText(winery.getEndereco());
    }

    @Override
    public int getItemCount() {
        return wineryList != null ? wineryList.size() : 0;
    }

    public static class WineryViewHolder extends RecyclerView.ViewHolder {
        TextView name, country, region;

        public WineryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewWineryName);
            country = itemView.findViewById(R.id.textViewCountry);
            region = itemView.findViewById(R.id.textViewRegion);
        }
    }
}
