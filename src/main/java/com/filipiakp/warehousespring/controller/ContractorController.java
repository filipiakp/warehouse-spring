package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Contractor;
import com.filipiakp.warehousespring.model.ContractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@Controller
public class ContractorController {

	@Autowired
	ContractorRepository repository;

	@RequestMapping("/contractors/add")
	String add(Model model){
		model.addAttribute("contractor",new Contractor());
		return "contractorForm";
	}

	@RequestMapping(value="/saveContractor", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, method=RequestMethod.POST)
	String saveContractor(@RequestParam Map<String, String> data){
		Contractor contractor = repository.existsById(data.get("nip"))?repository.findByNip(data.get("nip")).get():new Contractor();
		contractor.setNip(data.get("nip"));
		contractor.setSupplier(Boolean.parseBoolean(data.get("supplier")));
		contractor.setName(data.get("name"));
		contractor.setPhoneNumber(data.get("phoneNumber"));
		contractor.setCity(data.get("city"));
		contractor.setStreet(data.get("street"));
		contractor.setHouseNumber(data.get("houseNumber"));
		contractor.setApartmentNumber(data.get("apartmentNumber"));
		repository.save(contractor);
		return "redirect:/contractors";
	}

	@RequestMapping("/contractors")
	String getAll(Model model){
		model.addAttribute("contractors",repository.findAll());
		return "contractors";
	}

	@RequestMapping("/contractors/edit/{nip}")
	String edit(@PathVariable String nip, Model model){
		model.addAttribute("contractor",repository.findByNip(nip));
		return "contractorForm";
	}

	@RequestMapping("/contractors/delete/{nip}")
	String deleteContractor(@PathVariable String nip){
		Optional<Contractor> contractorOptional = repository.findByNip(nip);
		if (contractorOptional.isPresent()) {
			repository.delete(contractorOptional.get());
		}
		return "redirect:/contractors";
	}
}
