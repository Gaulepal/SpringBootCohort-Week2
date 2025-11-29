package com.kumar.week2.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

  @GetMapping(path = "/")
  public String getMessage() {
    return "Hello World!";
  }

}
