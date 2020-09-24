package com.filipiakp.warehousespring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="warehouse_product")
public class Product {

	@Id
	private String code;
	@Pattern(regexp = "[\\w- ?]{3,}", message = "Niepoprawny producent")
	private String manufacturer;
	@Pattern(regexp = "[\\w- ?]{3,}",message = "Niepoprawna nazwa")
	private String name;
	private String specification;
	@Pattern(regexp = "[0-9]{1,7}",message = "Niepoprawna ilosc. Podaj liczbe calkowita")
	private long amount;
	@Pattern(regexp = "[0-9]{1,7}(\\.[0-9]{1,2})?",message = "Niepoprawna cena")
	private double price;
	@Pattern(regexp = "[A-ZŻŹĆĘŚĄÓŁŃ][a-zżźćńąśłęó]+( [A-ZŻŹĆĘŚĄÓŁŃ][a-zżźćńąśłęó]+){0,}",message = "Niepoprawna nazwa dzialu. Użyj Dużych Liter")
	private String category;
	@Pattern(regexp = "[0-9]{1,7}(\\.[0-9]{1,3})?",message = "Niepoprawna waga")
	private double weight;
}
