package com.filipiakp.warehousespring.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class OrderDTO {
	private long id;
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
	private Date creationDate;
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
	private Date finishDate;
	private OrderProductDTO[] productsList;
	@NotNull(message = "Musisz podać kontrahenta")
	private String contractor;

	public OrderDTO() {
		creationDate = new Date(System.currentTimeMillis());
	}
}
