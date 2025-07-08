package es.pausegarra.fakt.customers.infrastructure.spec;

import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.infrastructure.requests.CreateCustomerRequest;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/customers")
@Tag(name = "Customers")
public interface CreateCustomerApiSpec {

  @POST
  @Operation(summary = "Create customer")
  @APIResponse(responseCode = "400", description = "Bad request")
  @APIResponse(responseCode = "401", description = "Unauthenticated")
  @APIResponse(responseCode = "403", description = "Forbidden")
  @APIResponse(responseCode = "201", description = "Customer created")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<CustomerDto> createClient(CreateCustomerRequest request);

}
