package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Contractor;
import com.filipiakp.warehousespring.model.ContractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@Controller
public class ContractorController implements WebMvcConfigurer {

	@Autowired
	private ContractorRepository repository;

	@RequestMapping("/contractors/add")
	private String add(Model model){
		model.addAttribute("contractor",new Contractor());
		return "contractorForm";
	}

	@RequestMapping(value="/saveContractor", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, method=RequestMethod.POST)
	public String saveContractor(@Valid Contractor data, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return "contractorForm";
		}
		Contractor contractor = repository.existsById(data.getNip())?repository.findByNip(data.getNip()).get():new Contractor();
		contractor.setNip(data.getNip());
		contractor.setSupplier(data.isSupplier());
		contractor.setName(data.getName());
		contractor.setPhoneNumber(data.getPhoneNumber());
		contractor.setCity(data.getCity());
		contractor.setStreet(data.getStreet());
		contractor.setHouseNumber(data.getHouseNumber());
		contractor.setApartmentNumber(data.getApartmentNumber());
		repository.save(contractor);
		return "redirect:/contractors";
	}

	@RequestMapping("/contractors")
	public String getAll(Model model){
		model.addAttribute("contractors",repository.findAll());
		return "contractors";
	}

	@RequestMapping("/contractors/edit/{nip}")
	public String edit(@PathVariable String nip, Model model){
		model.addAttribute("contractor",repository.findByNip(nip));
		return "contractorForm";
	}

	@RequestMapping("/contractors/delete/{nip}")
	public String deleteContractor(@PathVariable String nip){
		Optional<Contractor> contractorOptional = repository.findByNip(nip);
		if (contractorOptional.isPresent()) {
			repository.delete(contractorOptional.get());
		}
		return "redirect:/contractors";
	}
}
