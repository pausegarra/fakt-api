package es.pausegarra.fakt.customers.infrastructure.rest;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.common.application.pagination.PaginatedDto;
import es.pausegarra.fakt.common.infrastructure.presentations.PaginatedPresentation;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.application.services.find_customers_by_citeria.FIndCustomersByCriteriaDto;
import es.pausegarra.fakt.mother.CustomerMother;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindCustomersByCriteriaResourceTest {

  @Mock
  private Service<FIndCustomersByCriteriaDto, PaginatedDto<CustomerDto>> service;

  @InjectMocks
  private FindCustomersByCriteriaResource resource;

  @Test
  public void shouldReturnPaginatedCustomers() {
    CustomerDto dto = CustomerDto.fromEntity(CustomerMother.random().build());
    PaginatedDto<CustomerDto> response = new PaginatedDto<>(List.of(dto), 0, 10, 1, 1, true, true);

    when(service.handle(any(FIndCustomersByCriteriaDto.class))).thenReturn(response);

    RestResponse<PaginatedPresentation<CustomerDto>> result = resource.findCustomersByCriteria(0, 10, "name", "ASC", null);

    assertNotNull(result);
    assertEquals(200, result.getStatus());
    assertEquals(1, result.getEntity().data().size());
    assertEquals(dto.name(), result.getEntity().data().getFirst().name());
  }

}