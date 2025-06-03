package es.pausegarra.fakt.customers.domain.repositories;

import es.pausegarra.fakt.annotations.IntegrationTest;
import es.pausegarra.fakt.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.fakt.customers.domain.criterias.CustomerSearchCriteria;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.mother.CustomerMother;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class CustomersRepositoryTest extends IntegrationTest {

  @Inject
  CustomersRepository repository;

  @Transactional
  public CustomerEntity createCustomer() {
    CustomerEntity entity = CustomerMother.random().id(null).build();

    em.persist(entity);

    return entity;
  }

  @Test
  public void shouldFindByCriteria() {
    CustomerEntity entity = createCustomer();

    CustomerSearchCriteria criteria = CustomerSearchCriteria.create(0, 10, "name", "ASC", null);
    Paginated<CustomerEntity> customers = repository.findByCriteria(criteria);

    assertEquals(1, customers.data().size());
    assertEquals(entity.getName(), customers.data().getFirst().getName());
  }

}