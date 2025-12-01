package com.kumar.week2.controllers;

import com.kumar.week2.dto.EmployeeDTO;
import com.kumar.week2.services.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

  // connect the repository
  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  // take id as input
  @GetMapping("/{employeeId}")
  public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id) {
    return employeeService.getEmployeeById(id);
  }

  // list of the employees -> required false to make it optional -> default is required
//  @GetMapping
//  public List<EmployeeEntity> getAllEmployees(@RequestParam(required = false) Integer age, @RequestParam(required = false) String name) {
//    return employeeService.findAll();
//  }
//
//  // create employee
//  @PostMapping
//  public EmployeeEntity createEmployee(@RequestBody EmployeeEntity employeeEntity) {
//    return employeeRepository.save(employeeEntity);
//  }
}
