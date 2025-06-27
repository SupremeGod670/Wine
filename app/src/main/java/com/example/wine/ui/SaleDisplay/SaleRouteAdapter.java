package com.example.wine.ui.SaleDisplay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R;

import java.util.ArrayList;
import java.util.List;

public class SaleRouteAdapter extends RecyclerView.Adapter<SaleRouteAdapter.SaleViewHolder> {

    private List<SaleDisplayModel> salesList;

    public SaleRouteAdapter(List<SaleDisplayModel> salesList) {
        this.salesList = salesList;
    }

    @NonNull
    @Override
    public SaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sale_route, parent, false);
        return new SaleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleViewHolder holder, int position) {
        SaleDisplayModel sale = salesList.get(position);
        holder.clientName.setText(sale.getClientName());
        holder.saleDate.setText(sale.getSaleDate());
        holder.total.setText(String.format("Total: R$ %.2f", sale.getTotal()));
        holder.checkBox.setChecked(sale.isSelectedForRoute());

        // Listener para atualizar o estado de seleção
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sale.setSelectedForRoute(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return salesList.size();
    }

    public List<SaleDisplayModel> getSelectedSales() {
        List<SaleDisplayModel> selected = new ArrayList<>();
        for (SaleDisplayModel sale : salesList) {
            if (sale.isSelectedForRoute()) {
                selected.add(sale);
            }
        }
        return selected;
    }

    public void updateList(List<SaleDisplayModel> newList) {
        this.salesList = newList;
        notifyDataSetChanged();
    }

    static class SaleViewHolder extends RecyclerView.ViewHolder {
        TextView clientName;
        TextView saleDate;
        TextView total;
        CheckBox checkBox;

        SaleViewHolder(@NonNull View itemView) {
            super(itemView);
            clientName = itemView.findViewById(R.id.textViewClientName);
            saleDate = itemView.findViewById(R.id.textViewSaleDate);
            total = itemView.findViewById(R.id.textViewSaleTotal);
            checkBox = itemView.findViewById(R.id.checkBoxSelectSale);
        }
    }
}