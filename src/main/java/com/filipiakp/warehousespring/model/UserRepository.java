package com.filipiakp.warehousespring.model;

import com.filipiakp.warehousespring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {}
