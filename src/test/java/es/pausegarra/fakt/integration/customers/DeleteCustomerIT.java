package es.pausegarra.fakt.integration.customers;

import es.pausegarra.fakt.annotations.IntegrationTest;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.mother.CustomerMother;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class DeleteCustomerIT extends IntegrationTest {

  @Transactional
  public CustomerEntity createCustomer() {
    CustomerEntity entity = CustomerMother.random()
      .id(null)
      .build();

    em.persist(entity);

    return entity;
  }

  @Test
  @TestSecurity(user = "test", roles = "customers#delete")
  public void shouldDeleteCustomer() {
    CustomerEntity entity = createCustomer();

    given()
      .when()
      .delete("/customers/{id}", entity.getId())
      .then()
      .statusCode(204);

    CustomerEntity found = em.find(CustomerEntity.class, entity.getId());

    assertNull(found);
  }

  @Test
  @TestSecurity(user = "test", roles = "customers#delete")
  public void shouldReturn204EvenIfCustomerNotFound() {
    given()
      .when()
      .delete("/customers/{id}", UUID.randomUUID().toString())
      .then()
      .statusCode(204);
  }

  @Test
  public void shouldReturn401IfUserIsNotAuthenticated() {
    given()
      .when()
      .delete("/customers/{id}", UUID.randomUUID().toString())
      .then()
      .statusCode(401);
  }

  @Test
  @TestSecurity(user = "test")
  public void shouldReturn403IfUserIsNotAuthorized() {
    given()
      .when()
      .delete("/customers/{id}", UUID.randomUUID().toString())
      .then()
      .statusCode(403);
  }

}
