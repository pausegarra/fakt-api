package es.pausegarra.fakt.integration.customers;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.pausegarra.fakt.annotations.IntegrationTest;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.customers.infrastructure.requests.CreateCustomerRequest;
import es.pausegarra.fakt.mother.CustomerMother;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class CreateCustomerIT extends IntegrationTest {

  @Transactional
  public CustomerEntity createCustomerWithCustomEmail() {
    CustomerEntity entity = CustomerMother.random()
      .id(null)
      .email("test@test.com")
      .build();

    em.persist(entity);

    em.flush();

    return entity;
  }

  @Transactional
  public CustomerEntity createCustomerWithCustomNif() {
    CustomerEntity entity = CustomerMother.random()
      .id(null)
      .nif("123456789")
      .build();

    em.persist(entity);

    em.flush();

    return entity;
  }

  @Test
  @TestSecurity(
    user = "test",
    roles = {"customers#create"}
  )
  public void shouldCreateCustomer() throws JsonProcessingException {
    CustomerEntity entity = CustomerMother.random().id(null).build();
    CreateCustomerRequest request = new CreateCustomerRequest(
      entity.getName(),
      entity.getEmail(),
      entity.getCountry(),
      entity.getNif(),
      entity.getAddress(),
      entity.getPostcode(),
      entity.getCity(),
      entity.getCounty()
    );
    String body = objectMapper.writeValueAsString(request);

    CustomerDto created = given().when()
      .body(body)
      .contentType("application/json")
      .post("/customers")
      .then()
      .statusCode(201)
      .body("id", is(notNullValue()))
      .body("name", is(entity.getName()))
      .body("email", is(entity.getEmail()))
      .body("country", is(entity.getCountry()))
      .body("nif", is(entity.getNif()))
      .body("address", is(entity.getAddress()))
      .body("postcode", is(entity.getPostcode()))
      .body("city", is(entity.getCity()))
      .body("county", is(entity.getCounty()))
      .extract()
      .as(CustomerDto.class);

    CustomerEntity found = em.find(CustomerEntity.class, created.id());
    assertNotNull(created);
    assertEquals(request.name(), found.getName());
    assertEquals(request.email(), found.getEmail());
    assertEquals(request.country(), found.getCountry());
    assertEquals(request.nif(), found.getNif());
    assertEquals(request.address(), found.getAddress());
    assertEquals(request.postcode(), found.getPostcode());
    assertEquals(request.city(), found.getCity());
    assertEquals(request.county(), found.getCounty());
  }

  @Test
  @TestSecurity(
    user = "test",
    roles = {"customers#create"}
  )
  public void shouldReturn400IfRequestIsInvalid() throws JsonProcessingException {
    CreateCustomerRequest request = new CreateCustomerRequest(null, null, null, null, null, null, null, null);
    String body = objectMapper.writeValueAsString(request);

    given()
      .body(body)
      .contentType("application/json")
      .when()
      .post("/customers")
      .then()
      .statusCode(400)
      .body("errors.size()", is(8))
      .body("errors.field", hasItems("handle.dto.name", "handle.dto.postcode", "handle.dto.country", "handle.dto.county", "handle.dto.address", "handle.dto.email", "handle.dto.city", "handle.dto.nif"))
      .body("errors.message", hasItem("must not be blank"));
  }

  @Test
  @TestSecurity(
    user = "test",
    roles = {"customers#create"}
  )
  public void shouldReturn400IfEmailIsInvalid() throws JsonProcessingException {
    CreateCustomerRequest request = new CreateCustomerRequest("name", "email", "null", "null", "null", "null", "null", "null");
    String body = objectMapper.writeValueAsString(request);

    given()
      .body(body)
      .contentType(ContentType.JSON)
      .when()
      .post("/customers")
      .then()
      .statusCode(400)
      .body("errors.size()", is(1))
      .body("errors.field", hasItems("handle.dto.email"))
      .body("errors.message", hasItem("must be a well-formed email address"));
  }

  @Test
  @TestSecurity(
    user = "test",
    roles = {"customers#create"}
  )
  public void shouldReturn400IfEmailAlreadyExists() throws JsonProcessingException {
    CustomerEntity entity = createCustomerWithCustomEmail();
    CreateCustomerRequest request = new CreateCustomerRequest(entity.getName(), entity.getEmail(), "null", "null", "null", "null", "null", "null");
    String body = objectMapper.writeValueAsString(request);

    given()
      .body(body)
      .contentType(ContentType.JSON)
      .when()
      .post("/customers")
      .then()
      .statusCode(400)
      .body("code", is("NIF_OR_EMAIL_ALREADY_EXISTS"))
      .body("message", notNullValue())
      .body("status", is(400));
  }

  @Test
  @TestSecurity(
    user = "test",
    roles = {"customers#create"}
  )
  public void shouldReturn400IfNifAlreadyExists() throws JsonProcessingException {
    CustomerEntity entity = createCustomerWithCustomNif();
    CreateCustomerRequest request = new CreateCustomerRequest(entity.getName(), "test@test.com", "null", entity.getNif(), "null", "null", "null", "null");
    String body = objectMapper.writeValueAsString(request);

    given()
      .body(body)
      .contentType(ContentType.JSON)
      .when()
      .post("/customers")
      .then()
      .statusCode(400)
      .body("code", is("NIF_OR_EMAIL_ALREADY_EXISTS"))
      .body("message", notNullValue())
      .body("status", is(400));
  }

  @Test
  public void shouldReturn401IfUserIsNotAuthenticated() {
    given().when().post("/customers").then().statusCode(401);
  }

  @Test
  @TestSecurity(user = "test")
  public void shouldReturn403IfUserIsNotAuthorized() {
    given().when().post("/customers").then().statusCode(403);
  }

}
