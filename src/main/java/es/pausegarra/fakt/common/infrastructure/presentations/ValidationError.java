package es.pausegarra.fakt.common.infrastructure.presentations;

import jakarta.validation.ConstraintViolation;

public record ValidationError(
  String field, String message
) {

  public static ValidationError fromConstraint(ConstraintViolation<?> violation) {
    return new ValidationError(
      violation.getPropertyPath()
        .toString(), violation.getMessage()
    );
  }

}
