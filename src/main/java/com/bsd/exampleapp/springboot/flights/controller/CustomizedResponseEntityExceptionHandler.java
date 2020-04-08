package com.bsd.exampleapp.springboot.flights.controller;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bsd.exampleapp.springboot.flights.other.ErrorDetails;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    String detailMessage = "Field '" + ex.getBindingResult().getFieldError().getField() + "' " + 
    						ex.getBindingResult().getFieldError().getDefaultMessage() + ".";
	ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed", detailMessage);
    
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  } 
  
}
