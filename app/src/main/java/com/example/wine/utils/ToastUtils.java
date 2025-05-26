package com.example.wine.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    // Toast curto
    public static void showShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    // Toast longo, mostra por mais tempo, utilize para caso de erro e o curto para sucesso
    public static void showLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
