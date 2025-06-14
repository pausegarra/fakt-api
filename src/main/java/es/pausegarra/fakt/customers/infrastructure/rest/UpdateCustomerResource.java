package es.pausegarra.fakt.customers.infrastructure.rest;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.application.services.update_customer.UpdateCustomerDto;
import es.pausegarra.fakt.customers.infrastructure.requests.UpdateCustomerRequest;
import es.pausegarra.fakt.customers.infrastructure.spec.UpdateCustomerApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class UpdateCustomerResource implements UpdateCustomerApiSpec {

  private final Service<UpdateCustomerDto, CustomerDto> service;

  @Override
  @RolesAllowed("customers#edit")
  public RestResponse<CustomerDto> updateCustomer(String id, UpdateCustomerRequest request) {
    UpdateCustomerDto dto = UpdateCustomerDto.from(
      id,
      request.name(),
      request.contactName(),
      request.email(),
      request.country(),
      request.nif(),
      request.address(),
      request.postcode(),
      request.city(),
      request.county()
    );
    CustomerDto customerDto = service.handle(dto);

    return RestResponse.ok(customerDto);
  }

}
