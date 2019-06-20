package com.example.Capstone1_GameStore.controllers;

import com.example.Capstone1_GameStore.models.Console;
import com.example.Capstone1_GameStore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ConsoleController {
    @Autowired
    ServiceLayer service;

    @RequestMapping(value = "/consoles", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Console> getAllConsoles(@RequestParam(value = "manufacturer", required = false) String manufacturer) {
        if (manufacturer != null) {
            return service.getAllConsolesFromDbByManufacturer(manufacturer);
        }
        return service.getAllConsolesFromDb();
    }

    @RequestMapping(value = "/console", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Console addConsole(@RequestBody @Valid Console console) {
        return service.addConsoleToDb(console);
    }

    @RequestMapping(value = "/console/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Console getConsoleById(@PathVariable int id) {
        return service.getConsoleById(id);
    }

    @RequestMapping(value = "/console/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Console updateConsole(@PathVariable int id, @RequestBody @Valid Console console) {
        console.setConsole_id(id);
        return service.updateConsoleInDb(console);
    }

    @RequestMapping(value = "/console/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean deleteConsoleById(@PathVariable int id) {
        return service.deleteConsoleFromDb(id);
    }
}
