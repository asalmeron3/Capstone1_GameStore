package com.example.Capstone1_GameStore.service;

import com.example.Capstone1_GameStore.dao.GameDao;
import com.example.Capstone1_GameStore.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceLayer {

    private GameDao serviceLayerGameDao;

    @Autowired
    public ServiceLayer(GameDao gameDao) {
        serviceLayerGameDao = gameDao;
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



    // T-Shirt API
}
