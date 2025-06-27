package com.example.wine.ui.client.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wine.R;
import com.example.wine.domain.model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClientViewHolder> {

    private List<Client> clientList = new ArrayList<>();

    public void setClientList(List<Client> clients) {
        this.clientList = clients != null ? clients : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_client, parent, false);
        return new ClientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client client = clientList.get(position);
        holder.name.setText(client.getName());
        holder.email.setText(client.getEmail());
        holder.phone.setText(client.getPhone());
        holder.city.setText(client.getCity());

        if (client.getObservation() != null && !client.getObservation().isEmpty()) {
            holder.observation.setVisibility(View.VISIBLE);
            holder.observation.setText(client.getObservation());
        } else {
            holder.observation.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    static class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, phone, city, observation;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            email = itemView.findViewById(R.id.textViewEmail);
            phone = itemView.findViewById(R.id.textViewPhone);
            city = itemView.findViewById(R.id.textViewCity);
            observation = itemView.findViewById(R.id.textViewObservation);
        }
    }
}
