package com.example.wine.data.local;

import android.content.Context;
import androidx.room.Room;

// Singleton para fornecer a instância do AppDatabase
public class AppDatabaseProvider {

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabaseProvider.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "wine_app.db") // Nome do arquivo do DB
                            // ATENÇÃO: fallbackToDestructiveMigration() apaga os dados em caso de mudança de versão!
                            // Use apenas em desenvolvimento. Para produção, implemente Migrations.
                            .fallbackToDestructiveMigration()
                            // REMOVIDO: .allowMainThreadQueries() <--- ISSO É CRUCIAL PARA UMA IMPLEMENTAÇÃO SAUDÁVEL!
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Método para fechar o banco de dados quando não for mais necessário (geralmente no encerramento da aplicação)
    public static void closeDatabase() {
        if (INSTANCE != null && INSTANCE.isOpen()) {
            INSTANCE.close();
            INSTANCE = null;
        }
    }
}