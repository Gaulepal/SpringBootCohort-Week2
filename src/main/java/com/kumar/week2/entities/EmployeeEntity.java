package com.kumar.week2.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@EqualsAndHashCode // 👈we should not use in the Entity because it has the problem with the `id` fields- > specially relational database
@Table(name = "employees")
public class EmployeeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  private String email;

  private Integer age;

  private LocalDate dateOfJoining;

  private Boolean isActive;

}
