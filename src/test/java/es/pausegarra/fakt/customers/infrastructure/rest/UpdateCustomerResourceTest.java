package es.pausegarra.fakt.customers.infrastructure.rest;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.application.services.update_customer.UpdateCustomerDto;
import es.pausegarra.fakt.customers.infrastructure.requests.UpdateCustomerRequest;
import es.pausegarra.fakt.mother.CustomerMother;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerResourceTest {

  @Mock
  private Service<UpdateCustomerDto, CustomerDto> service;

  @InjectMocks
  private UpdateCustomerResource resource;

  @Test
  public void shouldReturnCustomer() {
    CustomerDto customerDto = CustomerDto.fromEntity(CustomerMother.random().build());

    when(service.handle(any())).thenReturn(customerDto);

    UpdateCustomerRequest to = new UpdateCustomerRequest(
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
    RestResponse<CustomerDto> response = resource.updateCustomer(customerDto.id().toString(), to);

    assertNotNull(response);
    assertEquals(200, response.getStatus());

    verify(service).handle(any());
  }

}