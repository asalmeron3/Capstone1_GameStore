package com.example.Capstone1_GameStore.controllers;

import com.example.Capstone1_GameStore.models.Tshirt;
import com.example.Capstone1_GameStore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TshirtController {
    @Autowired
    ServiceLayer service;

    @RequestMapping(value = "/tshirts", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Tshirt> getAllTshirts(@RequestParam(value = "color", required = false) String color,
                                      @RequestParam(value = "size", required = false) String size) {

       if (color != null) {
           return service.getAllTshirtsByColor(color);
       } else if (size != null) {
           return service.getAllTshirtsBySize(size);
       }
       return service.getAllTshirts();
    }

    @RequestMapping(value = "/tshirts", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Tshirt addATshirt(@RequestBody @Valid Tshirt tshirt) {
        return service.addTshirtToDb(tshirt);
    }

    @RequestMapping(value = "/tshirts/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Tshirt updateTshirt(@RequestBody @Valid Tshirt tshirt, @PathVariable int id) {
        tshirt.setT_shirt_id(id);
        return service.updateTshirtInDb(tshirt);
    }

    @RequestMapping(value = "/tshirts/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean deleteTshirt(@PathVariable int id ){
        return service.deleteTshirtInDb(id);
    }

    @RequestMapping(value = "/tshirts/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Tshirt getTshirtByID(@PathVariable int id ){
        return service.getTshirtFromDbById(id);
    }

}
