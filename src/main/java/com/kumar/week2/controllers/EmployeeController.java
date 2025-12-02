package com.kumar.week2.controllers;

import com.kumar.week2.dto.EmployeeDTO;
import com.kumar.week2.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

  // connect the repository
  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  // TODO: exception handler
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<String> handleEmployeeNotFound(NoSuchElementException exception) {
    return new ResponseEntity<>("Employee not found...", HttpStatus.NOT_FOUND);
  }

  // take id as input
  @GetMapping(path = "/{employeeId}")
  public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long id) {
    Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);

    return employeeDTO
            .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
            // .orElse(ResponseEntity.notFound().build());
            .orElseThrow(() -> new NoSuchElementException("Employee not found..."));
  }

  // list of the employees -> required false to make it optional -> default is required
  @GetMapping
  public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) Integer age, @RequestParam(required = false) String name) {
    return ResponseEntity.ok(employeeService.findAll());
  }

  // create employee
  @PostMapping
  public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO inputEmployee) {
    // return employeeService.save(employeeDTO);
    EmployeeDTO savedEmployee = employeeService.createEmployee(inputEmployee);
    // using ResponseEntity constructor using `new`
    return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
  }

  // PUT -> when we need to update the entire employee details
  @PutMapping(path = "/{employeeId}")
  public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO employeeDTO, @PathVariable Long employeeId) {
    return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId, employeeDTO));
  }

  // delete by employeeId
  @DeleteMapping(path = "/{employeeId}")
  public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId) {
    boolean getDeleted = employeeService.deleteEmployeeById(employeeId);
    if (getDeleted) return ResponseEntity.ok(true);
    // else
    return ResponseEntity.notFound().build();
  }

  // partially update the details -> String is the key, where Object is the data we want to change
  @PatchMapping(path = "/{employeeId}")
  public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody @Valid Map<String, Object> newUpdate, @PathVariable Long employeeId) {
    // return  employeeService.updatePartialEmployeeById(newUpdate, employeeId);
    EmployeeDTO updatedEmployee = employeeService.updatePartialEmployeeById(newUpdate, employeeId);

    if (updatedEmployee == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(updatedEmployee);
  }
}
