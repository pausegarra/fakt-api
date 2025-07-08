package es.pausegarra.fakt.integration.customers;

import es.pausegarra.fakt.annotations.IntegrationTest;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.mother.CustomerMother;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class FindCustomersByCriteriaIT extends IntegrationTest {

  @Transactional
  public void createCustomers() {
    for (int i = 0; i < 20; i++) {
      CustomerEntity customer = CustomerMother.random()
        .id(null)
        .build();

      em.persist(customer);
    }

    em.flush();
    em.clear();
  }

  @Transactional
  public CustomerEntity createCustomer() {
    CustomerEntity customer = CustomerMother.random()
      .id(null)
      .build();

    em.persist(customer);

    return customer;
  }

  @Test
  @TestSecurity(user = "test", roles = {"customers#read"})
  public void shouldFindPaginatedCustomers() {
    createCustomers();

    given()
      .when()
      .get("/customers?page=0&pageSize=10&sortBy=name&sortDirection=asc")
      .then()
      .statusCode(200)
      .body("data.name", everyItem(notNullValue()))
      .body("data.contactName", everyItem(notNullValue()))
      .body("data.email", everyItem(notNullValue()))
      .body("data.country", everyItem(notNullValue()))
      .body("data.nif", everyItem(notNullValue()))
      .body("data.address", everyItem(notNullValue()))
      .body("data.postcode", everyItem(notNullValue()))
      .body("data.city", everyItem(notNullValue()))
      .body("data.county", everyItem(notNullValue()))
      .body("data.size()", is(10))
      .body("page", is(0))
      .body("pageSize", is(10))
      .body("totalPages", is(2))
      .body("totalElements", is(20))
      .body("hasNextPage", is(true))
      .body("hasPreviousPage", is(false));
  }

  @Test
  @TestSecurity(user = "test", roles = {"customers#read"})
  public void shouldWorkPageAndPageSize() {
    createCustomers();

    given()
      .when()
      .get("/customers?page=1&pageSize=5&sortBy=name&sortDirection=asc")
      .then()
      .statusCode(200)
      .body("data.name", everyItem(notNullValue()))
      .body("data.contactName", everyItem(notNullValue()))
      .body("data.email", everyItem(notNullValue()))
      .body("data.country", everyItem(notNullValue()))
      .body("data.nif", everyItem(notNullValue()))
      .body("data.address", everyItem(notNullValue()))
      .body("data.postcode", everyItem(notNullValue()))
      .body("data.city", everyItem(notNullValue()))
      .body("data.county", everyItem(notNullValue()))
      .body("data.size()", is(5))
      .body("page", is(1))
      .body("pageSize", is(5))
      .body("totalPages", is(4))
      .body("totalElements", is(20))
      .body("hasNextPage", is(true))
      .body("hasPreviousPage", is(true));
  }

  @Test
  @TestSecurity(user = "test", roles = {"customers#read"})
  public void shouldSearch() {
    CustomerEntity customer = createCustomer();

    given()
      .when()
      .get("/customers?page=0&pageSize=10&sortBy=name&sortDirection=asc&search=" + customer.getName())
      .then()
      .statusCode(200)
      .body("data.name", everyItem(notNullValue()))
      .body("data.contactName", everyItem(notNullValue()))
      .body("data.email", everyItem(notNullValue()))
      .body("data.country", everyItem(notNullValue()))
      .body("data.nif", everyItem(notNullValue()))
      .body("data.address", everyItem(notNullValue()))
      .body("data.postcode", everyItem(notNullValue()))
      .body("data.city", everyItem(notNullValue()))
      .body("data.county", everyItem(notNullValue()))
      .body("data.size()", is(1))
      .body("page", is(0))
      .body("pageSize", is(10))
      .body("totalPages", is(1))
      .body("totalElements", is(1))
      .body("hasNextPage", is(false))
      .body("hasPreviousPage", is(false));
  }

  @Test
  public void shouldReturn401IfUserIsNotAuthenticated() {
    given()
      .when()
      .get("/customers?page=0&pageSize=10&sortBy=name&sortDirection=asc")
      .then()
      .statusCode(401);
  }

  @Test
  @TestSecurity(user = "test")
  public void shouldReturn403IfUserIsNotAuthorized() {
    given()
      .when()
      .get("/customers?page=0&pageSize=10&sortBy=name&sortDirection=asc")
      .then()
      .statusCode(403);
  }

}
