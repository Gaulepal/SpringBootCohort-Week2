package com.kumar.week2.services;

import com.kumar.week2.dto.EmployeeDTO;
import com.kumar.week2.entities.EmployeeEntity;
import com.kumar.week2.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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
  public EmployeeDTO getEmployeeById(Long id) {
    // create Entity
    EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
//    // convert to DTO
//    return new EmployeeDTO(Objects.requireNonNull(employeeEntity).getId(), employeeEntity.getName(), employeeEntity.getEmail(), employeeEntity.getAge(), employeeEntity.getDateOfJoining(), employeeEntity.getIsActive());

    // create the object of ModelMapper
//    ModelMapper modelMapper = new ModelMapper();
    // requires to params -> source, type class
    return modelMapper.map(employeeEntity, EmployeeDTO.class);
  }

  public List<EmployeeDTO> findAll() {
    List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
    // we need to use `Stream` library
    return employeeEntities.stream()
            .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
            .collect(Collectors.toList());
  }
}
