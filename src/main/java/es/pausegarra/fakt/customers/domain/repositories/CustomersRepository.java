package es.pausegarra.fakt.customers.domain.repositories;

import es.pausegarra.fakt.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.fakt.customers.domain.criterias.CustomerSearchCriteria;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;

public interface CustomersRepository {

  Paginated<CustomerEntity> findByCriteria(CustomerSearchCriteria criteria);

}
