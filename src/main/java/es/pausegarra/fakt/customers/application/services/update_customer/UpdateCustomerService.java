package es.pausegarra.fakt.customers.application.services.update_customer;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.customers.domain.exception.CustomerNotFound;
import es.pausegarra.fakt.customers.domain.exception.NifOrEmailAlreadyExists;
import es.pausegarra.fakt.customers.domain.repositories.CustomersRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class UpdateCustomerService implements Service<UpdateCustomerDto, CustomerDto> {

  private final CustomersRepository repository;

  @Override
  @Transactional
  public CustomerDto handle(@Valid UpdateCustomerDto dto) {
    CustomerEntity entity = findById(dto.id());

    ensureNifOrEmailNotAlreadyExists(dto.nif(), dto.email());

    CustomerEntity toUpdate = entity.update(
      dto.name(),
      dto.contactName(),
      dto.email(),
      dto.country(),
      dto.nif(),
      dto.address(),
      dto.postcode(),
      dto.city(),
      dto.county()
    );

    CustomerEntity updated = repository.save(toUpdate);

    return CustomerDto.fromEntity(updated);
  }

  private CustomerEntity findById(UUID id) {
    return repository.getById(id)
      .orElseThrow(() -> new CustomerNotFound(id.toString()));
  }

  private void ensureNifOrEmailNotAlreadyExists(String nif, String email) {
    Optional<CustomerEntity> found = repository.findByNifOrEmail(nif, email);

    if (found.isPresent()) {
      throw new NifOrEmailAlreadyExists(nif, email);
    }
  }

}
