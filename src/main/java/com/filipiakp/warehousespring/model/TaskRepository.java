package com.filipiakp.warehousespring.model;

import com.filipiakp.warehousespring.entities.Task;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
  Optional<Task> findById(long id);
}
