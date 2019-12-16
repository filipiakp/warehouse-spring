package com.filipiakp.warehousespring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="warehouse_orderproduct")
public class OrderProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
//	@ManyToOne(optional = false, fetch = FetchType.LAZY)
//	@JoinColumn(name = "order_id")
//	private Order order;
	@ManyToOne
	@JoinColumn(name = "code")
	private Product product;
	private int quantity;

	@Override
	public String toString(){
		return "OrderProduct id: " + id + ", order id: " +/* order.getId() +*/ ", product code: " + product.getCode() + ", quantity: " + quantity + ";";
	}

}
