package es.pausegarra.fakt.mother;

import es.pausegarra.fakt.common.domain.audit.AuditFields;
import es.pausegarra.fakt.incoices.domain.entities.InvoiceEntity;

import java.time.LocalDate;
import java.util.UUID;

public class InvoiceMother {

  public static InvoiceEntity.InvoiceEntityBuilder random() {
    return InvoiceEntity.builder()
      .id(UUID.randomUUID())
      .invoiceNumber(MotherCreator.random().name().username())
      .customer(CustomerMother.random().build())
      .date(LocalDate.now())
      .audit(new AuditFields());
  }

}
