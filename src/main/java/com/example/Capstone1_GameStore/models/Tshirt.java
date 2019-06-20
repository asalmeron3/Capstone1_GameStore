package com.example.Capstone1_GameStore.models;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

public class Tshirt extends StoreItem {

    @PositiveOrZero(message = "'t_shirt_id' must be greater than 0")
    @Digits(integer = 11, fraction = 0, message = "'t_shirt_id' cannot exceed 1E11 ")
    private int t_shirt_id;
    @NotBlank(message = "'size' cannot be empty, missing, or blank")
    @Size(max = 20, message = "'size' must be less than 20 characters")
    private String size;
    @NotBlank(message = "'color' cannot be empty, missing, or blank")
    @Size(max = 20, message = "'color' must be less than 20 characters")
    private  String color;
    @NotBlank(message = "'description' cannot be empty, missing, or blank")
    @Size(max = 255, message = "'description' must be less than 255 characters")
    private String description;

    public Tshirt() {
    }

    public Tshirt(String size, String color, String description, BigDecimal price, int quantity){

        super(price, quantity);

        this.size = size;
        this.color = color;
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getT_shirt_id() {
        return t_shirt_id;
    }

    public void setT_shirt_id(int t_shirt_id) {
        this.t_shirt_id = t_shirt_id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tshirt tshirt = (Tshirt) o;
        return t_shirt_id == tshirt.t_shirt_id &&
                Objects.equals(size, tshirt.size) &&
                Objects.equals(color, tshirt.color) &&
                Objects.equals(description, tshirt.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), t_shirt_id, size, color, description);
    }
}
