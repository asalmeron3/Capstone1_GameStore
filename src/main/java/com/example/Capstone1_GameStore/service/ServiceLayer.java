package com.example.Capstone1_GameStore.service;

import com.example.Capstone1_GameStore.dao.ConsoleDao;
import com.example.Capstone1_GameStore.dao.GameDao;
import com.example.Capstone1_GameStore.models.Console;
import com.example.Capstone1_GameStore.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceLayer {

    private GameDao serviceLayerGameDao;
    private ConsoleDao serviceLayerConsoleDao;
    @Autowired
    public ServiceLayer(GameDao gameDao, ConsoleDao consoleDao) {

        serviceLayerGameDao = gameDao;
        serviceLayerConsoleDao = consoleDao;
    }

    // Game API
    public Game saveGameToDb(Game game) {
        return serviceLayerGameDao.addGame(game);
    }

    public Game getGameByIdFromDb(int id) {
        return serviceLayerGameDao.getGameById(id);
    }

    public Game updateGameInDb(Game game) {
        return serviceLayerGameDao.updateGame(game);
    }

    public boolean deleteGameFromDb(int id) {
        return serviceLayerGameDao.deleteGameById(id);
    }

    public List<Game> getAllGamesInDb() {
        return serviceLayerGameDao.getAllGames();
    }

    public List<Game> getAllGamesWithTitleFromDb(String title) {
        return serviceLayerGameDao.getGamesByTitle(title);
    }

    public List<Game> getAllGamesWithEsrbRatingFromDb(String esrbRating) {
        return serviceLayerGameDao.getGamesByEsrbRating(esrbRating);
    }

    public List<Game> getAllGameByStudioFromDb(String studio) {
        return serviceLayerGameDao.getGamesByStudio(studio);
    }


    // Console API
    public Console addConsoleToDb(Console console) {
        return serviceLayerConsoleDao.addConsole(console);
    }

    public Console getConsoleById(int id) {
        return serviceLayerConsoleDao.getConsoleById(id);
    }

    public Console updateConsoleInDb(Console console) {
        return serviceLayerConsoleDao.updateConsole(console);
    }

    public boolean deleteConsoleFromDb(int id) {
        return serviceLayerConsoleDao.deleteConsoleById(id) ? true : false;
    }

    public List<Console> getAllConsolesFromDb() {
        return serviceLayerConsoleDao.getAllConsoles();
    }

    public List<Console> getAllConsolesFromDbByManufacturer(String manufacturer) {
        return serviceLayerConsoleDao.getAllConsolesByManufacturere(manufacturer);
    }


    // T-Shirt API
}
