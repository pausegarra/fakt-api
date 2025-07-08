package es.pausegarra.fakt.customers.application.services.update_customer;

import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.customers.domain.exception.CustomerNotFound;
import es.pausegarra.fakt.customers.domain.exception.NifOrEmailAlreadyExists;
import es.pausegarra.fakt.customers.domain.repositories.CustomersRepository;
import es.pausegarra.fakt.mother.CustomerMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerServiceTest {

  @Mock
  private CustomersRepository repository;

  @InjectMocks
  private UpdateCustomerService service;

  @Test
  public void shouldReturnCustomer() {
    CustomerEntity entity = CustomerMother.random().build();

    when(repository.getById(entity.getId())).thenReturn(Optional.of(entity));
    when(repository.findByNifOrEmailWhereIdNe(anyString(), anyString(), any(UUID.class))).thenReturn(Optional.empty());
    when(repository.save(any(CustomerEntity.class))).thenReturn(entity);

    UpdateCustomerDto dto = UpdateCustomerDto.from(
      entity.getId().toString(),
      "name",
      "contactName",
      "email",
      "country",
      "nif",
      "address",
      "postcode",
      "city",
      "county",
      null
    );
    CustomerDto result = service.handle(dto);

    assertNotNull(result);
    assertEquals(entity.getName(), result.name());

    verify(repository).getById(entity.getId());
    verify(repository).save(any(CustomerEntity.class));
  }

  @Test
  public void shouldThrowExceptionIfNotFound() {
    when(repository.getById(any(UUID.class))).thenReturn(Optional.empty());

    UpdateCustomerDto dto = UpdateCustomerDto.from(
      UUID.randomUUID().toString(),
      "name",
      "contactName",
      "email",
      "country",
      "nif",
      "address",
      "postcode",
      "city",
      "county",
      null
    );
    assertThrows(CustomerNotFound.class, () -> service.handle(dto));
  }

  @Test
  public void shouldThrowExceptionIfINewEmailOrNifAlreadyExists() {
    CustomerEntity entity = CustomerMother.random().build();

    when(repository.getById(entity.getId())).thenReturn(Optional.of(entity));
    when(repository.findByNifOrEmailWhereIdNe(anyString(), anyString(), any(UUID.class))).thenReturn(Optional.of(entity));

    UpdateCustomerDto dto = UpdateCustomerDto.from(
      entity.getId().toString(),
      "name",
      "contactName",
      "email",
      "country",
      "nif",
      "address",
      "postcode",
      "city",
      "county",
      null
    );
    assertThrows(NifOrEmailAlreadyExists.class, () -> service.handle(dto));
  }

}