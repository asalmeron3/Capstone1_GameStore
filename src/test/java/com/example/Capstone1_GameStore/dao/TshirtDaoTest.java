package com.example.Capstone1_GameStore.dao;

import com.example.Capstone1_GameStore.models.Tshirt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TshirtDaoTest {

    @Autowired
    TshirtDao tshirtDao;

    @Before
    public void setup() {
        List<Tshirt> allTshirts = tshirtDao.getAllTshirts();

        allTshirts.stream()
                .forEach(T -> tshirtDao.deleteTshirtById(T.getT_shirt_id()));

        Tshirt t1 = new Tshirt(
                "medium",
                "blue",
                "Short sleeve v neck with panda on front",
                new BigDecimal(15.99),
                28);

        Tshirt t2 = new Tshirt(
                "large",
                "blue",
                "Short sleeve v neck with panda on front",
                new BigDecimal(15.99),
                28);

        Tshirt t3 = new Tshirt(
                "large",
                "white",
                "Short sleeve v neck with panda on front",
                new BigDecimal(15.99),
                28);

        tshirtDao.addTshirt(t1);
        tshirtDao.addTshirt(t2);
        tshirtDao.addTshirt(t3);
    }

    @Test
    public void createGetDeletTshirt() {
        Tshirt t1 = new Tshirt(
                "medium",
                "blue",
                "Short sleeve v neck with panda on front",
                new BigDecimal(15.99),
                28);
        Tshirt tAdded = tshirtDao.addTshirt(t1);

        int id  = tAdded.getT_shirt_id();
        t1.setT_shirt_id(id);

        assertEquals(tAdded, t1);

        Tshirt tFound = tshirtDao.getTshirtById(id);
        assertEquals(tFound, t1);

        assertNull(tshirtDao.getTshirtById(0));

        assertTrue(tshirtDao.deleteTshirtById(id));
        assertFalse(tshirtDao.deleteTshirtById(0));
    }

    @Test
    public void updateTshirt() {
        Tshirt t1 = new Tshirt(
                "medium",
                "blue",
                "Short sleeve v neck with panda on front",
                new BigDecimal(15.99),
                28);
        t1 = tshirtDao.addTshirt(t1);

        t1.setQuantity(30);
        t1.setColor("black");

        Tshirt updatedT = tshirtDao.updateTshirt(t1);

        assertEquals(updatedT, t1);
    }

    @Test
    public void getAllTshirts() {
        List<Tshirt> allTs = tshirtDao.getAllTshirts();
        assertEquals(allTs.size(), 3);
    }

    @Test
    public void getAllTshirtsByColor() {
        List<Tshirt> allBlueTs = tshirtDao.getTshirtsByColor("blue");
        assertEquals(allBlueTs.size(), 2);

        allBlueTs = tshirtDao.getTshirtsByColor("BLu");
        assertEquals(allBlueTs.size(), 2);

        List<Tshirt> whiteTs = tshirtDao.getTshirtsByColor("White");
        assertEquals(whiteTs.size(), 1);

        whiteTs = tshirtDao.getTshirtsByColor("whi");
        assertEquals(whiteTs.size(), 1);

        List<Tshirt> pinkTs = tshirtDao.getTshirtsByColor("pink");
        assertEquals(pinkTs.size(), 0);
    }

    @Test
    public void getAllTshirtsBySize() {
        List<Tshirt> mediumsTs = tshirtDao.getTshirtsBysize("MEDIUM");
        assertEquals(mediumsTs.size(), 1);

        mediumsTs = tshirtDao.getTshirtsBysize("MEdi");
        assertEquals(mediumsTs.size(), 1);

        List<Tshirt> largeTs = tshirtDao.getTshirtsBysize("LARge");
        assertEquals(largeTs.size(), 2);

        largeTs = tshirtDao.getTshirtsBysize("lar");
        assertEquals(largeTs.size(), 2);

        List<Tshirt> smallTs = tshirtDao.getTshirtsBysize("small");
        assertEquals(smallTs.size(), 0);
    }



}
