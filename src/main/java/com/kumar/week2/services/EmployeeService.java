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

  public EmployeeDTO save(EmployeeDTO employeeDTO) {
    // map before saving -> convert dto to Entity
    EmployeeEntity toBeSavedEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
    EmployeeEntity savedEmployeeEntity = employeeRepository.save(toBeSavedEntity);
    // use modelMapper to convert entity to DTO
    return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
  }

  public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
    // what we want to do if we do not find the employeeId in the database -> create new or throw exception (ask manager)
    // case 1. for us -> lets create new if not found
    EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class); // convert DTO to Entity
    // set the id to new Entity
    employeeEntity.setId(employeeId);
    // save new employeeEntity to the database
    EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);

    // case 2. -> if we found then update
    // try to find with given map -> it works like HashMap -> if key is present then update
    return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
  }
}
