package com.filipiakp.warehousespring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@NotBlank
	private String name;
	@NotBlank
	private String surname;
	@NotBlank
	private String position;
	private double salary;
	@NotBlank
	private String city;
	@NotBlank
	private String street;
	@NotBlank
	private String houseNumber;
	@Column(nullable=true)
	private String apartmentNumber;
	@Temporal(TemporalType.DATE)
	private Date employmentDate;
}
