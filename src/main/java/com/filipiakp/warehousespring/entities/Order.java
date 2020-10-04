package com.filipiakp.warehousespring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
@Table(name="warehouse_order")
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	@Temporal(TemporalType.DATE)
	@Column(name="order_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date date;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "order_id")
	private Set<OrderProduct> productsList;
	@ManyToOne
	@JoinColumn(name="nip")
	@NotNull(message = "Musisz podać kontrahenta")
	private Contractor contractor;

	public Order(){
		id = 0;
		date = new Date();
		productsList = new HashSet<>();
		contractor = null;
	}

	public void setProductsList(Set<OrderProduct> orderProducts) {
		productsList.addAll(orderProducts);
	}

	@Override
	public String toString(){
		return "Order id: " + id + ", date: " + date.toString() + ", contractor: " + contractor==null?"null":contractor.getNip() + ";";
	}
}
