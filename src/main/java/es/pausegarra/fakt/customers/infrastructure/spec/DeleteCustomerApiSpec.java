package es.pausegarra.fakt.customers.infrastructure.spec;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/customers/{id}")
@Tag(name = "Customers")
public interface DeleteCustomerApiSpec {

  @DELETE
  @APIResponse(responseCode = "204", description = "Customer deleted")
  @APIResponse(responseCode = "401", description = "Unauthenticated")
  @APIResponse(responseCode = "403", description = "Forbidden")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<Void> deleteCustomer(
    @PathParam("id")
    @Parameter(description = "Customer id", required = true, in = ParameterIn.PATH)
    String id
  );

}
