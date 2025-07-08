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
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class FindCustomerByIdIT extends IntegrationTest {

  @Transactional
  public CustomerEntity createCustomer() {
    CustomerEntity entity = CustomerMother.random()
      .id(null)
      .build();

    em.persist(entity);

    return entity;
  }

  @Test
  @TestSecurity(user = "test", roles = "customers#read")
  public void shouldReturnCustomer() {
    CustomerEntity entity = createCustomer();

    given()
      .when()
      .get("/customers/{id}", entity.getId())
      .then()
      .statusCode(200)
      .body("id", is(entity.getId().toString()))
      .body("name", is(entity.getName()))
      .body("contactName", is(entity.getContactName()))
      .body("email", is(entity.getEmail()))
      .body("country", is(entity.getCountry()))
      .body("nif", is(entity.getNif()))
      .body("address", is(entity.getAddress()))
      .body("postcode", is(entity.getPostcode()))
      .body("city", is(entity.getCity()))
      .body("county", is(entity.getCounty()))
      .body("emailExtraRecipients", is(entity.getEmailExtraRecipients()));
  }

  @Test
  @TestSecurity(user = "test", roles = "customers#read")
  public void shouldReturn404IfNotFound() {
    given()
      .when()
      .get("/customers/{id}", UUID.randomUUID().toString())
      .then()
      .statusCode(404);
  }

  @Test
  public void shouldReturn401IfUnauthenticated() {
    given()
      .when()
      .get("/customers/{id}", UUID.randomUUID().toString())
      .then()
      .statusCode(401);
  }

  @Test
  @TestSecurity(user = "test")
  public void shouldReturn403IfForbidden() {
    given()
      .when()
      .get("/customers/{id}", UUID.randomUUID().toString())
      .then()
      .statusCode(403);
  }

}
