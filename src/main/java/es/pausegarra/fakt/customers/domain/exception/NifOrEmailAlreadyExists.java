package es.pausegarra.fakt.customers.domain.exception;

import es.pausegarra.fakt.common.domain.exception.BadRequest;

public class NifOrEmailAlreadyExists extends BadRequest {

  public NifOrEmailAlreadyExists(String nif, String email) {
    super("Customer with nif " + nif + " or email " + email + " already exists", "NIF_OR_EMAIL_ALREADY_EXISTS");
  }

}
