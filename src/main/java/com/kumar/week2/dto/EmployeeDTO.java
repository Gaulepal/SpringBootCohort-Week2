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
  @NotBlank(message = "email field cannot be blank!")
  private String email;

  @Min(value = 18, message = "minor cannot work")
  @Max(value = 64, message = "age should not exceed 64")
  @NotNull
  @Positive
  private Integer age;

  // add role -> ADMIN or USER -> regular expression always starts with `^` and ends with `$`
  @Pattern(regexp = "^(ADMIN|USER)$", message = "role can be USER or ADMIN")
  @NotBlank(message = "role cannot be black!")
  private String role;

  @NotNull(message = "salary field is required!")
  @Positive
  @Digits(integer = 6, fraction = 2, message = "salary cannot exceed 6 figure and 2 digits after decimal")
  // not more than 6 figure and after decimal it has 2 digits
  private Double salary;

  @PastOrPresent(message = "date of joining should be in past or present day!")
  private LocalDate dateOfJoining;

  @NotNull
  private Boolean isActive;

  // default constructor -> to define the entity
  public EmployeeDTO() {
  }

  // constructor
  public EmployeeDTO(Long id, String name, String email, Integer age, String role, Double salary, LocalDate dateOfJoining, Boolean isActive) {
    this.id = id;

    this.name = name;

    this.email = email;

    this.age = age;

    this.role = role;

    this.salary = salary;

    this.dateOfJoining = dateOfJoining;

    this.isActive = isActive;
  }

}
