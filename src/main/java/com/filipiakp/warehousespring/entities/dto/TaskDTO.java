package com.filipiakp.warehousespring.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
	private long id;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date creationDate;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date finishedDate;
	private String name;
	private String description;
	private int importance;
	private EmployeeTaskDTO[] employeesList = new EmployeeTaskDTO[0];
	@NotNull(message = "Musisz podać kontrahenta")
	private String contractor;
}
