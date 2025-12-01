package com.kumar.week2.repositories;

import com.kumar.week2.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

  List<EmployeeEntity> findByName(String name);

}
