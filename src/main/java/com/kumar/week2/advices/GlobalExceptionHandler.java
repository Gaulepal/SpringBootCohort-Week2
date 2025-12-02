package com.kumar.week2.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // TODO: exception handler -> need to handle globally
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ApiError> handleResourceNotFound(NoSuchElementException exception) {
    // create apiError object
    ApiError apiError = ApiError.builder().status(HttpStatus.NOT_FOUND).message("Resource not found...").build();
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }
}
