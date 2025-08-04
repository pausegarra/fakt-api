package es.pausegarra.fakt.incoices.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record InvoiceLineDto(
  UUID id,
  String description,
  Double quantity,
  BigDecimal unitPrice,
  BigDecimal total
) {}
