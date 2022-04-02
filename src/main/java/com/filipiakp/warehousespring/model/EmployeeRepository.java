package com.filipiakp.warehousespring.model;

import com.filipiakp.warehousespring.entities.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
  Optional<Employee> findById(int id);
}
