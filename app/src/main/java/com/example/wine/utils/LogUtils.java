package com.example.wine.utils;

import android.util.Log;
import com.example.wine.data.local.entity.WineryEntity;
import java.util.List;
import com.example.wine.domain.model.Wine;

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
    // Função para imprimir todos os itens de WineryEntity
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

}
