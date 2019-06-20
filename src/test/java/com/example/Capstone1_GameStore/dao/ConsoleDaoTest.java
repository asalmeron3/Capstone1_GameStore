package com.example.Capstone1_GameStore.dao;

import com.example.Capstone1_GameStore.models.Console;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConsoleDaoTest {
    @Autowired
    protected ConsoleDao consoleDao;

    @Before
    public void setup() {
        List<Console> allConsoles = consoleDao.getAllConsoles();

        allConsoles.stream()
                .forEach(console -> consoleDao.deleteConsoleById(console.getConsole_id()));

        Console console1 = new Console(
                "Playstation",
                "Sony",
                "8 GB",
                "Intel 1J",
                new BigDecimal(50.21),
                64
        );

        Console console2 = new Console(
                "Xbox One",
                "Microsoft",
                "8 GB",
                "Intel 1J",
                new BigDecimal(212.50),
                64
        );
        Console console3 = new Console(
                "Xbox",
                "Microsoft",
                "8 GB",
                "Intel 1J",
                new BigDecimal(69),
                64
        );

        consoleDao.addConsole(console1);
        consoleDao.addConsole(console2);
        consoleDao.addConsole(console3);
    }

    @Test
    public void createGetDeleteConsole() {
        Console actualConsole = new Console(
                "Playstation",
                "Sony",
                "8 GB",
                "Intel 1J",
                new BigDecimal(50.21),
                64
        );

        Console consoleAdded = consoleDao.addConsole(actualConsole);

        int id = consoleAdded.getConsole_id();
        actualConsole.setConsole_id(id);

        assertEquals(consoleAdded, actualConsole);

        Console consoleFound = consoleDao.getConsoleById(id);

        assertEquals(consoleFound, actualConsole);

        assertTrue(consoleDao.deleteConsoleById(id));
        assertFalse(consoleDao.deleteConsoleById(0));

//        System.out.println("-------------------  Actual Data ----------------- ");
//        System.out.println(actualConsole.getConsole_id());
//        System.out.println(actualConsole.getModel());
//        System.out.println(actualConsole.getManufacturer());
//        System.out.println(actualConsole.getMemory_amount());
//        System.out.println(actualConsole.getProcessor());
//        System.out.println(actualConsole.getPrice());
//        System.out.println(actualConsole.getQuantity());
//
//        System.out.println("\n\n-------------------  Found Data ----------------- ");
//        System.out.println(consoleFound.getConsole_id());
//        System.out.println(consoleFound.getModel());
//        System.out.println(consoleFound.getManufacturer());
//        System.out.println(consoleFound.getMemory_amount());
//        System.out.println(consoleFound.getProcessor());
//        System.out.println(consoleFound.getPrice());
//        System.out.println(consoleFound.getQuantity());
    }

    @Test
    public void updateConsole() {
        Console actualConsole = new Console(
                "Playstation",
                "Sony",
                "8 GB",
                "Intel 1J",
                new BigDecimal(50.21),
                64
        );

        Console consoleAdded = consoleDao.addConsole(actualConsole);

        int id = consoleAdded.getConsole_id();
        actualConsole.setConsole_id(id);
        actualConsole.setPrice(new BigDecimal(60.33));
        actualConsole.setMemory_amount("16 GB");

        Console updatedConsole = consoleDao.updateConsole(actualConsole);

        assertEquals(updatedConsole, actualConsole);
    }

    @Test
    public void getAllConsolesByManufacture() {
        List<Console> microsoftConsoles = consoleDao.getAllConsolesByManufacturere("micr");
        List<Console> sonyConsoles = consoleDao.getAllConsolesByManufacturere("sony");
        List<Console> nintendoConsoles = consoleDao.getAllConsolesByManufacturere("Nintendo");

        assertEquals(microsoftConsoles.size(), 2);
        assertEquals(sonyConsoles.size(), 1);
        assertEquals(nintendoConsoles.size(), 0);
    }
}
