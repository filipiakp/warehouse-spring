package com.filipiakp.warehousespring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="warehouse_employee")
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
	@Column(name = "house_number")
	private String houseNumber;
	@Column(name = "apartment_number")
	private String apartmentNumber;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd.MM.yyyy")
	@Column(name = "employment_date")
	private Date employmentDate;
}
