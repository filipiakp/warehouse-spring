package com.filipiakp.warehousespring.entities.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO {
  private long id;
  private String productCode;
  private String productName;
  private double productPrice;

  @NotNull(message = "Podaj ilosc")
  @DecimalMin(value = "0", message = "Niepoprawna ilosc. Podaj liczbe naturalna")
  private int quantity;

  private boolean deleted;
}
