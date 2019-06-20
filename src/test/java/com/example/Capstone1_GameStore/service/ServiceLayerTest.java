package com.example.Capstone1_GameStore.service;

import com.example.Capstone1_GameStore.dao.*;
import com.example.Capstone1_GameStore.models.Console;
import com.example.Capstone1_GameStore.models.Game;
import com.example.Capstone1_GameStore.models.Tshirt;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;



public class ServiceLayerTest {

    ServiceLayer serviceLayerInTest;
    GameDao gameDaoForTest;
    ConsoleDao consoleDaoForTest;
    TshirtDao tshirtDao;

    @Before
    public void setUpServiceLayerInTestAndMockDbs() throws Exception {

        setUpGameMock();
        setUpConsoleDao();
        setUpTshirtDao();

        serviceLayerInTest = new ServiceLayer(gameDaoForTest, consoleDaoForTest, tshirtDao);
    }

    private void setUpGameMock() {

        gameDaoForTest = mock(GameDaoJdbcTemplate.class);

        // This is the game we will "send to the database" to test out service layer's gameDao.addGame
        // Mock will use it to compare anything being sent to the the database. If it does not match exactly
        // it will not know what to do. Multiple games can be created to test multiple sent games; if the game is
        // not made her as a reference, the mock will not know what to return and throw a NullPointerException
        Game gameBeingAddedToMockDb = new Game(
                "Toy's Story",
                "E",
                "An adventure game in the setting of the movie Toy Story",
                new BigDecimal(6.22),
                "Disney",
                12);

        // This is the game we want our mock database to return when the above game is sent. The id 88 is set, thus
        // simulating what our true database would do for us
        Game gameSentBackDirectlyFromDb = new Game(
                "Toy's Story",
                "E",
                "An adventure game in the setting of the movie Toy Story",
                new BigDecimal(6.22),
                "Disney",
                12);
        gameSentBackDirectlyFromDb.setGame_id(88);

        doReturn(gameSentBackDirectlyFromDb).when(gameDaoForTest).addGame(gameBeingAddedToMockDb);

        doReturn(gameSentBackDirectlyFromDb).when(gameDaoForTest).getGameById(88);

        doReturn(true).when(gameDaoForTest).deleteGameById(88);
        doReturn(false).when(gameDaoForTest).deleteGameById(44);

        Game updatedGame = new Game(
                "Toy's Story",
                "E",
                "An adventure game in the setting of the movie Toy Story",
                new BigDecimal(6.22),
                "Insomniac",
                12);
        updatedGame.setGame_id(88);
        doReturn(updatedGame).when(gameDaoForTest).updateGame(updatedGame);

        // Instantiating several games to compose lists for searching for all, by title, by esrb_rating, and studio
        Game game1 = new Game(
                "Assassin's Creed",
                "T",
                "Stealth action game",
                new BigDecimal(10.00),
                "Encore Software",
                6);

        Game game2 = new Game(
                "Assassin's Creed 2",
                "M",
                "Stealth action game",
                new BigDecimal(5.99),
                "Insomniac",
                5);

        Game game3 = new Game(
                "Assassin's Creed 3 ",
                "M",
                "Stealth action game",
                new BigDecimal(8.66),
                "Encore Software",
                4);

        Game game4 = new Game(
                "Left for Dead",
                "M",
                "Stealth action game",
                new BigDecimal(11.2),
                "Encore Software",
                2);

        List<Game> listOfAllGames = new ArrayList<>();
        listOfAllGames.add(game1);
        listOfAllGames.add(game2);
        listOfAllGames.add(game3);
        listOfAllGames.add(game4);

        List<Game> listOfGamesByEncoreSoftware = new ArrayList<>();
        listOfGamesByEncoreSoftware.add(game1);
        listOfGamesByEncoreSoftware.add(game3);
        listOfGamesByEncoreSoftware.add(game4);


        List<Game> listOfGamesWithEsrbRatingM = new ArrayList<>();
        listOfGamesWithEsrbRatingM.add(game2);
        listOfGamesWithEsrbRatingM.add(game3);
        listOfGamesWithEsrbRatingM.add(game4);

        List<Game> listOfGamesWithTitleAssassin = new ArrayList<>();
        listOfGamesWithTitleAssassin.add(game1);
        listOfGamesWithTitleAssassin.add(game2);
        listOfGamesWithTitleAssassin.add(game3);

        doReturn(listOfAllGames).when(gameDaoForTest).getAllGames();

        doReturn(listOfGamesByEncoreSoftware).when(gameDaoForTest).getGamesByStudio("Encore");
        doReturn(listOfGamesByEncoreSoftware).when(gameDaoForTest).getGamesByStudio("enco");

        doReturn(listOfGamesByEncoreSoftware).when(gameDaoForTest).getGamesByEsrbRating("M");
        doReturn(listOfGamesByEncoreSoftware).when(gameDaoForTest).getGamesByEsrbRating("mine");


        doReturn(listOfGamesWithTitleAssassin).when(gameDaoForTest).getGamesByTitle("assasin");
        doReturn(listOfGamesWithTitleAssassin).when(gameDaoForTest).getGamesByTitle("assa");


    }

