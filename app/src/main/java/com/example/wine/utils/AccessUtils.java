package com.example.wine.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AccessUtils {
    private static final String PREF_NAME = "wine_prefs";
    private static final String KEY_ROLE = "user_role";

    public static void saveUserRole(Context context, String role) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_ROLE, role).apply();
    }

    public static String getUserRole(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_ROLE, "REPRESENTATIVE"); // Valor padrão modificar para quando tiver usuarios cadastrados
    }
}
