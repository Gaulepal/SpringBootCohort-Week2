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
  public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException exception) {
    // create apiError object using builder
    ApiError apiError = ApiError.builder()
            .status(HttpStatus.NOT_FOUND)
            .message(exception.getMessage())
            .build();
    // create another method to build the error
    return buildErrorResponseEntity(apiError);
  }

  // other Exceptions like generic -> `Exception.class` is the parent of all Exception classes
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exception) {
    // create apiError object using builder
    ApiError apiError = ApiError.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .message(exception.getMessage())
            .build();

    // return error with newly created method
    return buildErrorResponseEntity(apiError);
  }

  // another
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<?>> handleInputValidationErrors(MethodArgumentNotValidException exception) {
    List<String> errors = exception.
            getBindingResult()
            .getAllErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList();

    ApiError apiError = ApiError.builder()
            .status(HttpStatus.BAD_REQUEST)
            .message("Input validation failed")
            .subErrors(errors)
            .build();

    // return error with newly created method
    return buildErrorResponseEntity(apiError);
  }

  // helper method to build the error -> internal method must be below
  private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
  }

}
