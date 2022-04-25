package com.filipiakp.warehousespring.entities.dto;

import com.filipiakp.warehousespring.entities.Contractor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryDTO {
  private long id;
  private Date creationDate;
  private Date finishDate;
  private Contractor contractor;
  private double summaryValue;
}
