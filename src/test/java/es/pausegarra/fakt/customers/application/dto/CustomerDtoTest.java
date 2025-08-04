package es.pausegarra.fakt.customers.application.dto;

import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.mother.CustomerMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDtoTest {

  @Test
  public void shouldMapFromEntity() {
    CustomerEntity entity = CustomerMother.random().build();

    CustomerDto dto = CustomerDto.fromEntity(entity);

    assertEquals(entity.getId(), dto.id());
    assertEquals(entity.getName(), dto.name());
    assertEquals(entity.getContactName(), dto.contactName());
    assertEquals(entity.getEmail(), dto.email());
    assertEquals(entity.getCountry(), dto.country());
    assertEquals(entity.getNif(), dto.nif());
    assertEquals(entity.getAddress(), dto.address());
    assertEquals(entity.getPostcode(), dto.postcode());
    assertEquals(entity.getCity(), dto.city());
    assertEquals(entity.getCounty(), dto.county());
    assertEquals(entity.getAudit().getCreatedAt(), dto.createdAt());
    assertEquals(entity.getAudit().getUpdatedAt(), dto.updatedAt());
  }

  @Test
  public void shouldMapToEntity() {
    CustomerEntity entity = CustomerMother.random().build();
    CustomerDto dto = CustomerDto.fromEntity(entity);

    CustomerEntity result = dto.toEntity();

    assertEquals(dto.id(), result.getId());
    assertEquals(dto.name(), result.getName());
    assertEquals(dto.contactName(), result.getContactName());
    assertEquals(dto.email(), result.getEmail());
    assertEquals(dto.country(), result.getCountry());
    assertEquals(dto.nif(), result.getNif());
    assertEquals(dto.address(), result.getAddress());
    assertEquals(dto.postcode(), result.getPostcode());
    assertEquals(dto.city(), result.getCity());
    assertEquals(dto.country(), result.getCountry());
    assertEquals(dto.createdAt(), result.getAudit().getCreatedAt());
    assertEquals(dto.updatedAt(), result.getAudit().getUpdatedAt());
  }

}