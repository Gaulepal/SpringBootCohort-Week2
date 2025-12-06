package com.kumar.week2.configs;

import com.kumar.week2.dto.EmployeeDto;
import com.kumar.week2.entities.EmployeeEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

  @Bean
  // this will be the factory method that will return the object of ModelMapper
  public ModelMapper getModelMapper() {
    ModelMapper modelMapper = new ModelMapper();

    // Skip ID field when mapping DTO to Entity
    modelMapper
      .typeMap(EmployeeDto.class, EmployeeEntity.class)
      .addMappings(mapper -> mapper.skip(EmployeeEntity::setId));

    return modelMapper;
  }
}
