package com.filipiakp.warehousespring.model;

import com.filipiakp.warehousespring.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
  Optional<Employee> findById(int id);
}
