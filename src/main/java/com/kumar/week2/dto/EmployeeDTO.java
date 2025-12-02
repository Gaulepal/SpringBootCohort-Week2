package com.kumar.week2.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeDTO {

  private Long id;

  @NotBlank(message = "name field cannot be blank!") // user may pass just empty spaces
  @Size(min = 2, max = 15, message = "name should be at least 2 and max of 15 character long")
  private String name;

  @Email(message = "email is not valid!")
  private String email;

  @Min(value = 18, message = "minor cannot work")
  @Max(value = 64, message = "age should not exceed 64")
  private Integer age;

  // add role -> ADMIN or USER -> regular expression always starts with `^` and ends with `$`
  @Pattern(regexp = "^(ADMIN|USER)$")
  private String role;

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
