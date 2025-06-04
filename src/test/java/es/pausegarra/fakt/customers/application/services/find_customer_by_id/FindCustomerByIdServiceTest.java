package es.pausegarra.fakt.customers.application.services.find_customer_by_id;

import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.customers.domain.exception.CustomerNotFound;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindCustomerByIdServiceTest {

  @Mock
  CustomersRepository repository;

  @InjectMocks
  FindCustomerByIdService service;

  @Test
  public void shouldReturnCustomer() {
    CustomerEntity entity = CustomerMother.random().build();

    when(repository.getById(entity.getId())).thenReturn(Optional.of(entity));

    CustomerDto result = service.handle(FindCustomerByIdDto.from(entity.getId().toString()));

    assertNotNull(result);
    assertEquals(entity.getName(), result.name());

    verify(repository).getById(entity.getId());
  }

  @Test
  public void shouldThrowExceptionIfNotFound() {
    when(repository.getById(any(UUID.class))).thenReturn(Optional.empty());

    FindCustomerByIdDto dto = FindCustomerByIdDto.from(UUID.randomUUID().toString());
    assertThrows(CustomerNotFound.class, () -> service.handle(dto));
  }

}