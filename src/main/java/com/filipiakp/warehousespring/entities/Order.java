package com.filipiakp.warehousespring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
@Table(name = "warehouse_order")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long id;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "creation_date")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private Date creationDate;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "finish_date")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private Date finishDate;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  @JoinColumn(name = "order_id")
  private Set<OrderProduct> productsList;

  @ManyToOne
  @JoinColumn(name = "nip")
  private Contractor contractor;

  public Order() {
    id = 0;
    creationDate = new Date();
    finishDate = null;
    productsList = new HashSet<>();
    contractor = null;
  }

  public void setProductsList(Set<OrderProduct> orderProducts) {
    productsList.addAll(orderProducts);
  }

  @Override
  public String toString() {
    return "Order{"
        + "id="
        + id
        + ", creationDate="
        + creationDate
        + ", finishDate="
        + finishDate
        + ", productsList="
        + productsList
        + ", contractor="
        + contractor
        + '}';
  }
}
