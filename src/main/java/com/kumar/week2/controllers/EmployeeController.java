package com.kumar.week2.controllers;

import com.kumar.week2.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

  // take id as input
  @GetMapping("/{employeeId}")
  public EmployeeDTO getEmployeeById(@PathVariable Long employeeId) {
    return new EmployeeDTO(employeeId, "Hora", "go@gm.com", 12, LocalDate.of(2005, 10, 4), true);
  }

  // list of the employees -> required false to make it optional -> default is required
  @GetMapping
  public String getAllEmployees(@RequestParam(required = false) Integer age, @RequestParam(required = false) String name) {
    return "Hello, age is: " + age + " name is: " + name;
  }
}
