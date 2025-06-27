// Caminho: com.example.wine.data.datasource.client/ClientLocalDataSource.java
package com.example.wine.data.datasource.client;

import com.example.wine.data.local.dao.ClientDao;
import com.example.wine.data.local.entity.ClientEntity;
import com.example.wine.domain.model.Client;
import com.example.wine.utils.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ClientLocalDataSource {
    private final ClientDao clientDao;

    public ClientLocalDataSource(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public List<Client> getAll() {
        List<ClientEntity> entities = clientDao.getAll();
        List<Client> clients = new ArrayList<>();
        for (ClientEntity entity : entities) {
            clients.add(Mapper.toModel(entity));
        }
        return clients;
    }

    public Client getById(String id) {
        ClientEntity entity = clientDao.getById(id);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    public Client getByEmail(String email) {
        ClientEntity entity = clientDao.getByEmail(email);
        return entity != null ? Mapper.toModel(entity) : null;
    }

    public List<Client> getClientsByRegionId(String regionId) { // NOVO MÉTODO
        List<ClientEntity> entities = clientDao.getClientsByRegionId(regionId);
        List<Client> clients = new ArrayList<>();
        for (ClientEntity entity : entities) {
            clients.add(Mapper.toModel(entity));
        }
        return clients;
    }

    public void insert(Client client) {
        clientDao.insert(Mapper.toEntity(client));
    }

    public void update(Client client) {
        clientDao.update(Mapper.toEntity(client));
    }

    public void softDelete(String id) {
        clientDao.softDelete(id);
    }

    public List<Client> getAllApprovedClients() {
        List<ClientEntity> entities = clientDao.getAllApprovedClients();
        List<Client> clients = new ArrayList<>();
        for (ClientEntity entity : entities) {
            clients.add(Mapper.toModel(entity));
        }
        return clients;
    }

    public List<Client> getClientsApprovedBy(String userId) {
        List<ClientEntity> entities = clientDao.getClientsApprovedBy(userId);
        List<Client> clients = new ArrayList<>();
        for (ClientEntity entity : entities) {
            clients.add(Mapper.toModel(entity));
        }
        return clients;
    }

    public List<Client> getAllNotSynced() {
        List<ClientEntity> entities = clientDao.getAllNotSynced();
        List<Client> clients = new ArrayList<>();
        for (ClientEntity entity : entities) {
            clients.add(Mapper.toModel(entity));
        }
        return clients;
    }

    public void updateSyncStatus(String id, boolean isSynced) {
        clientDao.updateSyncStatus(id, isSynced);
    }

    public List<Client> getAllNotApproved() {
        List<ClientEntity> entities = clientDao.getAllNotApprovedClients();
        List<Client> clients = new ArrayList<>();
        for (ClientEntity entity : entities) {
            clients.add(Mapper.toModel(entity));
        }
        return clients;
    }
}