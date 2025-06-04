package es.pausegarra.fakt.customers.application.services.find_customer_by_id;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.customers.domain.exception.CustomerNotFound;
import es.pausegarra.fakt.customers.domain.repositories.CustomersRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class FindCustomerByIdService implements Service<FindCustomerByIdDto, CustomerDto> {

  private final CustomersRepository repository;

  @Override
  public CustomerDto handle(FindCustomerByIdDto dto) {
    CustomerEntity customer = repository.getById(dto.id())
      .orElseThrow(() -> new CustomerNotFound(dto.id().toString()));

    return CustomerDto.fromEntity(customer);
  }

}
