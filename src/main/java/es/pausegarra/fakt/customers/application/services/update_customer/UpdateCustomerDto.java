package es.pausegarra.fakt.customers.application.services.update_customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateCustomerDto(
  @NotNull UUID id,
  @NotBlank String name,
  @NotBlank String contactName,
  @NotBlank @Email String email,
  @NotBlank String country,
  @NotBlank String nif,
  @NotBlank String address,
  @NotBlank String postcode,
  @NotBlank String city,
  @NotBlank String county,
  String emailExtraRecipients
) {

  public static UpdateCustomerDto from(
    String id,
    String name,
    String contactName,
    String email,
    String country,
    String nif,
    String address,
    String postcode,
    String city,
    String county,
    String emailExtraRecipients
  ) {
    return new UpdateCustomerDto(UUID.fromString(id), name, contactName, email, country, nif, address, postcode, city, county, emailExtraRecipients);
  }

}
