package es.pausegarra.fakt.mother;

import es.pausegarra.fakt.common.domain.audit.AuditFields;
import es.pausegarra.fakt.incoices.domain.entities.InvoiceLineEntity;

import java.math.BigDecimal;
import java.util.UUID;

public class InvoiceLineMother {

  public static InvoiceLineEntity.InvoiceLineEntityBuilder random() {
    return InvoiceLineEntity.builder()
      .id(UUID.randomUUID())
      .description(MotherCreator.random().name().username())
      .quantity(MotherCreator.random().number().randomDouble(2, 0, 10))
      .unitPrice(BigDecimal.valueOf(MotherCreator.random().number().randomDouble(2, 0, 10)))
      .total(BigDecimal.valueOf(MotherCreator.random().number().randomDouble(2, 0, 10)))
      .audit(new AuditFields());
  }

}
