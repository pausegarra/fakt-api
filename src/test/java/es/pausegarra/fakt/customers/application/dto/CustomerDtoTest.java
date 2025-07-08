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

}