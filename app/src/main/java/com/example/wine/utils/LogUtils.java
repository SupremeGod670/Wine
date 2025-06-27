package com.example.wine.utils;

import android.util.Log;

import com.example.wine.data.local.entity.WineryEntity;
import com.example.wine.data.local.entity.AppUserEntity;
import com.example.wine.domain.model.AppUser;
import com.example.wine.domain.model.Client;
import com.example.wine.domain.model.Wine;

import java.util.List;

public class LogUtils {

    public static void debug(String tag, String message) {
        Log.d(tag, message);
    }

    public static void error(String tag, String message) {
        Log.e(tag, message);
    }

    public static void info(String tag, String message) {
        Log.i(tag, message);
    }

    public static void logWineries(List<WineryEntity> wineries) {
        Log.d("LOG_Wineries", "---- Listando todas as vinícolas ----");
        for (WineryEntity w : wineries) {
            Log.d("LOG_Wineries", "ID: " + w.getId()
                    + " | Nome: " + w.getName()
                    + " | País: " + w.getCountry()
                    + " | Região: " + w.getRegion()
                    + " | Sincronizado: " + w.isSynced()
                    + " | Deletado: " + w.isDeleted()
                    + " | Atualizado em: " + w.getUpdatedAt());
        }
        Log.d("LOG_Wineries", "-------------------------------------");
    }

    public static void logWines(List<Wine> wines) {
        Log.d("LOG_Wines", "---- Listando todos os vinhos ----");
        if (wines == null || wines.isEmpty()) {
            Log.d("LOG_Wines", "Nenhum vinho cadastrado.");
        } else {
            for (Wine w : wines) {
                Log.d("LOG_Wines", "ID: " + w.getId()
                        + " | Nome: " + w.getName()
                        + " | Safra: " + w.getYear()
                        + " | Uva: " + w.getGrape()
                        + " | Tipo: " + w.getCategory()
                        + " | Teor Alcoólico: " + w.getAlcoholPercentage()
                        + " | Preço: " + w.getPrice()
                        + " | Estoque: " + w.getStock()
                        + " | Avaliação: " + w.getRating());
            }
        }
        Log.d("LOG_Wines", "-----------------------------------");
    }

    public static void logAppUser(AppUser user) {
        Log.d("LOG_AppUser", "ID: " + user.getId()
                + " | Nome: " + user.getName()
                + " | Email: " + user.getEmail()
                + " | SenhaHash: " + user.getPasswordHash()
                + " | Papel: " + user.getRole()
                + " | Aprovado: " + user.isApproved()
                + " | Sincronizado: " + user.isSynced()
                + " | Deletado: " + user.isDeleted()
                + " | Atualizado em: " + user.getUpdatedAt());
    }

    public static void logAppUsers(List<AppUser> users) {
        Log.d("LOG_AppUser", "---- Listando todos os AppUsers ----");
        if (users == null || users.isEmpty()) {
            Log.d("LOG_AppUser", "Nenhum AppUser encontrado.");
        } else {
            for (AppUser user : users) {
                logAppUser(user);
            }
        }
        Log.d("LOG_AppUser", "------------------------------------");
    }

    public static void logAppUserEntities(List<AppUserEntity> users) {
        Log.d("LOG_AppUserEntity", "---- Listando todos os usuários locais ----");
        if (users == null || users.isEmpty()) {
            Log.d("LOG_AppUserEntity", "Nenhum usuário encontrado.");
        } else {
            for (AppUserEntity user : users) {
                Log.d("LOG_AppUserEntity", "ID: " + user.getId()
                        + " | Nome: " + user.getName()
                        + " | Email: " + user.getEmail()
                        + " | SenhaHash: " + user.getPasswordHash()
                        + " | Papel: " + user.getRole()
                        + " | Sincronizado: " + user.isSynced()
                        + " | Deletado: " + user.isDeleted()
                        + " | Atualizado em: " + user.getUpdatedAt());
            }
        }
        Log.d("LOG_AppUserEntity", "-------------------------------------------");
    }

    public static void logClients(List<Client> clients) {
        Log.d("LOG_Client", "---- Listando todos os clientes ----");
        if (clients == null || clients.isEmpty()) {
            Log.d("LOG_Client", "Nenhum cliente cadastrado.");
        } else {
            for (Client c : clients) {
                Log.d("LOG_Client", "ID: " + c.getId()
                        + " | Nome: " + c.getName()
                        + " | Email: " + c.getEmail()
                        + " | Telefone: " + c.getPhone()
                        + " | Cidade: " + c.getCity()
                        + " | Aprovado: " + c.isApproved()
                        + " | Aprovado por: " + c.getApprovedBy()
                        + " | Atualizado em: " + c.getUpdatedAt());
            }
        }
        Log.d("LOG_Client", "-------------------------------------");
    }
}
