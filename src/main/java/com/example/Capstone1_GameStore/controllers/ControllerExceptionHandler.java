package com.example.Capstone1_GameStore.controllers;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> handleModelsValidation(MethodArgumentNotValidException e, WebRequest request) {

        //BindingResult holds the validation errors
        BindingResult result = e.getBindingResult();

        //Validation errors are stored in FieldError objects
        List<FieldError> fieldErrors = result.getFieldErrors();

        //Translate the FieldErrors to VndErrors
        List<VndErrors.VndError> vndErrorList = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            VndErrors.VndError vndError = new VndErrors.VndError(request.toString(), fieldError.getDefaultMessage());
            vndErrorList.add(vndError);
        }
        // Returns a list of the request sents, the validation message thrown, and any links associated to said message
        // Wrap all of the VndError objects in a VndErrors object
        VndErrors vndErrors = new VndErrors(vndErrorList);

        // Create and return the ResponseEntity
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(vndErrors, HttpStatus.UNPROCESSABLE_ENTITY);

        return responseEntity;
    }
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<VndErrors.VndError> handleInvalidParsing(
            HttpMessageNotReadableException e,
            HttpServletRequest request) {

        String uri = request.getRequestURI();
        String restMethod = request.getMethod();

        String responseMessage = "";


        switch (uri) {
            case "/videogame" :
            case "/console":
                responseMessage = "'quantity' and 'price' cannot have the datatype of string; must be numbers";
            break;

        }

        VndErrors.VndError vndError = new VndErrors.VndError(restMethod + ": " + uri, responseMessage);

        ResponseEntity<VndErrors.VndError> response = new ResponseEntity<>(vndError, HttpStatus.BAD_REQUEST);
        return response;
    }
}
