package com.example.wine.data.local.dao;

import android.content.Context;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.example.wine.data.local.AppDatabase;
import com.example.wine.data.local.entity.WineEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class WineDaoTest {

    private WineDao wineDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // Use um banco de dados em memória para testes
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()  //Permite executar queries na main thread para simplificar o teste. NÃO use em produção
                .build();
        wineDao = db.wineDao();
    }

    @After
    public void closeDb() throws IOException {
        if (db != null) { // Adiciona verificação nula
            db.close();
        }
    }

    @Test
    public void testInsertAndGetWine() throws Exception {
        WineEntity wine = new WineEntity();
        wine.name = "Cabernet Sauvignon";
        wine.harvest = "2018";
        wine.type = "Tinto";
        wineDao.insert(wine);

        WineEntity retrievedWine = wineDao.getById(1);
        assertNotNull(retrievedWine);
        assertEquals(wine.name, retrievedWine.name);
        assertEquals(wine.harvest, retrievedWine.harvest);
        assertEquals(wine.type, retrievedWine.type);
    }

    @Test
    public void testGetAllWines() throws Exception{
        WineEntity wine1 = new WineEntity();
        wine1.name = "Merlot";
        wine1.harvest = "2020";
        wine1.type = "Tinto";
        wineDao.insert(wine1);

        WineEntity wine2 = new WineEntity();
        wine2.name = "Sauvignon Blanc";
        wine2.harvest = "2021";
        wine2.type = "Branco";
        wineDao.insert(wine2);

        List<WineEntity> wines = wineDao.getAll();
        assertEquals(2,wines.size());
        assertEquals(wine1.name, wines.get(0).name);
        assertEquals(wine2.name, wines.get(1).name);

    }

    @Test
    public void testUpdateWine() throws Exception {
        WineEntity wine = new WineEntity();
        wine.name = "Cabernet Sauvignon";
        wine.harvest = "2018";
        wine.type = "Tinto";
        wineDao.insert(wine);

        wine.name = "Cabernet Sauvignon Updated";
        wine.harvest = "2019";
        wineDao.update(wine);

        WineEntity updatedWine = wineDao.getById(1);
        assertEquals("Cabernet Sauvignon Updated", updatedWine.name);
        assertEquals("2019", updatedWine.harvest);
    }

    @Test
    public void testDeleteWine() throws Exception {
        WineEntity wine = new WineEntity();
        wine.name = "Cabernet Sauvignon";
        wine.harvest = "2018";
        wine.type = "Tinto";
        wineDao.insert(wine);

        wineDao.delete(wine);
        WineEntity deletedWine = wineDao.getById(1);
        assertNull(deletedWine);
    }
}
