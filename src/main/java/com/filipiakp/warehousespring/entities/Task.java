package com.filipiakp.warehousespring.entities;

import java.util.*;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(name = "warehouse_task")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Date creationDate;
  private Date finishDate;
  private String name;
  private String description;
  private String phase;
  private int importance;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "employee_id")
  private Set<Employee> employees;

  @ManyToOne
  @JoinColumn(name = "contractor_nip")
  private Contractor contractor;

  public Task() {
    employees = new HashSet<>();
    creationDate = new Date();
  }
}
