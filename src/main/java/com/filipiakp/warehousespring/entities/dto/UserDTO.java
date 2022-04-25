package com.filipiakp.warehousespring.entities.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
  @NotBlank(message = "Nazwa użytkownika nie może być pusta")
  @Pattern(
      regexp = "^[A-Za-z0-9]+$",
      message =
          "Nazwa użytkownika może składać się z dużych i małych liter angielskiego alfabetu oraz cyfr")
  private String username;

  @Pattern(
      regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}",
      message =
          "Hasło musi zawierać minimum: jedną dużą literę, jedną małą, cyfrę, znak specjalny oraz być długości 8-20 znaków")
  private String newPassword;

  private String newPasswordRepeated;
}
