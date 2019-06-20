package com.example.Capstone1_GameStore.models;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

public abstract class StoreItem {

    @NotNull(message = "'price' cannot be empty, missing, or blank")
    @Positive(message = "'price' MUST be greater than or equal to 0.00")
    @Digits(integer = 5, fraction = 2, message = "'price' must be an non-string number less than or equal to 99999.99")
    private BigDecimal price;
    @PositiveOrZero(message = "'quantity' must be greater than or equal to 0")
    @NotNull(message = "'quantity' cannot be empty, missing, or blank")
    @Digits(integer = 11, fraction = 0, message = "'quantity' must be an integer less than 1E11")
    private int quantity;

    public StoreItem() {
    }

    public StoreItem(BigDecimal price, int quantity) {
        setPrice(price);
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.price = price;
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
        StoreItem storeItem = (StoreItem) o;
        return quantity == storeItem.quantity &&
                Objects.equals(price, storeItem.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, quantity);
    }
}
