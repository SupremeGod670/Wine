package com.example.wine.ui.representative;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R;

import java.util.List;

public class RepresentativeAdapter extends RecyclerView.Adapter<RepresentativeAdapter.RepresentativeViewHolder> {

    private List<RepresentativeDisplayModel> representativesList;

    public RepresentativeAdapter(List<RepresentativeDisplayModel> representativesList) {
        this.representativesList = representativesList;
    }

    @NonNull
    @Override
    public RepresentativeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout de cada item da lista (res/layout/item_representative.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_representative, parent, false);
        return new RepresentativeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepresentativeViewHolder holder, int position) {
        RepresentativeDisplayModel representative = representativesList.get(position);
        holder.name.setText(representative.getName());
        holder.phone.setText("Tel: " + representative.getPhone());
        holder.email.setText("Email: " + representative.getEmail());
    }

    @Override
    public int getItemCount() {
        return representativesList.size();
    }

    // Método para atualizar a lista de representantes exibida
    public void updateList(List<RepresentativeDisplayModel> newList) {
        this.representativesList = newList;
        notifyDataSetChanged();
    }

    static class RepresentativeViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView phone;
        TextView email;

        RepresentativeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewRepresentativeName);
            phone = itemView.findViewById(R.id.textViewRepresentativePhone);
            email = itemView.findViewById(R.id.textViewRepresentativeEmail);
        }
    }
}
