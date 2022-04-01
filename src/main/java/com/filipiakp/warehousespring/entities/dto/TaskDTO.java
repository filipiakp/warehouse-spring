package com.filipiakp.warehousespring.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
	private long id;
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
	private Date creationDate;
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
	private Date finishDate;
	private String name;
	private String description;
	private int importance;
	private int[] employeesList = new int[0];
	@NotNull(message = "Musisz podać kontrahenta")
	private String contractor;
}
