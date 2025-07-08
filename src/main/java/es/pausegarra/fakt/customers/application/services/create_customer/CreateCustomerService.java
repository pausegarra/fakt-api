package es.pausegarra.fakt.customers.application.services.create_customer;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.customers.domain.exception.NifOrEmailAlreadyExists;
import es.pausegarra.fakt.customers.domain.repositories.CustomersRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class CreateCustomerService implements Service<CreateCustomerDto, CustomerDto> {

  private final CustomersRepository repository;

  @Override
  @Transactional
  public CustomerDto handle(@Valid CreateCustomerDto dto) {
    ensureEmailOrNifDoesNotExist(dto.nif(), dto.email());

    CustomerEntity entity = CustomerEntity.create(
      dto.name(),
      dto.contactName(),
      dto.email(),
      dto.country(),
      dto.nif(),
      dto.address(),
      dto.postcode(),
      dto.city(),
      dto.county(),
      dto.emailExtraRecipients()
    );
    CustomerEntity saved = repository.save(entity);

    return CustomerDto.fromEntity(saved);
  }

  private void ensureEmailOrNifDoesNotExist(String nif, String email) {
    Optional<CustomerEntity> found = repository.findByNifOrEmail(nif, email);

    if (found.isPresent()) {
      throw new NifOrEmailAlreadyExists(nif, email);
    }
  }

}
