package es.pausegarra.fakt.customers.infrastructure.rest;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.customers.application.services.delete_customer.DeleteCustomerDto;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerResourceTest {

  @Mock
  private Service<DeleteCustomerDto, Void> service;

  @InjectMocks
  private DeleteCustomerResource resource;

  @Test
  public void shouldReturnVoid() {
    DeleteCustomerDto dto = DeleteCustomerDto.from(UUID.randomUUID().toString());

    RestResponse<Void> response = resource.deleteCustomer(dto.id().toString());

    assertEquals(204, response.getStatus());

    verify(service).handle(dto);
  }

}