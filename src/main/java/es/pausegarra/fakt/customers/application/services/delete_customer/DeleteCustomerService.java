package es.pausegarra.fakt.customers.application.services.delete_customer;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.customers.domain.repositories.CustomersRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class DeleteCustomerService implements Service<DeleteCustomerDto, Void> {

  private final CustomersRepository repository;

  @Override
  @Transactional
  public Void handle(DeleteCustomerDto dto) {
    repository.delete(dto.id());

    return null;
  }

}
