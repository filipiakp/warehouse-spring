package com.filipiakp.warehousespring.model;

import com.filipiakp.warehousespring.entities.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractorRepository extends JpaRepository<Contractor, String> {
	Optional<Contractor> findByNip(String nip);
}
