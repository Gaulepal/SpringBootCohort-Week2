package com.kumar.week2.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class MapperConfig {

  @Bean
  // this will be the factory method that will return the object of ModelMapper
  public ModelMapper getModelMapper() {
    return new ModelMapper();
  }
}
