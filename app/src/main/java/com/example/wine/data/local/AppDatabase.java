package com.example.wine.data.local;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.wine.data.local.dao.WineryDao;
import com.example.wine.data.local.dao.WineDao;
import com.example.wine.data.local.entity.WineryEntity;
import com.example.wine.data.local.entity.WineEntity;

@Database(entities = {WineryEntity.class, WineEntity.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract WineryDao wineryDao();
    public abstract WineDao wineDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "wine_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
