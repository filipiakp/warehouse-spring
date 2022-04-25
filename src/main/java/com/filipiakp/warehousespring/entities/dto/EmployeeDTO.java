package com.filipiakp.warehousespring.entities.dto;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
  private int id;

  @Size(min = 2, max = 40, message = "Zła długość imienia")
  @NotBlank(message = "Pole nie może być puste")
  private String name;

  @Size(min = 2, max = 40, message = "Zła długość nazwiska")
  @NotBlank(message = "Pole nie może być puste")
  private String surname;

  @NotBlank(message = "Pole nie może być puste")
  private String position;

  @DecimalMin(value = "0", message = "Pensja musi być dodatnia")
  @Digits(integer = 6, fraction = 2, message = "Podaj poprawną pensję")
  private double salary;

  @NotBlank(message = "Pole nie może być puste")
  private String city;

  @Pattern(
      regexp =
          "((al\\. )|(ul\\. ))?[A-ZŻŹĆĘŚĄÓŁŃ][a-zżźćńąśłęó]+([- ][A-ZŻŹĆĘŚĄÓŁŃ0-9][a-zżźćńąśłęó0-9]*){0,5}",
      message = "Niepoprawna ulica lub aleja")
  private String street;

  @Pattern(regexp = "[1-9][0-9]{0,4}[A-Z]?[A-Z]?", message = "Niepoprawny numer domu")
  private String houseNumber;

  @Pattern(regexp = "([1-9][0-9]{0,4}[A-Z]?[A-Z]?)|", message = "Niepoprawny numer mieszkania")
  private String apartmentNumber;

  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date employmentDate;

  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date birthDate;
}
