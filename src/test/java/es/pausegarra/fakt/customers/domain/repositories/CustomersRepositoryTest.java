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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

  @Transactional
  public CustomerEntity createCustomerWithCustomNifAndEmail() {
    CustomerEntity entity = CustomerMother.random()
      .id(null)
      .nif("123456789")
      .email("test@test.com")
      .build();

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

  @Test
  @Transactional
  public void shouldSaveCustomer() {
    CustomerEntity entity = CustomerMother.random()
      .id(null)
      .build();

    CustomerEntity saved = repository.save(entity);

    assertNotNull(saved);
    assertEquals(entity.getName(), saved.getName());

    CustomerEntity found = em.find(CustomerEntity.class, saved.getId());

    assertNotNull(found);
    assertEquals(entity.getName(), found.getName());
  }

  @Test
  public void shouldFindByNifOrEmail() {
    createCustomerWithCustomNifAndEmail();

    Optional<CustomerEntity> found = repository.findByNifOrEmail("123456789", "test@test.com");

    assertTrue(found.isPresent());
  }

  @Test
  public void shouldReturnEmptyOptionalIfNotFound() {
    Optional<CustomerEntity> found = repository.findByNifOrEmail("000000000", "not-found@test.com");

    assertFalse(found.isPresent());
  }

  @Test
  public void shouldFindById() {
    CustomerEntity entity = createCustomer();

    Optional<CustomerEntity> found = repository.getById(entity.getId());

    assertTrue(found.isPresent());
    assertEquals(entity.getId(), found.get().getId());
  }

  @Test
  @Transactional
  public void shouldUpdateCustomer() {
    CustomerEntity entity = createCustomer();

    CustomerEntity updated = CustomerMother.random()
      .id(entity.getId())
      .name("updated")
      .build();

    repository.save(updated);

    CustomerEntity found = em.find(CustomerEntity.class, entity.getId());

    assertEquals(updated.getName(), found.getName());
  }

  @Test
  @Transactional
  public void shouldDeleteCustomer() {
    CustomerEntity entity = createCustomer();

    repository.delete(entity.getId());

    CustomerEntity found = em.find(CustomerEntity.class, entity.getId());

    assertNull(found);
  }

  @Test
  public void shouldReturnEmptyOptionalIfCustomerByEmailOrNifWhereIdNeNotFound() {
    CustomerEntity entity = createCustomerWithCustomNifAndEmail();

    Optional<CustomerEntity> found = repository.findByNifOrEmailWhereIdNe("123456789", "test@test.com", entity.getId());

    assertTrue(found.isEmpty());
  }

  @Test
  public void shouldReturnOptionalIfCustomerByEmailOrNifWhereIdNeFound() {
    CustomerEntity entity = createCustomerWithCustomNifAndEmail();
    CustomerEntity second = createCustomer();

    Optional<CustomerEntity> found = repository.findByNifOrEmailWhereIdNe("123456789", "test@test.com", second.getId());

    assertTrue(found.isPresent());
    assertEquals(entity.getId(), found.get().getId());
  }

}