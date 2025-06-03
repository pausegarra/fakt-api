package es.pausegarra.fakt.customers.application.services.create_customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateCustomerDto(
  @NotBlank String name,
  @NotBlank@Email String email,
  @NotBlank String country,
  @NotBlank String nif,
  @NotBlank String address,
  @NotBlank String postcode,
  @NotBlank String city,
  @NotBlank String county
) {

  public static CreateCustomerDto create(
    String name,
    String email,
    String country,
    String nif,
    String address,
    String postcode,
    String city,
    String county
  ) {
    return new CreateCustomerDto(name, email, country, nif, address, postcode, city, county);
  }

}
