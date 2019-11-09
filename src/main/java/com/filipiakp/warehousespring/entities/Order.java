package com.filipiakp.warehousespring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="warehouse_order")
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	@Temporal(TemporalType.DATE)
	@Column(name="order_date")
	private Date date;
	@ManyToMany()//fetch= FetchType.EAGER
	@Column(name="products_list")
	private Set<Product> productsList;
	@ManyToOne
	@JoinColumn(name="nip")
	private Contractor contractor;
}
