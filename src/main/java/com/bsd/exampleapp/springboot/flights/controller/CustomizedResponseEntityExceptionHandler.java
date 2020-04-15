package com.bsd.exampleapp.springboot.flights.controller;

import com.bsd.exampleapp.springboot.flights.other.ErrorDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detailMessage = "Field '" + ex.getBindingResult().getFieldError().getField() + "' " +
                ex.getBindingResult().getFieldError().getDefaultMessage() + ".";
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Not allowed value of input data field.", detailMessage);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detailMessage = ex.getRootCause()==null ? ex.getClass().getSimpleName() : ex.getRootCause().getMessage();
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Not relevant input data.", detailMessage);
        return handleExceptionInternal(ex, errorDetails, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public final ResponseEntity<Object> handleBadRequest(RuntimeException ex, final WebRequest request) {
        String detailMessage = ex.getMessage()==null ? ex.getClass().getSimpleName() : ex.getMessage();
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "No integrity of input data.", detailMessage);
        return handleExceptionInternal(ex, errorDetails, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public final ResponseEntity<Object> handleNotExistId(RuntimeException ex, final WebRequest request) {
        String detailMessage = ex.getMessage()==null ? ex.getClass().getSimpleName() : ex.getMessage();
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Data not exists.", detailMessage);
        return handleExceptionInternal(ex, errorDetails, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
