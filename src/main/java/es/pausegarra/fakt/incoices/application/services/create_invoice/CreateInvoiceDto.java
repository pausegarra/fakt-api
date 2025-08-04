package es.pausegarra.fakt.incoices.application.services.create_invoice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CreateInvoiceDto(
  @NotBlank String invoiceNumber,
  @NotBlank UUID customerId,
  @NotNull LocalDate date,
  @NotNull List<CreateInvoiceLineDto> lines
) {

  public static CreateInvoiceDto from(
    String invoiceNumber,
    String customerId,
    LocalDate date,
    List<CreateInvoiceLineDto> lines
  ) {
    return new CreateInvoiceDto(invoiceNumber, UUID.fromString(customerId), date, lines);
  }

}
