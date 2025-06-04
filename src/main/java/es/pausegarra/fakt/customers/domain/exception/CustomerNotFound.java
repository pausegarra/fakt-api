package es.pausegarra.fakt.customers.domain.exception;

import es.pausegarra.fakt.common.domain.exception.NotFound;

public class CustomerNotFound extends NotFound {

  public CustomerNotFound(String id) {
    super("Customer with id " + id + " not found");
  }

}
