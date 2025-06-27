package com.example.wine.ui.adminDisplay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {

    private List<AdminDisplayModel> adminsList;

    public AdminAdapter(List<AdminDisplayModel> adminsList) {
        this.adminsList = adminsList;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout de cada item da lista (res/layout/item_admin.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin, parent, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        AdminDisplayModel admin = adminsList.get(position);
        holder.name.setText(admin.getName());
        holder.email.setText("Email: " + admin.getEmail());
        holder.role.setText("Função: " + admin.getRole()); // Exibe a função
    }

    @Override
    public int getItemCount() {
        return adminsList.size();
    }

    // Método para atualizar a lista de administradores exibida
    public void updateList(List<AdminDisplayModel> newList) {
        this.adminsList = newList;
        notifyDataSetChanged();
    }

    static class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView email;
        TextView role;

        AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewAdminName);
            email = itemView.findViewById(R.id.textViewAdminEmail);
            role = itemView.findViewById(R.id.textViewAdminRole);
        }
    }
}