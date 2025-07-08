package es.pausegarra.fakt.customers.infrastructure.rest;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.customers.application.services.delete_customer.DeleteCustomerDto;
import es.pausegarra.fakt.customers.infrastructure.spec.DeleteCustomerApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class DeleteCustomerResource implements DeleteCustomerApiSpec {

  private final Service<DeleteCustomerDto, Void> service;

  @Override
  @RolesAllowed("customers#delete")
  public RestResponse<Void> deleteCustomer(String id) {
    DeleteCustomerDto dto = DeleteCustomerDto.from(id);

    service.handle(dto);

    return RestResponse.noContent();
  }

}
