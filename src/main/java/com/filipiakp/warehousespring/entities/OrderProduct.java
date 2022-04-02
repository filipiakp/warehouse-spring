package com.filipiakp.warehousespring.entities;

import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "warehouse_orderproduct")
public class OrderProduct {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToOne
  @JoinColumn(name = "code")
  private Product product;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "order_id")
  private List<Discount> appliedDiscounts;

  private int quantity;

  @Override
  public String toString() {
    return "OrderProduct id: "
        + id
        + ", product code: "
        + product.getCode()
        + ", quantity: "
        + quantity
        + ";";
  }
}
