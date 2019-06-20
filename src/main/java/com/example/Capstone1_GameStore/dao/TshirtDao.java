package com.example.Capstone1_GameStore.dao;

import com.example.Capstone1_GameStore.models.Tshirt;

import java.util.List;

public interface TshirtDao {
    Tshirt addTshirt(Tshirt tshirt);
    Tshirt getTshirtById(int id);
    Tshirt updateTshirt(Tshirt tshirt);
    boolean deleteTshirtById(int id);

    List<Tshirt> getAllTshirts();
    List<Tshirt> getTshirtsByColor(String color);
    List<Tshirt> getTshirtsBysize(String size);
}
