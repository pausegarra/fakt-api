package es.pausegarra.fakt.incoices.application.dto;

import es.pausegarra.fakt.customers.application.dto.CustomerDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record InvoiceDto(
  UUID id,
  String invoiceNumber,
  CustomerDto customer,
  LocalDate date,
  List<InvoiceLineDto> lines
) {}
