package es.pausegarra.fakt.customers.infrastructure.rest;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.common.application.pagination.PaginatedDto;
import es.pausegarra.fakt.common.infrastructure.presentations.PaginatedPresentation;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.application.services.find_customers_by_citeria.FIndCustomersByCriteriaDto;
import es.pausegarra.fakt.customers.infrastructure.spec.FindCustomersByCriteriaApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class FindCustomersByCriteriaResource implements FindCustomersByCriteriaApiSpec {

  private final Service<FIndCustomersByCriteriaDto, PaginatedDto<CustomerDto>> service;

  @Override
  @RolesAllowed("customers#read")
  public RestResponse<PaginatedPresentation<CustomerDto>> findCustomersByCriteria(
    int page,
    int pageSize,
    String sortBy,
    String sortDirection,
    String search
  ) {
    FIndCustomersByCriteriaDto dto = FIndCustomersByCriteriaDto.create(page, pageSize, sortBy, sortDirection, search);
    PaginatedDto<CustomerDto> response = service.handle(dto);

    return RestResponse.ok(
      new PaginatedPresentation<>(
        response.data(),
        response.page(),
        response.pageSize(),
        response.totalPages(),
        response.totalElements(),
        response.hasNextPage(),
        response.hasPreviousPage()
      )
    );
  }

}
