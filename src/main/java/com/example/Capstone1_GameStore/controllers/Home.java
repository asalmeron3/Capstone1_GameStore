package com.example.Capstone1_GameStore.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
public class Home {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String homePage() {
        return "Welcome to the Game Store!" +
                "Anyone can view consoles, tshirts, and videogames." +
                "Authorized personnel can go to /authorized to login.";

    }


//    @RequestMapping(value = "/authorized", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public String authorizedPage(Principal principal) {
//        return "Hello " + principal.getName() + "! Looks like you're logged in!";
//    }
}
