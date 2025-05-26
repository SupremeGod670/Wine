package com.example.wine.utils;

import android.util.Log;
import com.example.wine.data.local.entity.WineryEntity;
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
}