    @Test
    public void addAGame() {
        // This is the game we expect after calling the service layer's saveGameToDb method. The mock database knows
        // to expect this object THUS will send back a game with the id 88 (as specified in setUpGameMock()
        Game actualGameFromServiceLayer = new Game(
                "Toy's Story",
                "E",
                "An adventure game in the setting of the movie Toy Story",
                new BigDecimal(6.22),
                "Disney",
                12);

        // This is the game to send to the mock database. Note: The mock database knows what to do when passed this game
        Game gameToAddToMock = new Game(
                "Toy's Story",
                "E",
                "An adventure game in the setting of the movie Toy Story",
                new BigDecimal(6.22),
                "Disney",
                12);

        // Use the serviceLayerInTest in this test to test the method saveGameToDb
        Game gameFromServiceLayer = serviceLayerInTest.saveGameToDb(gameToAddToMock);
        int id = gameFromServiceLayer.getGame_id();

        actualGameFromServiceLayer.setGame_id(id);

        assertEquals(gameFromServiceLayer, actualGameFromServiceLayer);
    }

    @Test
    public void getGameById() {
        Game actualGameFromServiceLayer = new Game(
                "Toy's Story",
                "E",
                "An adventure game in the setting of the movie Toy Story",
                new BigDecimal(6.22),
                "Disney",
                12);
        actualGameFromServiceLayer.setGame_id(88);

        Game gameFound = serviceLayerInTest.getGameByIdFromDb(88);

        assertEquals(gameFound, actualGameFromServiceLayer);
    }

    @Test
    public void deleteGame() {
        assertTrue(serviceLayerInTest.deleteGameFromDb(88));
        assertFalse(serviceLayerInTest.deleteGameFromDb(44));
    }

    @Test
    public void updateGame() {
        Game actualGameFromServiceLayer = new Game(
                "Toy's Story",
                "E",
                "An adventure game in the setting of the movie Toy Story",
                new BigDecimal(6.22),
                "Disney",
                12);
        actualGameFromServiceLayer.setGame_id(88);

        // make an update to studio
        actualGameFromServiceLayer.setStudio("Insomniac");

        Game updatedGame = serviceLayerInTest.updateGameInDb(actualGameFromServiceLayer) ;

        assertEquals(updatedGame, actualGameFromServiceLayer);
    }

    @Test
    public void getAllGames() {
        List<Game> allGames = serviceLayerInTest.getAllGamesInDb();
        assertEquals(allGames.size(), 4);
    }

    @Test
    public void getAllGamesRatedM() {
        List<Game> allRatedMGames1 = serviceLayerInTest.getAllGamesWithEsrbRatingFromDb("M");
        List<Game> allRatedMGames2 = serviceLayerInTest.getAllGamesWithEsrbRatingFromDb("mine");

        assertEquals(allRatedMGames1.size(), 3);
        assertEquals(allRatedMGames2.size(), 3);
    }

    @Test
    public void getAllGamesByStudioEncore() {
        List<Game> allEncoreGames2 = serviceLayerInTest.getAllGameByStudioFromDb("Encore");
        List<Game> allEncoreGames1 = serviceLayerInTest.getAllGameByStudioFromDb("enco");

        assertEquals(allEncoreGames1.size(), 3);
        assertEquals(allEncoreGames2.size(), 3);
    }

