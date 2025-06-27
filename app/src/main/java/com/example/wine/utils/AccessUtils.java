package com.example.wine.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AccessUtils {
    private static final String PREF_NAME = "wine_prefs";
    private static final String KEY_ROLE = "user_role";
    private static final String KEY_USER_ID = "user_id";

    public static void saveUserSession(Context context, String userId, String role) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putString(KEY_USER_ID, userId)
                .putString(KEY_ROLE, role)
                .apply();
    }

    public static String getUserRole(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_ROLE, null);
    }

    public static String getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_USER_ID, null);
    }

    public static void clearSession(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }

    public static String getLoggedUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("wine_prefs", Context.MODE_PRIVATE);
        return prefs.getString("logged_user_id", null);
    }
}
