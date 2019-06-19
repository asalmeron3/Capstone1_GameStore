package com.example.Capstone1_GameStore.dao;

import com.example.Capstone1_GameStore.models.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameDaoTest {

    @Autowired
    protected GameDao gameDao;

    @Before
    public void setup() {
        List<Game> allGames = gameDao.getAllGames();

        allGames.stream()
                .forEach(game -> gameDao.deleteGameById(game.getGame_id()));

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
                "assassin's creed 4",
                "M",
                "Stealth action game",
                new BigDecimal(11.2),
                "Encore Software",
                2);

        gameDao.addGame(game1);
        gameDao.addGame(game2);
        gameDao.addGame(game3);
        gameDao.addGame(game4);

    }

    @Test
    public void addGetDeleteGame() {
        Game game1 = new Game(
                "Assassin's Creed",
                "M",
                "Stealth action game",
                new BigDecimal(10.00),
                "Encore Software",
                6);

        Game gameAdded = gameDao.addGame(game1);

        int id = gameAdded.getGame_id();
        game1.setGame_id(id);
        assertEquals(gameAdded, game1);

        assertNull(gameDao.getGameById(0));

        assertTrue(gameDao.deleteGameById(id));
        assertFalse(gameDao.deleteGameById(0));
    }

    @Test
    public void getAllGames() {
        List<Game> allGames = gameDao.getAllGames();

        int listSize = allGames.size();

        assertEquals(listSize, 4);

        Game game = new Game(
                "Assassin's Creed",
                "M",
                "Stealth action game",
                new BigDecimal(10.00),
                "Encore Software",
                6);

        gameDao.addGame(game);

        allGames = gameDao.getAllGames();
        listSize = allGames.size();

        assertEquals(listSize, 5);
    }

    @Test
    public void getGamesByTitle() {
        List<Game> gamesList = gameDao.getGamesByTitle("assassin's creed");
        int totalGamesFound = gamesList.size();

        assertEquals(totalGamesFound, 4);

        gamesList = gameDao.getGamesByTitle("assassin's creed 4");
        totalGamesFound = gamesList.size();

        assertEquals(totalGamesFound, 1);

        gamesList = gameDao.getGamesByTitle("toy's story");
        totalGamesFound = gamesList.size();

        assertEquals(totalGamesFound, 0);
    }

    @Test
    public void getGamesByEsrbRating() {
        List<Game> gamesList = gameDao.getGamesByEsrbRating("mother");
        int totalGamesFound = gamesList.size();

        assertEquals(totalGamesFound, 3);

        gamesList = gameDao.getGamesByEsrbRating("t");
        totalGamesFound = gamesList.size();

        assertEquals(totalGamesFound, 1);

        gamesList = gameDao.getGamesByEsrbRating("p");
        totalGamesFound = gamesList.size();

        assertEquals(totalGamesFound, 0);


    }

    @Test
    public void getGamesByStudio() {
        List<Game> gamesList = gameDao.getGamesByStudio("insomniac");
        int totalGamesFound = gamesList.size();

        assertEquals(totalGamesFound, 1);

        gamesList = gameDao.getGamesByStudio("encore");
        totalGamesFound = gamesList.size();

        assertEquals(totalGamesFound, 3);

        gamesList = gameDao.getGamesByStudio("other");
        totalGamesFound = gamesList.size();

        assertEquals(totalGamesFound, 0);
    }

    @Test
    public void updateGame() {
        Game game = new Game(
                "Assassin's Creed",
                "M",
                "Stealth action game",
                new BigDecimal(10.00),
                "Encore Software",
                6);

        Game gameAdded = gameDao.addGame(game);

        int id = gameAdded.getGame_id();

        game.setGame_id(id);
        game.setDescription("This is a longer description of this stealth action game");
        game.setQuantity(8);

        Game updatedGame = gameDao.updateGame(game);

        assertEquals(updatedGame, game);

    }
}