    @Test
    public void getAllGamesByPartialTitle() {

        List<Game> allGamesWithTitle1 = serviceLayerInTest.getAllGamesWithTitleFromDb("assa");
        List<Game> allGamesWithTitle2 = serviceLayerInTest.getAllGamesWithTitleFromDb("assasin");

        assertEquals(allGamesWithTitle1.size(), 3);
        assertEquals(allGamesWithTitle2.size(), 3);
    }

    public void setUpConsoleDao() {
        consoleDaoForTest = mock(ConsoleDaoJdbcTemplate.class);

        Console successfullyAddedConsole = new Console(
                "Xbox One",
                "Microsoft",
                "8 GB",
                "Intel 1J",
                new BigDecimal(10.44),
                64
        );
        successfullyAddedConsole.setConsole_id(77);


        Console consoleToAdd = new Console(
                "Xbox One",
                "Microsoft",
                "8 GB",
                "Intel 1J",
                new BigDecimal(10.44),
                64
        );

        Console updatedConsole = new Console(
                "Xbox One",
                "Windows",
                "8 GB",
                "Intel 1J",
                new BigDecimal(10.44),
                64
        );
        updatedConsole.setConsole_id(77);

        doReturn(successfullyAddedConsole).when(consoleDaoForTest).addConsole(consoleToAdd);
        doReturn(successfullyAddedConsole).when(consoleDaoForTest).getConsoleById(77);

        doReturn(updatedConsole).when(consoleDaoForTest).updateConsole(updatedConsole);

        doReturn(true).when(consoleDaoForTest).deleteConsoleById(77);
        doReturn(false).when(consoleDaoForTest).deleteConsoleById(99);


        Console console1 = new Console(
                "Playstatoin",
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

        List<Console> allConsoles = new ArrayList<>();
        allConsoles.add(console1);
        allConsoles.add(console2);
        allConsoles.add(console3);

        List<Console> microsoftConsoles = new ArrayList<>();
        microsoftConsoles.add(console2);
        microsoftConsoles.add(console3);

        List<Console> sonyConsoles = new ArrayList<>();
        sonyConsoles.add(console1);

        List<Console> blankConsoles = new ArrayList<>();

        doReturn(allConsoles).when(consoleDaoForTest).getAllConsoles();

        doReturn(microsoftConsoles).when(consoleDaoForTest).getAllConsolesByManufacturere("micro");
        doReturn(microsoftConsoles).when(consoleDaoForTest).getAllConsolesByManufacturere("Mic");
        doReturn(blankConsoles).when(consoleDaoForTest).getAllConsolesByManufacturere("Nintendo");
        doReturn(sonyConsoles).when(consoleDaoForTest).getAllConsolesByManufacturere("son");
        doReturn(sonyConsoles).when(consoleDaoForTest).getAllConsolesByManufacturere("so");

    }

    @Test
    public void addConsole() {
        Console actualConsole = new Console(
                "Xbox One",
                "Microsoft",
                "8 GB",
                "Intel 1J",
                new BigDecimal(10.44),
                64
        );

        Console consoleToAdd = new Console(
                "Xbox One",
                "Microsoft",
                "8 GB",
                "Intel 1J",
                new BigDecimal(10.44),
                64
        );

        Console consoleAdded = serviceLayerInTest.addConsoleToDb(consoleToAdd);
        actualConsole.setConsole_id(consoleAdded.getConsole_id());

        assertEquals(consoleAdded, actualConsole);
    }

    @Test
    public void getConsoleById() {
        Console actualConsole = new Console(
                "Xbox One",
                "Microsoft",
                "8 GB",
                "Intel 1J",
                new BigDecimal(10.44),
                64
        );
        actualConsole.setConsole_id(77);

        Console consoleFound = serviceLayerInTest.getConsoleById(77);

        assertEquals(consoleFound, actualConsole);
    }

    @Test
    public void updateConsole() {

        Console console = new Console(
                "Xbox One",
                "Microsoft",
                "8 GB",
                "Intel 1J",
                new BigDecimal(10.44),
                64
        );
        console.setConsole_id(77);
        console.setManufacturer("Windows");

        Console updatedConsole = serviceLayerInTest.updateConsoleInDb(console);
        assertEquals(updatedConsole, console);
    }

    @Test
    public void deleteConsole() {
        assertTrue(serviceLayerInTest.deleteConsoleFromDb(77));
        assertFalse(serviceLayerInTest.deleteConsoleFromDb(99));

    }

    @Test
    public void getAllConsoles() {
        List<Console> allConsolesFromDb = serviceLayerInTest.getAllConsolesFromDb();
        int amountAllConsoles = allConsolesFromDb.size();

        assertEquals(amountAllConsoles, 3);
    }

    @Test
    public void getAllConsolesByManufacturer() {
        List<Console> sonyConsoles1 = serviceLayerInTest.getAllConsolesFromDbByManufacturer("so");
        List<Console> sonyConsoles2 = serviceLayerInTest.getAllConsolesFromDbByManufacturer("son");

        List<Console> microsoftConsoles1 = serviceLayerInTest.getAllConsolesFromDbByManufacturer("micro");
        List<Console> microsoftConsoles2 = serviceLayerInTest.getAllConsolesFromDbByManufacturer("Mic");

        List<Console> nintendoConsoles = serviceLayerInTest.getAllConsolesFromDbByManufacturer("Nintendo");


        assertEquals(sonyConsoles1.size(), 1);
        assertEquals(sonyConsoles2.size(), 1);
        assertEquals(sonyConsoles1.get(0).getManufacturer().toLowerCase(), "sony");

        assertEquals(microsoftConsoles1.size(), 2);
        assertEquals(microsoftConsoles2.size(), 2);
        assertEquals(microsoftConsoles1.get(1).getManufacturer().toLowerCase(), "microsoft");

        assertEquals(nintendoConsoles.size(), 0);
    }

    public void setUpTshirtDao() {
        tshirtDao = mock(TshirtDaoJdbcTemplate.class);

        Tshirt actualTshirt = new Tshirt(
                "xl",
                "blue",
                "Short sleeve v neck with panda on front",
                new BigDecimal(15.99),
                28);

        actualTshirt.setT_shirt_id(1128);

        Tshirt tshirtToAdd = new Tshirt(
                "xl",
                "blue",
                "Short sleeve v neck with panda on front",
                new BigDecimal(15.99),
                28);

        Tshirt updateTshirt = new Tshirt(
                "xl",
                "pink",
                "Short sleeve v neck with panda on front",
                new BigDecimal(15.99),
                30);

        doReturn(actualTshirt).when(tshirtDao).addTshirt(tshirtToAdd);

        doReturn(actualTshirt).when(tshirtDao).getTshirtById(1128);

        doReturn(true).when(tshirtDao).deleteTshirtById(1128);
        doReturn(false).when(tshirtDao).deleteTshirtById(0);

        doReturn(updateTshirt).when(tshirtDao).updateTshirt(updateTshirt);


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

        List<Tshirt> allTshirts = new ArrayList<>();
        allTshirts.add(t1);
        allTshirts.add(t2);
        allTshirts.add(t3);
        doReturn(allTshirts).when(tshirtDao).getAllTshirts();

        List<Tshirt> allBlueshirts1 = new ArrayList<>();
        allBlueshirts1.add(t1);
        allBlueshirts1.add(t2);
        doReturn(allBlueshirts1).when(tshirtDao).getTshirtsByColor("blu");
        doReturn(allBlueshirts1).when(tshirtDao).getTshirtsByColor("BL");

        List<Tshirt> allWhiteTshirts = new ArrayList<>();
        allWhiteTshirts.add(t3);
        doReturn(allWhiteTshirts).when(tshirtDao).getTshirtsByColor("WHIT");
        doReturn(allWhiteTshirts).when(tshirtDao).getTshirtsByColor("whi");

        List<Tshirt> emptyTshirtList = new ArrayList<>();
        doReturn(emptyTshirtList).when(tshirtDao).getTshirtsByColor("pink");
        doReturn(emptyTshirtList).when(tshirtDao).getTshirtsByColor("red");

        List<Tshirt> allMediumTshirts = new ArrayList<>();
        allMediumTshirts.add(t1);
        doReturn(allMediumTshirts).when(tshirtDao).getTshirtsBysize("m");
        doReturn(allMediumTshirts).when(tshirtDao).getTshirtsBysize("Med");

        List<Tshirt> allLargeTshirts = new ArrayList<>();
        allLargeTshirts.add(t3);
        allLargeTshirts.add(t2);
        doReturn(allLargeTshirts).when(tshirtDao).getTshirtsBysize("lar");
        doReturn(allLargeTshirts).when(tshirtDao).getTshirtsBysize("LARg");

        doReturn(emptyTshirtList).when(tshirtDao).getTshirtsBysize("sm");

    }

    @Test
    public void addTshirt() {
        Tshirt actualTshirt = new Tshirt(
                "xl",
                "blue",
                "Short sleeve v neck with panda on front",
                new BigDecimal(15.99),
                28);

        Tshirt addedTshirt = serviceLayerInTest.addTshirtToDb(actualTshirt);

        actualTshirt.setT_shirt_id(1128);

        assertEquals(addedTshirt, actualTshirt);
    }

    @Test
    public void getTshirtById() {
        Tshirt actualTshirt = new Tshirt(
                "xl",
                "blue",
                "Short sleeve v neck with panda on front",
                new BigDecimal(15.99),
                28);
        actualTshirt.setT_shirt_id(1128);

        Tshirt tshirtFound = serviceLayerInTest.getTshirtFromDbById(1128);

        assertEquals(tshirtFound, actualTshirt);
    }

    @Test
    public void updateTshirt() {
        Tshirt actualTshirt = new Tshirt(
                "xl",
                "blue",
                "Short sleeve v neck with panda on front",
                new BigDecimal(15.99),
                28);
        actualTshirt.setT_shirt_id(1128);
        actualTshirt.setColor("pink");
        actualTshirt.setQuantity(30);

        Tshirt updatedTshirt = serviceLayerInTest.updateTshirtInDb(actualTshirt);
    }

    @Test
    public void deleteTshirt() {
        assertTrue(serviceLayerInTest.deleteTshirtInDb(1128));
        assertFalse(serviceLayerInTest.deleteTshirtInDb(0));
    }

    @Test
    public void getAllTshirts() {
        List<Tshirt> allTs = serviceLayerInTest.getAllTshirts();
        assertEquals(allTs.size(), 3);
    }

    @Test
    public void getAllTshirtsByColor() {
        List<Tshirt> allBlueTs1 = serviceLayerInTest.getAllTshirtsByColor("blu");
        List<Tshirt> allBlueTs2 = serviceLayerInTest.getAllTshirtsByColor("BL");
        assertEquals(allBlueTs1.size(), 2);
        assertEquals(allBlueTs2.size(), 2);

        List<Tshirt> allWhiteTs1 = serviceLayerInTest.getAllTshirtsByColor("WHIT");
        List<Tshirt> allWhiteTs2 = serviceLayerInTest.getAllTshirtsByColor("whi");
        assertEquals(allWhiteTs1.size(), 1);
        assertEquals(allWhiteTs2.size(), 1);

        List<Tshirt> allNoPinkTs = serviceLayerInTest.getAllTshirtsByColor("pink");
        assertEquals(allNoPinkTs.size(), 0);
        List<Tshirt> allNoRedTs = serviceLayerInTest.getAllTshirtsByColor("red");
        assertEquals(allNoRedTs.size(), 0);
    }

    @Test
    public void getAllTshirtsBySize() {
        List<Tshirt> allMediumTshirts1 = serviceLayerInTest.getAllTshirtsBySize("m");
        List<Tshirt> allMediumTshirts2 = serviceLayerInTest.getAllTshirtsBySize("Med");
        assertEquals(allMediumTshirts1.size(), 1);
        assertEquals(allMediumTshirts2.size(), 1);

        List<Tshirt> allLargeTshirts1 = serviceLayerInTest.getAllTshirtsBySize("lar");
        List<Tshirt> allLargeTshirts2 = serviceLayerInTest.getAllTshirtsBySize("LARg");
        assertEquals(allLargeTshirts1.size(), 2);
        assertEquals(allLargeTshirts2.size(), 2);

        List<Tshirt> allSmallTs = serviceLayerInTest.getAllTshirtsBySize("sm");
        assertEquals(allSmallTs.size(), 0);
    }
}
