package com.example.wine.data.local.dao;

import android.content.Context;
import androidx.room.Room;

import com.example.wine.data.local.AppDatabase;
import com.example.wine.data.local.entity.WineEntity;

import java.util.List;

public class WineRepository {

    private final WineDao wineDao;

    public WineRepository(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "wine_db")
                .allowMainThreadQueries() // Para facilitar no MVP, evitar em produção
                .build();
        this.wineDao = db.wineDao();
    }

    public List<WineEntity> getAll() {
        return wineDao.getAll();
    }

    public void insert(WineEntity wine) {
        wineDao.insert(wine);
    }

    public void update(WineEntity wine) {
        wineDao.update(wine);
    }

    public void delete(WineEntity wine) {
        wineDao.delete(wine);
    }

    public WineEntity getById(int id) {
        return wineDao.getById(id);
    }
}