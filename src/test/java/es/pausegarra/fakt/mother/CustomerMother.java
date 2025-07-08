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
      .contactName(MotherCreator.random().name().toString())
      .email(MotherCreator.random().internet().emailAddress())
      .country(MotherCreator.random().country().toString())
      .nif(String.valueOf(MotherCreator.random().number().numberBetween(10000000, 99999999)))
      .address(MotherCreator.random().address().toString())
      .postcode(MotherCreator.random().number().toString())
      .city(MotherCreator.random().address().toString())
      .county(MotherCreator.random().address().toString())
      .emailExtraRecipients(MotherCreator.random().internet().emailAddress())
      .audit(new AuditFields(
        Instant.now(),
        Instant.now()
      ));
  }

}
