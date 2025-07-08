package es.pausegarra.fakt.customers.infrastructure.rest;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.application.services.find_customer_by_id.FindCustomerByIdDto;
import es.pausegarra.fakt.mother.CustomerMother;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindCustomerByIdResourceTest {

  @Mock
  private Service<FindCustomerByIdDto, CustomerDto> service;

  @InjectMocks
  private FindCustomerByIdResource resource;

  @Test
  public void shouldReturnCustomer() {
    CustomerDto customerDto = CustomerDto.fromEntity(CustomerMother.random().build());

    when(service.handle(any())).thenReturn(customerDto);

    RestResponse<CustomerDto> response = resource.findCustomerById(UUID.randomUUID().toString());

    assertNotNull(response);
    assertEquals(200, response.getStatus());
    assertEquals(customerDto, response.getEntity());
  }

}