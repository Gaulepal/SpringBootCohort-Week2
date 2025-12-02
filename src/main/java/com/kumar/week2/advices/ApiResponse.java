package com.kumar.week2.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {

  //  @Pattern(regexp = "hh-mm-ss dd-mm-yyyy")
  @JsonFormat(pattern = "hh-mm-ss dd-mm-yyyy") // Jackson will format accordingly
  private LocalDateTime timestamp;

  private T data;

  private ApiError error;

  // default constructor that runs at least once while creating the object -> we can create the timestamp
  public ApiResponse() {
    this.timestamp = LocalDateTime.now();
  }

  // for data
  public ApiResponse(T data) {
    this();// call default constructor
    this.data = data;
  }

  // for error
  public ApiResponse(ApiError apiError) {
    this();// call default constructor
    this.error = apiError;
  }
}
