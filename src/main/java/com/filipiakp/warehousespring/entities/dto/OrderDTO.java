package com.filipiakp.warehousespring.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	private long id;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date date;
	private Set<OrderProductDTO> productsList;
	private String contractor;
}
