package es.pausegarra.fakt.customers.application.services.find_customer_by_id;

import java.util.UUID;

public record FindCustomerByIdDto(
  UUID id
) {

  public static FindCustomerByIdDto from(String id) {
    return new FindCustomerByIdDto(UUID.fromString(id));
  }

}
