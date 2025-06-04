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

  Optional<CustomerEntity> getById(UUID id);

}
