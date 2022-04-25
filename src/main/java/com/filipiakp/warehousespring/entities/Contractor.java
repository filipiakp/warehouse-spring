package com.filipiakp.warehousespring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "warehouse_contractor")
public class Contractor {

  @Id
  @Digits(integer = 10, fraction = 0, message = "NIP sklada sie z 10 cyfr")
  @NotBlank(message = "NIP nie moze byc pusty")
  private String nip;

  @Size(min = 2, max = 255, message = "Zla dlugosc nazwy")
  @NotBlank(message = "Nazwa nie moze byc pusta")
  private String name;

  @Column(name = "phone_number")
  @Pattern(regexp = "[0-9]{9}", message = "Niepoprawny numer telefonu- 9 cyfr")
  private String phoneNumber;

  @Pattern(regexp = "([A-ZŻŹĆĘŚĄÓŁŃ][a-zżźćńąśłęó]{2,} ?)+", message = "Niepoprawne miasto")
  private String city;

  @Pattern(
      regexp =
          "((al\\. )|(ul\\. ))?[A-ZŻŹĆĘŚĄÓŁŃ][a-zżźćńąśłęó]+([- ][A-ZŻŹĆĘŚĄÓŁŃ0-9][a-zżźćńąśłęó0-9]*){0,5}",
      message = "Niepoprawna ulica lub aleja")
  private String street;

  @Column(name = "house_number")
  @Pattern(regexp = "[1-9][0-9]{0,4}[A-Z]?[A-Z]?", message = "Niepoprawny numer domu")
  private String houseNumber;

  @Column(name = "apartment_number")
  @Pattern(regexp = "([1-9][0-9]{0,4}[A-Z]?[A-Z]?)|", message = "Niepoprawny numer mieszkania")
  private String apartmentNumber;

  @Column(name = "is_supplier")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private boolean isSupplier;

  private int importance;
}
