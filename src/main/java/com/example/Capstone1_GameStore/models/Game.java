package com.example.Capstone1_GameStore.models;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

public class Game extends StoreItem {
    @PositiveOrZero(message = "'game_id' must be greater than 0")
    @Digits(integer = 11, fraction = 0, message = "'game_id' cannot exceed 1E11 ")
    private int game_id;
    @NotBlank(message = "'title' cannot be empty, missing, or blank")
    @Size(max = 50, message = "'title' must be less than 50 characters")
    private String title;
    @NotBlank(message = "'esrb_rating' cannot be empty, missing, or blank")
    @Size(max = 50, message = "'esrb_rating' must be less than 50 characters")
    private  String esrb_rating;
    @NotBlank(message = "'description' cannot be empty, missing, or blank")
    @Size(max = 255, message = "'description' must be less than 255 characters")
    private String description;
    @NotBlank(message = "'studio' cannot be empty, missing, or blank")
    @Size(max = 50, message = "'studio' must be less than 50 characters")
    private String studio;

    public Game() {
    }

    public Game(String title, String esrb_rating, String description, BigDecimal price, String studio, int quantity) {
        super(price, quantity);
        this.title = title;
        this.esrb_rating = esrb_rating;
        this.description = description;
        this.studio = studio;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEsrb_rating() {
        return esrb_rating;
    }

    public void setEsrb_rating(String esrb_rating) {
        this.esrb_rating = esrb_rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return game_id == game.game_id &&
                Objects.equals(title, game.title) &&
                Objects.equals(esrb_rating, game.esrb_rating) &&
                Objects.equals(description, game.description) &&
                Objects.equals(studio, game.studio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game_id, title, esrb_rating, description, studio);
    }
}
