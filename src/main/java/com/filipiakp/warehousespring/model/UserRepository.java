package com.filipiakp.warehousespring.model;

import com.filipiakp.warehousespring.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUsername(String username);
}
