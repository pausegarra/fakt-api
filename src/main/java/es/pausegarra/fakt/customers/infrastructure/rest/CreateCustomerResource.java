package es.pausegarra.fakt.customers.infrastructure.rest;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.application.services.create_customer.CreateCustomerDto;
import es.pausegarra.fakt.customers.infrastructure.requests.CreateCustomerRequest;
import es.pausegarra.fakt.customers.infrastructure.spec.CreateCustomerApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class CreateCustomerResource implements CreateCustomerApiSpec {

  private final Service<CreateCustomerDto, CustomerDto> service;

  @Override
  @RolesAllowed("customers#create")
  public RestResponse<CustomerDto> createClient(CreateCustomerRequest request) {
    CreateCustomerDto dto = CreateCustomerDto.create(
      request.name(),
      request.contactName(),
      request.email(),
      request.country(),
      request.nif(),
      request.address(),
      request.postcode(),
      request.city(),
      request.county(),
      request.emailExtraRecipients()
    );
    CustomerDto response = service.handle(dto);

    return RestResponse.status(RestResponse.Status.CREATED, response);
  }

}
