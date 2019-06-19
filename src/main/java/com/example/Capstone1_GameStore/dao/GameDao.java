package com.example.Capstone1_GameStore.dao;

import com.example.Capstone1_GameStore.models.Game;

import java.util.List;

public interface GameDao {
    Game addGame(Game game);
    Game getGameById(int id);
    Game updateGame(Game game);
    boolean deleteGameById(int id);

    List<Game> getAllGames();
    List<Game> getGamesByTitle(String title);
    List<Game> getGamesByEsrbRating(String esrb_rating);
    List<Game> getGamesByStudio(String studio);
}
