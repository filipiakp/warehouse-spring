package com.filipiakp.warehousespring.entities.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@AllArgsConstructor
public class OrderDTO {
  private long id;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private Date creationDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private Date finishDate;

  private OrderProductDTO[] productsList;

  @NotNull(message = "Musisz podać kontrahenta")
  private String contractor;

  public OrderDTO() {
    creationDate = new Date(System.currentTimeMillis());
  }
}
