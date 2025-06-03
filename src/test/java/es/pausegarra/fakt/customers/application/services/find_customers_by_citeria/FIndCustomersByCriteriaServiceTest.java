package es.pausegarra.fakt.customers.application.services.find_customers_by_citeria;

import es.pausegarra.fakt.common.application.pagination.PaginatedDto;
import es.pausegarra.fakt.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.domain.criterias.CustomerSearchCriteria;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.customers.domain.repositories.CustomersRepository;
import es.pausegarra.fakt.mother.CustomerMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FIndCustomersByCriteriaServiceTest {

  @Mock
  CustomersRepository repository;

  @InjectMocks
  FIndCustomersByCriteriaService service;

  @Test
  public void shouldReturnPaginatedCustomers() {
    CustomerEntity entity = CustomerMother.random().build();
    Paginated<CustomerEntity> response = new Paginated<>(List.of(entity), 0, 10, 1, 1, true, true);
    when(repository.findByCriteria(any(CustomerSearchCriteria.class))).thenReturn(response);

    FIndCustomersByCriteriaDto dto = FIndCustomersByCriteriaDto.create(0, 10, "name", "ASC", null);
    PaginatedDto<CustomerDto> result = service.handle(dto);

    assertEquals(1, result.data().size());
    assertEquals(entity.getName(), result.data().getFirst().name());

    verify(repository).findByCriteria(any(CustomerSearchCriteria.class));
  }

}