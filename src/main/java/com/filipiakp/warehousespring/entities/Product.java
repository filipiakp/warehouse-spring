package com.filipiakp.warehousespring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "warehouse_product")
public class Product {

  @Id private String code;

  @Pattern(regexp = "[\\w-ŻŹĆĘŚĄÓŁŃżźćńąśłęó ?]{3,}", message = "Niepoprawny producent")
  private String manufacturer;

  @Pattern(regexp = "[\\w-ŻŹĆĘŚĄÓŁŃżźćńąśłęó ?]{3,}", message = "Niepoprawna nazwa")
  private String name;

  private String specification;

  @DecimalMin(value = "0", message = "Niepoprawna ilosc. Podaj liczbe naturalna")
  private long amount;

  @DecimalMin(value = "0.0", message = "Niepoprawna cena. Podaj dodatnią liczbę rzeczywistą")
  private double price;

  @Pattern(
      regexp = "[A-ZŻŹĆĘŚĄÓŁŃ]+[a-zżźćńąśłęó]*( [A-ZŻŹĆĘŚĄÓŁŃ]+[a-zżźćńąśłęó]*){0,}",
      message = "Niepoprawna nazwa dzialu. Użyj Dużych Liter")
  private String category;

  @DecimalMin(value = "0.0", message = "Niepoprawna waga")
  @Digits(integer = 6, fraction = 3, message = "Niepoprawna precyzja wagi")
  private double weight;
}
