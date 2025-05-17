package com.example.wine.data.repository;


import android.content.Context;
import androidx.room.Room;

import com.example.wine.data.local.AppDatabase;
import com.example.wine.data.local.dao.WineryDao;
import com.example.wine.data.local.entity.WineryEntity;
import com.example.wine.domain.model.Winery;
import com.example.wine.domain.repository.WineryRepository;
import com.example.wine.util.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class WineryRepositoryImpl implements WineryRepository {
    private final AppDatabase database;
    private final WineryDao wineryDao;

    public WineryRepositoryImpl(Context context) {
        database = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class,
                "wine_app.db"
        ).allowMainThreadQueries().build();

        wineryDao = database.wineryDao();
    }

    @Override
    public long save(Winery winery) {
        WineryEntity entity = Mapper.toEntity(winery);
        return wineryDao.insert(entity);
    }

    @Override
    public List<Winery> getAll() {
        return wineryDao.getAllWineries()
                .stream()
                .map(Mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Winery getById(int id) {
        WineryEntity entity = wineryDao.getById(id);
        return entity != null ? Mapper.toDomain(entity) : null;
    }

    @Override
    public int update(Winery winery) {
        WineryEntity entity = Mapper.toEntity(winery);
        return wineryDao.update(entity);
    }

    @Override
    public int delete(int id) {
        WineryEntity entity = wineryDao.getById(id);
        if (entity != null) {
            return wineryDao.delete(entity);
        }
        return 0;
    }
}