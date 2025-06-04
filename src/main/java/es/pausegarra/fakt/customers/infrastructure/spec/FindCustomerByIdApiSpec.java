package es.pausegarra.fakt.customers.infrastructure.spec;

import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import jakarta.ws.rs.GET;
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
public interface FindCustomerByIdApiSpec {

  @GET
  @Operation(summary = "Find customer by id")
  @APIResponse(responseCode = "200", description = "Customer found")
  @APIResponse(responseCode = "404", description = "Customer not found")
  @APIResponse(responseCode = "401", description = "Unauthorized")
  @APIResponse(responseCode = "403", description = "Forbidden")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<CustomerDto> findCustomerById(
    @PathParam("id")
    @Parameter(description = "Customer id", required = true, in = ParameterIn.PATH)
    String id
  );

}
