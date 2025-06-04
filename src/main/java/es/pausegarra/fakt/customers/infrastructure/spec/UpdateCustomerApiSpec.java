package es.pausegarra.fakt.customers.infrastructure.spec;

import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.infrastructure.requests.UpdateCustomerRequest;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/customers/{id}")
@Tag(name = "Customers")
public interface UpdateCustomerApiSpec {

  @PUT
  @Operation(summary = "Update customer")
  @APIResponse(responseCode = "200", description = "Customer updated")
  @APIResponse(responseCode = "400", description = "Bad request")
  @APIResponse(responseCode = "401", description = "Unauthenticated")
  @APIResponse(responseCode = "403", description = "Forbidden")
  @APIResponse(responseCode = "404", description = "Customer not found")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<CustomerDto> updateCustomer(
    @PathParam("id")
    @Parameter(description = "Customer id", required = true, in = ParameterIn.PATH)
    String id,
    UpdateCustomerRequest request
  );

}
