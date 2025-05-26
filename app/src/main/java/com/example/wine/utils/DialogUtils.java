package com.example.wine.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtils {
    public static void showInfo(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    public static void showError(Context context, String message) {
        new AlertDialog.Builder(context)
                .setTitle("Erro")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    public static void showConfirm(Context context, String title, String message, DialogInterface.OnClickListener onYes) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Sim", onYes)
                .setNegativeButton("Não", null)
                .show();
    }
}
