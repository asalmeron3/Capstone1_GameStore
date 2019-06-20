package com.example.Capstone1_GameStore.dao;

import com.example.Capstone1_GameStore.models.Console;

import java.util.List;

public interface ConsoleDao {
    Console addConsole(Console console);
    Console getConsoleById(int id);
    Console updateConsole(Console console);
    boolean deleteConsoleById(int id);

    List<Console> getAllConsoles();
    List<Console> getAllConsolesByManufacturere(String manufacturer);
}
