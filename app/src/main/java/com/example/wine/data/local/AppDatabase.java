package com.example.wine.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.wine.data.local.dao.WineDao;
import com.example.wine.data.local.dao.WineryDao;
import com.example.wine.data.local.entity.WineEntity;
import com.example.wine.data.local.entity.WineryEntity;

@Database(
        entities = { WineEntity.class, WineryEntity.class },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract WineDao wineDao();
    public abstract WineryDao wineryDao();
}