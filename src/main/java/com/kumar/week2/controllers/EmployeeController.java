package com.kumar.week2.controllers;

import com.kumar.week2.entities.EmployeeEntity;
import com.kumar.week2.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

  // connect the repository
  private final EmployeeRepository employeeRepository;

  public EmployeeController(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  // take id as input
  @GetMapping("/{employeeId}")
  public EmployeeEntity getEmployeeById(@PathVariable(name = "employeeId") Long id) {
//    return new EmployeeDTO(id, "Hora", "go@gm.com", 12, LocalDate.of(2005, 10, 4), true);
    return employeeRepository.findById(id).orElse(null);
  }

  // list of the employees -> required false to make it optional -> default is required
  @GetMapping
  public List<EmployeeEntity> getAllEmployees(@RequestParam(required = false) Integer age, @RequestParam(required = false) String name) {
//    return "Hello, age is: " + age + " name is: " + name;

    return employeeRepository.findAll();
  }

  // create employee
  @PostMapping
  public EmployeeEntity createEmployee(@RequestBody EmployeeEntity employeeEntity) {
    return employeeRepository.save(employeeEntity);
  }
}
