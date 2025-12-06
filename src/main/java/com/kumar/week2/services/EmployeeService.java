package com.kumar.week2.services;

import com.kumar.week2.dto.EmployeeDto;
import com.kumar.week2.entities.EmployeeEntity;
import com.kumar.week2.exceptions.ResourceNotFoundException;
import com.kumar.week2.repositories.EmployeeRepository;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
public class EmployeeService {

  // inject the EmployeeRepository
  private final EmployeeRepository employeeRepository;
  // inject the modelMapper
  private final ModelMapper modelMapper;

  // constructor generated
  public EmployeeService(
    EmployeeRepository employeeRepository,
    ModelMapper modelMapper
  ) {
    this.employeeRepository = employeeRepository;
    this.modelMapper = modelMapper;
  }

  // method
  public Optional<EmployeeDto> getEmployeeById(Long id) {
    // create Entity
    //    Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
    // convert to DTO
    // return new EmployeeDTO(Objects.requireNonNull(employeeEntity).getId(), employeeEntity.getName(), employeeEntity.getEmail(), employeeEntity.getAge(), employeeEntity.getDateOfJoining(), employeeEntity.getIsActive());

    // create the object of ModelMapper
    // ModelMapper modelMapper = new ModelMapper();
    // requires to params -> source, type class
    //    return employeeEntity.map(employeeEntity2 -> modelMapper.map(employeeEntity, EmployeeDTO.class));
    return employeeRepository
      .findById(id)
      .map(employeeEntity ->
        modelMapper.map(employeeEntity, EmployeeDto.class)
      );
  }

  public List<EmployeeDto> findAll() {
    List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
    // we need to use `Stream` library
    return employeeEntities
      .stream()
      .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDto.class))
      .collect(Collectors.toList());
  }

  public EmployeeDto createEmployee(EmployeeDto employeeDTO) {
    // map before saving -> convert dto to Entity
    EmployeeEntity toBeSavedEntity = modelMapper.map(
      employeeDTO,
      EmployeeEntity.class
    );
    EmployeeEntity savedEmployeeEntity = employeeRepository.save(
      toBeSavedEntity
    );
    // use modelMapper to convert entity to DTO
    return modelMapper.map(savedEmployeeEntity, EmployeeDto.class);
  }

  public EmployeeDto updateEmployeeById(
    Long employeeId,
    EmployeeDto employeeDTO
  ) {
    // first search the employeeById
    EmployeeEntity employeeEntity = employeeRepository
      .findById(employeeId)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "Employee not found with id: " + employeeId
        )
      );

    // Update the existing entity with new values
    modelMapper.map(employeeDTO, employeeEntity);

    EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
    return modelMapper.map(savedEmployee, EmployeeDto.class);
  }

  // Private helper method
  private void validateEmployeeExists(Long employeeId) {
    employeeRepository
      .findById(employeeId)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "Employee not found with id: " + employeeId
        )
      );
  }

  public boolean deleteEmployeeById(Long employeeId) {
    // check
    validateEmployeeExists(employeeId);

    employeeRepository.deleteById(employeeId);
    return true; // Successfully deleted
  }

  public EmployeeDto updatePartialEmployeeById(
    Map<String, Object> newUpdate,
    Long employeeId
  ) {
    // call method
    validateEmployeeExists(employeeId);

    // if not
    EmployeeEntity employeeEntity = employeeRepository
      .findById(employeeId)
      .get();

    // now update only new value -> with `reflection` -> traversing each field and if matched -> update
    newUpdate.forEach((field, value) -> {
      // use Reflection
      Field fieldToBeUpdated = ReflectionUtils.findField(
        EmployeeEntity.class,
        field
      );
      // make it public because as of now fields are private in the Entity class
      // Check if field exists before updating
      if (fieldToBeUpdated != null) {
        fieldToBeUpdated.setAccessible(true);
        // set
        ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
      }
    });

    // save the new fields to the database
    return modelMapper.map(
      employeeRepository.save(employeeEntity),
      EmployeeDto.class
    );
  }
}
