package com.kumar.week2.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder // add builder pattern to create easily
public class ApiError {

  private HttpStatus status;
  private String message;
  private List<String> subErrors;
}
