package es.pausegarra.fakt.customers.application.services.find_customers_by_citeria;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.common.application.pagination.PaginatedDto;
import es.pausegarra.fakt.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.domain.criterias.CustomerSearchCriteria;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.customers.domain.repositories.CustomersRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class FIndCustomersByCriteriaService implements Service<FIndCustomersByCriteriaDto, PaginatedDto<CustomerDto>> {

  private final CustomersRepository repository;

  @Override
  public PaginatedDto<CustomerDto> handle(FIndCustomersByCriteriaDto dto) {
    CustomerSearchCriteria criteria = CustomerSearchCriteria.create(
      dto.page(),
      dto.pageSize(),
      dto.sortBy(),
      dto.sortDirection(),
      dto.search()
    );
    Paginated<CustomerEntity> customers = repository.findByCriteria(criteria);

    List<CustomerDto> data = customers.data()
      .stream()
      .map(CustomerDto::fromEntity)
      .toList();

    return new PaginatedDto<>(
      data,
      customers.page(),
      customers.pageSize(),
      customers.totalPages(),
      customers.totalElements(),
      customers.hasNextPage(),
      customers.hasPreviousPage()
    );
  }

}
