package com.example.wine.ui.SaleCreateDisplay;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wine.R;
import com.example.wine.ui.SaleCreateDisplay.WineSaleItemModel;
import java.util.List;
import java.util.Locale;

public class SaleItemAdapter extends RecyclerView.Adapter<SaleItemAdapter.SaleItemViewHolder> {

    private static List<WineSaleItemModel> items;
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onQuantityChanged();
        void onPriceChanged();
        void onItemRemoved(int position);
    }

    public SaleItemAdapter(List<WineSaleItemModel> items, OnItemActionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SaleItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_sale_wine, parent, false);
        return new SaleItemViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleItemViewHolder holder, int position) {
        WineSaleItemModel item = items.get(position);
        holder.wineName.setText(item.getWineName());
        holder.quantity.setText(String.valueOf(item.getQuantity()));
        holder.unitPrice.setText(String.format(Locale.getDefault(), "%.2f", item.getUnitPrice()));
        holder.itemTotal.setText(String.format(Locale.getDefault(), "R$ %.2f", item.getItemTotal()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<WineSaleItemModel> getItems() {
        return items;
    }

    public void addItem(WineSaleItemModel item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        listener.onQuantityChanged(); // Recalcula o total ao remover
    }

    // Atualiza todos os itens na lista
    public void updateItems(List<WineSaleItemModel> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
        listener.onQuantityChanged(); // Recalcula o total
    }

    static class SaleItemViewHolder extends RecyclerView.ViewHolder {
        TextView wineName;
        EditText quantity;
        EditText unitPrice;
        TextView itemTotal;
        ImageButton removeButton;

        SaleItemViewHolder(@NonNull View itemView, OnItemActionListener listener) {
            super(itemView);
            wineName = itemView.findViewById(R.id.textViewWineName);
            quantity = itemView.findViewById(R.id.editTextQuantity);
            unitPrice = itemView.findViewById(R.id.editTextUnitPrice);
            itemTotal = itemView.findViewById(R.id.textViewItemTotal);
            removeButton = itemView.findViewById(R.id.buttonRemoveItem);

            // Listeners para atualizar o modelo quando o texto muda
            quantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        int qty = Integer.parseInt(s.toString());
                        items.get(getAdapterPosition()).setQuantity(qty);
                    } catch (NumberFormatException e) {
                        items.get(getAdapterPosition()).setQuantity(0);
                    }
                    itemTotal.setText(String.format(Locale.getDefault(), "R$ %.2f", items.get(getAdapterPosition()).getItemTotal()));
                    listener.onQuantityChanged();
                }
            });

            unitPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        double price = Double.parseDouble(s.toString());
                        items.get(getAdapterPosition()).setUnitPrice(price);
                    } catch (NumberFormatException e) {
                        items.get(getAdapterPosition()).setUnitPrice(0.0);
                    }
                    itemTotal.setText(String.format(Locale.getDefault(), "R$ %.2f", items.get(getAdapterPosition()).getItemTotal()));
                    listener.onPriceChanged();
                }
            });

            removeButton.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemRemoved(getAdapterPosition());
                }
            });
        }
    }
}