package es.pausegarra.fakt.mother;

import es.pausegarra.fakt.common.domain.audit.AuditFields;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;

import java.time.Instant;
import java.util.UUID;

public class CustomerMother {

  public static CustomerEntity.CustomerEntityBuilder random() {
    return CustomerEntity.builder()
      .id(UUID.randomUUID())
      .name(MotherCreator.random().name().toString())
      .email(MotherCreator.random().name().toString())
      .country(MotherCreator.random().country().toString())
      .nif(MotherCreator.random().number().toString())
      .address(MotherCreator.random().address().toString())
      .postcode(MotherCreator.random().number().toString())
      .city(MotherCreator.random().address().toString())
      .county(MotherCreator.random().address().toString())
      .audit(new AuditFields(
        Instant.now(),
        Instant.now()
      ));
  }

}
