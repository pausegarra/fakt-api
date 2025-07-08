package es.pausegarra.fakt.integration.customers;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.pausegarra.fakt.annotations.IntegrationTest;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.customers.infrastructure.requests.UpdateCustomerRequest;
import es.pausegarra.fakt.mother.CustomerMother;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class UpdateCustomerIT extends IntegrationTest {

  @Transactional
  public CustomerEntity createCustomer() {
    CustomerEntity entity = CustomerMother.random()
      .id(null)
      .build();

    em.persist(entity);

    return entity;
  }

  @Transactional
  public CustomerEntity createCustomerWithCustomEmail() {
    CustomerEntity entity = CustomerMother.random()
      .id(null)
      .email("test@test.com")
      .build();

    em.persist(entity);

    return entity;
  }

  @Transactional
  public CustomerEntity createCustomerWithCustomNif() {
    CustomerEntity entity = CustomerMother.random()
      .id(null)
      .nif("123456789")
      .build();

    em.persist(entity);

    return entity;
  }

  @Test
  @TestSecurity(user = "test", roles = "customers#edit")
  public void shouldUpdateCustomer() throws JsonProcessingException {
    CustomerEntity entity = createCustomer();
    UpdateCustomerRequest request = new UpdateCustomerRequest(
      "updated",
      "contactName",
      "updated@test.com",
      "country",
      "nif",
      "address",
      "postcode",
      "city",
      "county",
      null
    );
    String json = objectMapper.writeValueAsString(request);

    given()
      .body(json)
      .contentType("application/json")
      .when()
      .put("/customers/{id}", entity.getId())
      .then()
      .statusCode(200)
      .body("id", is(entity.getId().toString()))
      .body("name", is(request.name()))
      .body("email", is(request.email()))
      .body("country", is(request.country()))
      .body("nif", is(request.nif()))
      .body("address", is(request.address()))
      .body("postcode", is(request.postcode()))
      .body("city", is(request.city()))
      .body("county", is(request.county()));

    CustomerEntity updated = em.find(CustomerEntity.class, entity.getId());

    assertNotNull(updated);
    assertEquals(request.name(), updated.getName());
    assertEquals(request.email(), updated.getEmail());
    assertEquals(request.country(), updated.getCountry());
    assertEquals(request.nif(), updated.getNif());
    assertEquals(request.address(), updated.getAddress());
    assertEquals(request.postcode(), updated.getPostcode());
    assertEquals(request.city(), updated.getCity());
    assertEquals(request.county(), updated.getCounty());
  }

  @Test
  @TestSecurity(user = "test", roles = "customers#edit")
  public void shouldReturn404IfCustomerNotFound() throws JsonProcessingException {
    UpdateCustomerRequest request = new UpdateCustomerRequest(
      "updated",
      "contactName",
      "updated@test.com",
      "country",
      "nif",
      "address",
      "postcode",
      "city",
      "county",
      null
    );
    String json = objectMapper.writeValueAsString(request);

    given()
      .body(json)
      .contentType("application/json")
      .when()
      .put("/customers/{id}", UUID.randomUUID().toString())
      .then()
      .statusCode(404);
  }

  @Test
  public void shouldReturn401IfUnauthenticated() {
    given()
      .when()
      .put("/customers/{id}", UUID.randomUUID().toString())
      .then()
      .statusCode(401);
  }

  @Test
  @TestSecurity(user = "test")
  public void shouldReturn403IfForbidden() {
    given()
      .when()
      .put("/customers/{id}", UUID.randomUUID().toString())
      .then()
      .statusCode(403);
  }

  @Test
  @TestSecurity(user = "test", roles = "customers#edit")
  public void shouldReturn400IfRequestIsInvalid() throws JsonProcessingException {
    UpdateCustomerRequest request = new UpdateCustomerRequest(null, null, null, null, null, null, null, null, null, null);
    String json = objectMapper.writeValueAsString(request);

    given()
      .body(json)
      .contentType("application/json")
      .when()
      .put("/customers/{id}", UUID.randomUUID().toString())
      .then()
      .statusCode(400)
      .body("errors.size()", is(9))
      .body("errors.field", hasItems("handle.dto.name", "handle.dto.contactName", "handle.dto.postcode", "handle.dto.country", "handle.dto.county", "handle.dto.address", "handle.dto.email", "handle.dto.city", "handle.dto.nif"))
      .body("errors.message", hasItem("must not be blank"));
  }

  @Test
  @TestSecurity(user = "test", roles = "customers#edit")
  public void shouldReturn400IfEmailIsInvalid() throws JsonProcessingException {
    CustomerEntity entity = createCustomer();
    UpdateCustomerRequest request = new UpdateCustomerRequest("name", "email", "null", "null", "null", "null", "null", "null", "null", null);
    String json = objectMapper.writeValueAsString(request);

    given()
      .body(json)
      .contentType(ContentType.JSON)
      .when()
      .put("/customers/{id}", entity.getId().toString())
      .then()
      .statusCode(400)
      .body("errors.size()", is(1))
      .body("errors.field", hasItems("handle.dto.email"))
      .body("errors.message", hasItem("must be a well-formed email address"));
  }

  @Test
  @TestSecurity(
    user = "test",
    roles = {"customers#edit"}
  )
  public void shouldReturn400IfEmailAlreadyExists() throws JsonProcessingException {
    CustomerEntity entity = createCustomerWithCustomEmail();
    CustomerEntity toUpdate = createCustomer();
    UpdateCustomerRequest request = new UpdateCustomerRequest(toUpdate.getName(), toUpdate.getContactName(), entity.getEmail(), "null", "null", "null", "null", "null", "null", null);
    String json = objectMapper.writeValueAsString(request);

    given()
      .body(json)
      .contentType(ContentType.JSON)
      .when()
      .put("/customers/{id}", toUpdate.getId())
      .then()
      .statusCode(400)
      .body("code", is("NIF_OR_EMAIL_ALREADY_EXISTS"))
      .body("message", notNullValue())
      .body("status", is(400));
  }

  @Test
  @TestSecurity(
    user = "test",
    roles = {"customers#edit"}
  )
  public void shouldReturn400IfNifAlreadyExists() throws JsonProcessingException {
    CustomerEntity entity = createCustomerWithCustomNif();
    CustomerEntity toUpdate = createCustomer();
    UpdateCustomerRequest request = new UpdateCustomerRequest(toUpdate.getName(), toUpdate.getContactName(), "test@test.com", "null", entity.getNif(), "null", "null", "null", "null", null);
    String json = objectMapper.writeValueAsString(request);

    given()
      .body(json)
      .contentType(ContentType.JSON)
      .when()
      .put("/customers/{id}", toUpdate.getId())
      .then()
      .statusCode(400)
      .body("code", is("NIF_OR_EMAIL_ALREADY_EXISTS"))
      .body("message", notNullValue())
      .body("status", is(400));
  }

  @Test
  @TestSecurity(
    user = "test",
    roles = {"customers#edit"}
  )
  public void shouldReturn200IfCustomerNifIsTheSameAsTheOldOne() throws JsonProcessingException {
    CustomerEntity entity = createCustomerWithCustomNif();
    UpdateCustomerRequest request = new UpdateCustomerRequest(entity.getName(), entity.getContactName(), "test@test.com", "null", entity.getNif(), "null", "null", "null", "null", null);
    String json = objectMapper.writeValueAsString(request);

    given()
      .body(json)
      .contentType(ContentType.JSON)
      .when()
      .put("/customers/{id}", entity.getId())
      .then()
      .statusCode(200);
  }

  @Test
  @TestSecurity(
    user = "test",
    roles = {"customers#edit"}
  )
  public void shouldReturn200IfCustomerEmailIsTheSameAsTheOldOne() throws JsonProcessingException {
    CustomerEntity entity = createCustomerWithCustomEmail();
    UpdateCustomerRequest request = new UpdateCustomerRequest(entity.getName(), entity.getContactName(), "test@test.com", "null", "null", "null", "null", "null", "null", null);
    String json = objectMapper.writeValueAsString(request);

    given()
      .body(json)
      .contentType(ContentType.JSON)
      .when()
      .put("/customers/{id}", entity.getId())
      .then()
      .statusCode(200);
  }

}
