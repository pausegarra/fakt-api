package es.pausegarra.fakt.customers.application.services.delete_customer;

import es.pausegarra.fakt.customers.domain.repositories.CustomersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerServiceTest {

  @Mock
  private CustomersRepository repository;

  @InjectMocks
  private DeleteCustomerService service;

  @Test
  public void shouldReturnVoid() {
    DeleteCustomerDto dto = DeleteCustomerDto.from(UUID.randomUUID().toString());

    assertNull(service.handle(dto));

    verify(repository).delete(any(UUID.class));
  }

}