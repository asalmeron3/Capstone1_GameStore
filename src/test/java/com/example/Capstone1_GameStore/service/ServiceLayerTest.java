package com.example.Capstone1_GameStore.service;

import com.example.Capstone1_GameStore.dao.GameDao;
import com.example.Capstone1_GameStore.dao.GameDaoJdbcTemplate;
import com.example.Capstone1_GameStore.models.Game;
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

    @Before
    public void setUpServiceLayerInTestAndMockDbs() throws Exception {

        setUpGameMock();

        serviceLayerInTest = new ServiceLayer(gameDaoForTest);
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

//
//        System.out.println("-------------------   Actual Data we Want ----------------- ");
//        System.out.println(actualGameFromServiceLayer.getGame_id());
//        System.out.println(actualGameFromServiceLayer.getTitle());
//        System.out.println(actualGameFromServiceLayer.getDescription());
//        System.out.println(actualGameFromServiceLayer.getStudio());
//        System.out.println(actualGameFromServiceLayer.getEsrb_rating());
//        System.out.println(actualGameFromServiceLayer.getPrice());
//        System.out.println(actualGameFromServiceLayer.getQuantity());
//
//        System.out.println("\n\n-------------------  Data Mock sent back ----------------- ");
//        System.out.println(gameFromServiceLayer.getGame_id());
//        System.out.println(gameFromServiceLayer.getTitle());
//        System.out.println(gameFromServiceLayer.getDescription());
//        System.out.println(gameFromServiceLayer.getStudio());
//        System.out.println(gameFromServiceLayer.getEsrb_rating());
//        System.out.println(gameFromServiceLayer.getPrice());
//        System.out.println(gameFromServiceLayer.getQuantity());


}
