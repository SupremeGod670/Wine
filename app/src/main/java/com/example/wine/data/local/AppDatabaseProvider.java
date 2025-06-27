package com.example.wine.data.local;

import android.content.Context;

import androidx.room.Room;

public class AppDatabaseProvider {

    private static volatile AppDatabase INSTANCE;

    /**
     * Método usado internamente para obter a instância de banco de dados.
     * Esse é o padrão correto e seguro (Double-Checked Locking).
     */
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabaseProvider.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "wine_app.db"
                            )
                            .fallbackToDestructiveMigration() // cuidado em produção!
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Compatibilidade para chamadas como:
     * AppDatabaseProvider.getInstance(context).appUserDao();
     */
    public static AppDatabase getInstance(Context context) {
        return getDatabase(context);
    }

    /**
     * Fecha a instância do banco — útil em testes ou quando o app finaliza.
     */
    public static void closeDatabase() {
        if (INSTANCE != null && INSTANCE.isOpen()) {
            INSTANCE.close();
            INSTANCE = null;
        }
    }
}
