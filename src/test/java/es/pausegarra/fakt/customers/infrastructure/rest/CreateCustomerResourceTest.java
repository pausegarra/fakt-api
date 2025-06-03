package es.pausegarra.fakt.customers.infrastructure.rest;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.application.services.create_customer.CreateCustomerDto;
import es.pausegarra.fakt.customers.infrastructure.requests.CreateCustomerRequest;
import es.pausegarra.fakt.mother.CustomerMother;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCustomerResourceTest {

  @Mock
  private Service<CreateCustomerDto, CustomerDto> service;

  @InjectMocks
  private CreateCustomerResource resource;

  @Test
  public void shouldReturnCustomer() {
    CustomerDto dto = CustomerDto.fromEntity(CustomerMother.random().build());

    when(service.handle(any(CreateCustomerDto.class))).thenReturn(dto);

    CreateCustomerRequest request = new CreateCustomerRequest(
      "name",
      "email",
      "country",
      "nif",
      "address",
      "postcode",
      "city",
      "county"
    );
    RestResponse<CustomerDto> result = resource.createClient(request);

    assertNotNull(result);
    assertEquals(201, result.getStatus());
  }

}