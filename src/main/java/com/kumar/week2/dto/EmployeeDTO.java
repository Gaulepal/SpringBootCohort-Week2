package com.kumar.week2.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeDTO {

  private Long id;
  private String name;
  private String email;
  private Integer age;
  private LocalDate dateOfJoining;
  private Boolean isActive;

  // default constructor -> to define the entity
  public EmployeeDTO() {

  }

  // constructor
  public EmployeeDTO(Long id, String name, String email, Integer age, LocalDate dateOfJoining, Boolean isActive) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.age = age;
    this.dateOfJoining = dateOfJoining;
    this.isActive = isActive;
  }

}
