package com.filipiakp.warehousespring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	private String code;
	private String manufacturer;
	private String name;
	private String specification;
	private long amount;
	private double price;
	private String category;
	private double weight;
}
