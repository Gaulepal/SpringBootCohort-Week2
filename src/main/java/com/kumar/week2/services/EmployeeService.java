package com.kumar.week2.services;

import com.kumar.week2.dto.EmployeeDTO;
import com.kumar.week2.entities.EmployeeEntity;
import com.kumar.week2.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

  // inject the EmployeeRepository
  private final EmployeeRepository employeeRepository;
  // inject the modelMapper
  private final ModelMapper modelMapper;

  // constructor generated
  public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
    this.employeeRepository = employeeRepository;
    this.modelMapper = modelMapper;
  }

  // method
  public Optional<EmployeeDTO> getEmployeeById(Long id) {
    // create Entity
//    Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
    // convert to DTO
    // return new EmployeeDTO(Objects.requireNonNull(employeeEntity).getId(), employeeEntity.getName(), employeeEntity.getEmail(), employeeEntity.getAge(), employeeEntity.getDateOfJoining(), employeeEntity.getIsActive());

    // create the object of ModelMapper
    // ModelMapper modelMapper = new ModelMapper();
    // requires to params -> source, type class
//    return employeeEntity.map(employeeEntity2 -> modelMapper.map(employeeEntity, EmployeeDTO.class));
    return employeeRepository.findById(id).map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class));
  }

  public List<EmployeeDTO> findAll() {
    List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
    // we need to use `Stream` library
    return employeeEntities.stream()
            .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
            .collect(Collectors.toList());
  }

  public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
    // map before saving -> convert dto to Entity
    EmployeeEntity toBeSavedEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
    EmployeeEntity savedEmployeeEntity = employeeRepository.save(toBeSavedEntity);
    // use modelMapper to convert entity to DTO
    return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
  }

  public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
    // first search the employeeById
    EmployeeEntity employeeEntity = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Employee not found with id: " + employeeId
            ));

    // Update the existing entity with new values
    modelMapper.map(employeeDTO, employeeEntity);

    EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
    return modelMapper.map(savedEmployee, EmployeeDTO.class);
  }

  // TODO: as we repeat userExist -> make a new method
  public boolean isEmployeeExist(Long employeeId) {
    // check
    return employeeRepository.existsById(employeeId);
  }

  public boolean deleteEmployeeById(Long employeeId) {
    // check
    boolean exists = isEmployeeExist(employeeId);

    if (exists) {
      employeeRepository.deleteById(employeeId);
      return true; // Successfully deleted
    }
    return false; // not found
  }

  public EmployeeDTO updatePartialEmployeeById(Map<String, Object> newUpdate, Long employeeId) {
    boolean exists = isEmployeeExist(employeeId);

    if (!exists) return null;

    // if not
    EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();

    // now update only new value -> with `reflection` -> traversing each field and if matched -> update
    newUpdate.forEach((field, value) -> {
      // use Reflection
      Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class, field);
      // make it public because as of now fields are private in the Entity class
      // Check if field exists before updating
      if (fieldToBeUpdated != null) {
        fieldToBeUpdated.setAccessible(true);
        // set
        ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
      }
    });

    // save the new fields to the database
    return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
  }
}

