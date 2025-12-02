package com.kumar.week2.advices;

import com.kumar.week2.exceptions.ResourceNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException exception) {
    // create apiError object using builder
    ApiError apiError = ApiError.builder()
            .status(HttpStatus.NOT_FOUND)
            .message(exception.getMessage())
            .build();
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }

  // other Exceptions like generic -> `Exception.class` is the parent of all Exception classes
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleInternalServerError(Exception exception) {
    // create apiError object using builder
    ApiError apiError = ApiError.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .message(exception.getMessage())
            .build();

    return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // another
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleInputValidationErrors(MethodArgumentNotValidException exception) {
    List<String> errors = exception.
            getBindingResult()
            .getAllErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList();

    ApiError apiError = ApiError.builder()
            .status(HttpStatus.BAD_REQUEST)
            .message(errors.toString())
            .build();

    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }
}
