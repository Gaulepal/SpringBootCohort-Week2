package com.kumar.week2.controllers;

import com.kumar.week2.dto.EmployeeDTO;
import com.kumar.week2.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

  // connect the repository
  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  // take id as input
  @GetMapping(path = "/{employeeId}")
  public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id) {
    return employeeService.getEmployeeById(id);
  }

  // list of the employees -> required false to make it optional -> default is required
  @GetMapping
  public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false) Integer age, @RequestParam(required = false) String name) {
    return employeeService.findAll();
  }

  // create employee
  @PostMapping
  public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) {
    return employeeService.save(employeeDTO);
  }

  // PUT -> when we need to update the entire employee details
  @PutMapping(path = "/{employeeId}")
  public EmployeeDTO updateEmployeeById(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long employeeId) {
    return employeeService.updateEmployeeById(employeeId, employeeDTO);
  }

  // delete by employeeId
  @DeleteMapping(path = "/{employeeId}")
  public boolean deleteEmployeeById(@PathVariable Long employeeId) {
    return employeeService.deleteEmployeeById(employeeId);
  }

}
