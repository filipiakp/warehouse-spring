package com.filipiakp.warehousespring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "warehouse_contractor")
public class Contractor {

	@Id
	private String nip;
	private String name;
	@Column(name="phone_number")
	private String phoneNumber;
	private String city;
	private String street;
	@Column(name = "house_number")
	private String houseNumber;
	@Column(name = "apartment_number")
	private String apartmentNumber;
	@Column(name="is_supplier")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isSupplier;//dostawcy lub klienci
}
