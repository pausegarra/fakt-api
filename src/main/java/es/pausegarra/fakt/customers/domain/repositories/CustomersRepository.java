package es.pausegarra.fakt.customers.domain.repositories;

import es.pausegarra.fakt.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.fakt.customers.domain.criterias.CustomerSearchCriteria;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;

import java.util.Optional;
import java.util.UUID;

public interface CustomersRepository {

  Paginated<CustomerEntity> findByCriteria(CustomerSearchCriteria criteria);

  CustomerEntity save(CustomerEntity customer);

  Optional<CustomerEntity> findByNifOrEmail(String nif, String email);

  Optional<CustomerEntity> findByNifOrEmailWhereIdNe(String nif, String email, UUID id);

  Optional<CustomerEntity> getById(UUID id);

  void delete(UUID id);

}
