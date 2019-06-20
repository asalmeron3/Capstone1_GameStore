package com.example.Capstone1_GameStore.models;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

public class Console extends StoreItem {

    @PositiveOrZero(message = "'console_id' MUST be a positive integer")
    @Digits(integer = 11, fraction = 0, message = "'console_id must be a value under 1E11")
    private int console_id;
    @NotBlank(message = "'model' cannot be empty, missing, or blank")
    @Size(max = 50, message = "'model' cannot exceed 50 characters")
    private String model;
    @NotBlank(message = "'manufacturer' cannot be empty, missing, or blank")
    @Size(max = 50, message = "'manufacturer' cannot exceed 50 characters")
    private String manufacturer;
    @NotBlank(message = "'memory_amount' cannot be empty, missing, or blank")
    @Size(max = 20, message = "'memory_amount' cannot exceed 20 characters")
    private String memory_amount;
    @NotBlank(message = "'processor' cannot be empty, missing, or blank")
    @Size(max = 20, message = "'processor' cannot exceed 20 characters")
    private String processor;

    public Console() {
    }

    public Console(String model,String manufacturer, String memory_amount, String processor, BigDecimal price, int quantity ) {
        super(price, quantity);
        this.model = model;
        this.manufacturer = manufacturer;
        this.memory_amount = memory_amount;
        this.processor = processor;
    }

    public int getConsole_id() {
        return console_id;
    }

    public void setConsole_id(int console_id) {
        this.console_id = console_id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMemory_amount() {
        return memory_amount;
    }

    public void setMemory_amount(String memory_amount) {
        this.memory_amount = memory_amount;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Console console = (Console) o;
        return console_id == console.console_id &&
                Objects.equals(model, console.model) &&
                Objects.equals(manufacturer, console.manufacturer) &&
                Objects.equals(memory_amount, console.memory_amount) &&
                Objects.equals(processor, console.processor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(console_id, model, manufacturer, memory_amount, processor);
    }
}
