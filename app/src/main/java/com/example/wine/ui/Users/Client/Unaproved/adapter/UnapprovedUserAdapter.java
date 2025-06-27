package com.example.wine.ui.Users.Client.Unaproved.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R;
import com.example.wine.domain.model.Client;
import com.example.wine.domain.model.AppUser;

import java.util.List;

public class UnapprovedUserAdapter extends RecyclerView.Adapter<UnapprovedUserAdapter.ViewHolder> {

    public interface OnApproveClickListener {
        void onApproveClick(Client client, AppUser user);
    }

    private final List<Client> clients;
    private final List<AppUser> users;
    private final OnApproveClickListener listener;

    public UnapprovedUserAdapter(List<Client> clients, List<AppUser> users, OnApproveClickListener listener) {
        this.clients = clients;
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_unapproved_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Client client = clients.get(position);
        AppUser user = findUserById(client.getUserId());

        holder.nameText.setText(user != null ? user.getName() : client.getName());
        holder.emailText.setText(user != null ? user.getEmail() : client.getEmail());
        holder.phoneText.setText(client.getPhone());
        holder.cityText.setText(client.getCity());
        holder.observationText.setText(client.getObservation());

        holder.approveButton.setOnClickListener(v -> listener.onApproveClick(client, user));
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    private AppUser findUserById(String userId) {
        for (AppUser user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, emailText, phoneText, cityText, observationText;
        Button approveButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.textClientName);
            emailText = itemView.findViewById(R.id.textClientEmail);
            phoneText = itemView.findViewById(R.id.textClientPhone);
            cityText = itemView.findViewById(R.id.textClientCity);
            observationText = itemView.findViewById(R.id.textClientObservation);
            approveButton = itemView.findViewById(R.id.buttonApprove);
        }
    }
}
