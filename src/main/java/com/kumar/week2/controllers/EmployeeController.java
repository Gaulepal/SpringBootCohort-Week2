package com.kumar.week2.controllers;

import com.kumar.week2.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class EmployeeController {

  @GetMapping(path = "/")
  public String getMessage() {
    return "Hello World!";
  }

  // take id as input
  @GetMapping("/employees/{employeeId}")
  public EmployeeDTO getEmployeeById(@PathVariable Long employeeId) {
    return new EmployeeDTO(1L, "Hora", "go@gm.com", 12, LocalDate.of(2005, 10, 4), true);
  }

}
