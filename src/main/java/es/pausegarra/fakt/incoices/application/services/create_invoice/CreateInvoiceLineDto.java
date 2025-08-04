package es.pausegarra.fakt.incoices.application.services.create_invoice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateInvoiceLineDto(
  @NotBlank String description,
  @PositiveOrZero @NotNull Double quantity,
  @PositiveOrZero @NotNull BigDecimal unitPrice,
  @PositiveOrZero @NotNull BigDecimal total
) {

  public static CreateInvoiceLineDto from(
    String description,
    Double quantity,
    BigDecimal unitPrice,
    BigDecimal total
  ) {
    return new CreateInvoiceLineDto(description, quantity, unitPrice, total);
  }

}
