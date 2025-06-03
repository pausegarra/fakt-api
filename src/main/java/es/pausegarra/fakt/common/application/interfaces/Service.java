package es.pausegarra.fakt.common.application.interfaces;

import jakarta.validation.Valid;

public interface Service<T, R> {

  R handle(
    @Valid
    T dto
  );

}
