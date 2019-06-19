package com.example.Capstone1_GameStore.dao;

import com.example.Capstone1_GameStore.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GameDaoJdbcTemplate implements GameDao {
    JdbcTemplate gameJdbc;

    @Autowired
    public GameDaoJdbcTemplate(JdbcTemplate jdbcTemplate) {
        gameJdbc = jdbcTemplate;
    }

    public Game mapRowToGame(ResultSet rs, int rowNum) throws SQLException {

        Game game = new Game();

        game.setGame_id(rs.getInt("game_id"));
        game.setTitle(rs.getString("title"));
        game.setDescription(rs.getString("description"));
        game.setStudio(rs.getString("studio"));
        game.setEsrb_rating((rs.getString("esrb_rating")));
        game.setPrice(rs.getBigDecimal("price"));
        game.setQuantity(rs.getInt("quantity"));

        return game;
    }

    private static final String ADD_GAME = "insert into game (title, esrb_rating, description, price, studio, " +
            "quantity) values (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_GAME = "update game set title = ?, esrb_rating = ?, description = ?," +
            " price = ?, studio = ?, quantity = ? where game_id = ?";

    private static final String DELETE_GAME_BY_ID = "delete from game where game_id = ?";

    private static final String GET_GAME_BY_ID = "select * from game where game_id = ?";

    private static final String GET_ALL_GAMES = "select * from game";

    private static final String GET_GAMES_BY_TITLE = "select * from game where title like ?";

    private static final String GET_GAMES_BY_ESRB_RATING = "select * from game where esrb_rating = ?";

    private static final String GET_GAMES_BY_STUDIO = "select * from game where studio like ?";


    @Override
    @Transactional
    public Game addGame(Game game) {

        gameJdbc.update(ADD_GAME,
                game.getTitle(),
                game.getEsrb_rating(),
                game.getDescription(),
                game.getPrice(),
                game.getStudio(),
                game.getQuantity());

        int id = gameJdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
        game.setGame_id(id);

        return game;
    }

    @Override
    @Transactional
    public Game getGameById(int id) {
        try {
            return gameJdbc.queryForObject(GET_GAME_BY_ID, this::mapRowToGame, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public Game updateGame(Game game) {

        int gameUpdatedResult = gameJdbc.update(UPDATE_GAME,
                    game.getTitle(),
                    game.getEsrb_rating(),
                    game.getDescription(),
                    game.getPrice(),
                    game.getStudio(),
                    game.getQuantity(),
                    game.getGame_id());

        return gameUpdatedResult == 1 ? game : null;
    }

    @Override
    @Transactional
    public boolean deleteGameById(int id) {

        int sqlDeletionResponse = gameJdbc.update(DELETE_GAME_BY_ID, id);

        return sqlDeletionResponse == 1 ?  true : false;
    }

    @Override
    @Transactional
    public List<Game> getAllGames() {
        return gameJdbc.query(GET_ALL_GAMES, this::mapRowToGame);
    }

    @Override
    @Transactional
    public List<Game> getGamesByTitle(String title) {
        // Add % after title so sql can search titles that start with the given string
        title = title + "%";

        return gameJdbc.query(GET_GAMES_BY_TITLE, this::mapRowToGame, title);
    }

    @Override
    @Transactional
    public List<Game> getGamesByEsrbRating(String esrb_rating) {
        esrb_rating = esrb_rating.substring(0,1);
        System.out.println(esrb_rating);
        return gameJdbc.query(GET_GAMES_BY_ESRB_RATING, this::mapRowToGame, esrb_rating);
    }

    @Override
    @Transactional
    public List<Game> getGamesByStudio(String studio) {
        // Add % after studio so sql can search studio that start with the given string
        studio = studio + "%";

        return gameJdbc.query(GET_GAMES_BY_STUDIO, this::mapRowToGame, studio);
    }
}
