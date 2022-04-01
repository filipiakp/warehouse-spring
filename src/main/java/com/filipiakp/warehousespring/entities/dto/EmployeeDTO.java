package com.filipiakp.warehousespring.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private int id;
    @Size(min=2, max=40, message = "Zla dlugosc imienia")
    @NotBlank(message = "Pole nie może być puste")
    private String name;
    @Size(min=2, max=40, message = "Zla dlugosc nazwiska")
    @NotBlank(message = "Pole nie może być puste")
    private String surname;
}
