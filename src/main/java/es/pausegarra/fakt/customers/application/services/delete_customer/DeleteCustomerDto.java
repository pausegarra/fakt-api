package es.pausegarra.fakt.customers.application.services.delete_customer;

import java.util.UUID;

public record DeleteCustomerDto(
  UUID id
) {

  public static DeleteCustomerDto from(String id) {
    return new DeleteCustomerDto(UUID.fromString(id));
  }

}
