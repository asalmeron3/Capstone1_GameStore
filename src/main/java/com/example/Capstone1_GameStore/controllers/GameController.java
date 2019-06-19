package com.example.Capstone1_GameStore.controllers;

import com.example.Capstone1_GameStore.models.Game;
import com.example.Capstone1_GameStore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class GameController {

    @Autowired
    ServiceLayer service;

    @RequestMapping(value = "/videogames", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Game> getAllGames(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "studio", required = false) String studio,
            @RequestParam(value = "esrb_rating", required = false) String esrbRating) {

        if (title != null) {
            return service.getAllGamesWithTitleFromDb(title);
        } else if (studio != null) {
            return service.getAllGameByStudioFromDb(studio);
        } else if (esrbRating != null) {
            return service.getAllGamesWithEsrbRatingFromDb(esrbRating);
        }

        return service.getAllGamesInDb();
    }

    @RequestMapping(value = "/videogame", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Game addAGame(@RequestBody @Valid Game game) throws Exception {
        return service.saveGameToDb(game);
    }

    @RequestMapping(value = "/videogame/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Game addAGame(@PathVariable int id) {
        return service.getGameByIdFromDb(id);
    }

    @RequestMapping(value = "/videogame/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteAGame(@PathVariable int id) {

        boolean isDeleted = service.deleteGameFromDb(id);

        if (isDeleted){
            return "The game with game_id " + id + " has been deleted.";
        }

        return "No game with game_id of" + id + " was found. Nothing was deleted";
    }

    @RequestMapping(value = "/videogame/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Game updateGame(@PathVariable int id, @RequestBody @Valid Game game) {
        game.setGame_id(id);
        return service.updateGameInDb(game);
    }


}
