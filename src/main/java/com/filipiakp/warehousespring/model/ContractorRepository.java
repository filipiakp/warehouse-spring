package com.filipiakp.warehousespring.model;

import com.filipiakp.warehousespring.entities.Contractor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractorRepository extends JpaRepository<Contractor, String> {
  Optional<Contractor> findByNip(String nip);
}
