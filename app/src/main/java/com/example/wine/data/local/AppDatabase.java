package com.example.wine.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.wine.data.local.dao.WineDao;
import com.example.wine.data.local.entity.WineEntity;

@Database(entities = {WineEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WineDao wineDao();
}