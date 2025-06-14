package es.pausegarra.fakt.customers.application.dto;

import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;

import java.time.Instant;
import java.util.UUID;

public record CustomerDto(
  UUID id,
  String name,
  String contactName,
  String email,
  String country,
  String nif,
  String address,
  String postcode,
  String city,
  String county,
  Instant createdAt,
  Instant updatedAt
) {

  public static CustomerDto fromEntity(CustomerEntity entity) {
    return new CustomerDto(
      entity.getId(),
      entity.getName(),
      entity.getContactName(),
      entity.getEmail(),
      entity.getCountry(),
      entity.getNif(),
      entity.getAddress(),
      entity.getPostcode(),
      entity.getCity(),
      entity.getCounty(),
      entity.getAudit()
        .getCreatedAt(),
      entity.getAudit()
        .getUpdatedAt()
    );
  }

}
