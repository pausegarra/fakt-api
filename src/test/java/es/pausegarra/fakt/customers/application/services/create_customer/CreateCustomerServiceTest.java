package es.pausegarra.fakt.customers.application.services.create_customer;

import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.customers.domain.exception.NifOrEmailAlreadyExists;
import es.pausegarra.fakt.customers.domain.repositories.CustomersRepository;
import es.pausegarra.fakt.mother.CustomerMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCustomerServiceTest {

  @Mock
  private CustomersRepository repository;

  @InjectMocks
  private CreateCustomerService service;

  @Test
  public void shouldCreateCustomer() {
    CustomerEntity entity = CustomerMother.random().build();
    when(repository.save(any(CustomerEntity.class))).thenReturn(entity);

    CreateCustomerDto dto = CreateCustomerDto.create(
      entity.getName(),
      entity.getContactName(),
      entity.getEmail(),
      entity.getCountry(),
      entity.getNif(),
      entity.getAddress(),
      entity.getPostcode(),
      entity.getCity(),
      entity.getCounty()
    );
    CustomerDto result = service.handle(dto);

    assertNotNull(result);

    verify(repository).save(any(CustomerEntity.class));
  }

  @Test
  public void shouldThrowExceptionIfCustomerAlreadyExists() {
    CustomerEntity entity = CustomerMother.random().build();
    when(repository.findByNifOrEmail(anyString(), anyString())).thenReturn(Optional.of(entity));

    CreateCustomerDto dto = CreateCustomerDto.create(
      entity.getName(),
      entity.getContactName(),
      entity.getEmail(),
      entity.getCountry(),
      entity.getNif(),
      entity.getAddress(),
      entity.getPostcode(),
      entity.getCity(),
      entity.getCounty()
    );

    assertThrows(NifOrEmailAlreadyExists.class, () -> service.handle(dto));
  }

}