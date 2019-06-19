package com.example.Capstone1_GameStore.models;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

public class Game {
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
    @NotNull(message = "'price' cannot be empty, missing, or blank")
    @Positive(message = "'price' MUST be greater than or equal to 0.00")
    @Digits(integer = 5, fraction = 2, message = "'price' must be an non-string number less than or equal to 99999.99")
    private BigDecimal price;
    @NotBlank(message = "'studio' cannot be empty, missing, or blank")
    @Size(max = 50, message = "'studio' must be less than 50 characters")
    private String studio;
    @PositiveOrZero(message = "'quantity' must be greater than or equal to 0")
    @NotNull(message = "'quantity' cannot be empty, missing, or blank")
    @Digits(integer = 11, fraction = 0, message = "'quantity' must be an integer less than 1E11")
    private int quantity;

    public Game() {
    }

    public Game(String title, String esrb_rating, String description, BigDecimal price, String studio, int quantity) {
        this.title = title;
        this.esrb_rating = esrb_rating;
        this.description = description;
        setPrice(price);
        this.studio = studio;
        this.quantity = quantity;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.price = price;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return game_id == game.game_id &&
                quantity == game.quantity &&
                Objects.equals(title, game.title) &&
                Objects.equals(esrb_rating, game.esrb_rating) &&
                Objects.equals(description, game.description) &&
                Objects.equals(price, game.price) &&
                Objects.equals(studio, game.studio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game_id, title, esrb_rating, description, price, studio, quantity);
    }
}
