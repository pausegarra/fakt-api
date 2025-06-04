package es.pausegarra.fakt.customers.infrastructure.rest;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.application.services.find_customer_by_id.FindCustomerByIdDto;
import es.pausegarra.fakt.customers.infrastructure.spec.FindCustomerByIdApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class FindCustomerByIdResource implements FindCustomerByIdApiSpec {

  private final Service<FindCustomerByIdDto, CustomerDto> service;

  @Override
  @RolesAllowed("customers#read")
  public RestResponse<CustomerDto> findCustomerById(String id) {
    CustomerDto customerDto = service.handle(FindCustomerByIdDto.from(id));

    return RestResponse.ok(customerDto);
  }

}
