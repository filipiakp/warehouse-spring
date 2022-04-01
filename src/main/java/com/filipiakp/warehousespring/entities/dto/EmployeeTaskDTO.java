package com.filipiakp.warehousespring.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTaskDTO {
	private String id;
	private String employeeId;
	private String employeeName;
	private String employeeSurname;
	private boolean deleted;
}
