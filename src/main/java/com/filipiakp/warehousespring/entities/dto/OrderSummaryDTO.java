package com.filipiakp.warehousespring.entities.dto;

import com.filipiakp.warehousespring.entities.Contractor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
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
