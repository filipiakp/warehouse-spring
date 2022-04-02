package com.filipiakp.warehousespring.controller.rest;

import com.filipiakp.warehousespring.entities.Contractor;
import com.filipiakp.warehousespring.model.ContractorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contractors")
public class ContractorRestController {

  @Autowired private ContractorRepository contractorRepository;

  @GetMapping
  public ResponseEntity<List<Contractor>> getContractors() {
    return new ResponseEntity<>(contractorRepository.findAll(), HttpStatus.OK);
  }

  @GetMapping("/{nip}")
  public ResponseEntity<Contractor> getContractorDetails(@PathVariable String nip) {
    return new ResponseEntity<>(contractorRepository.findByNip(nip).get(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Contractor> createContractor(@RequestBody Contractor contractor) {
    contractorRepository.save(contractor);
    return new ResponseEntity<>(contractor, HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<Contractor> updateContractor(@RequestBody Contractor newCont) {
    Contractor oldCont = contractorRepository.findByNip(newCont.getNip()).get();
    oldCont.setName(newCont.getName());
    oldCont.setPhoneNumber(newCont.getPhoneNumber());
    oldCont.setCity(newCont.getCity());
    oldCont.setStreet(newCont.getStreet());
    oldCont.setHouseNumber(newCont.getHouseNumber());
    oldCont.setApartmentNumber(newCont.getApartmentNumber());
    oldCont.setSupplier(newCont.isSupplier());
    contractorRepository.save(oldCont);
    return new ResponseEntity<>(oldCont, HttpStatus.OK);
  }

  @DeleteMapping("/{nip}")
  public ResponseEntity<String> deleteContractor(@PathVariable String nip) {
    Optional<Contractor> contractorOptional = contractorRepository.findByNip(nip);
    if (contractorOptional.isPresent()) {
      contractorRepository.delete(contractorOptional.get());
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
