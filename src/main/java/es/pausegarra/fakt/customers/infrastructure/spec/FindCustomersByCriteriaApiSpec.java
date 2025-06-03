package es.pausegarra.fakt.customers.infrastructure.spec;

import es.pausegarra.fakt.common.infrastructure.presentations.PaginatedPresentation;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/customers")
@Tag(name = "Customers")
public interface FindCustomersByCriteriaApiSpec {

  @GET
  @Operation(summary = "Find customers by criteria")
  @APIResponse(responseCode = "200", description = "Customers found")
  @APIResponse(responseCode = "401", description = "Unauthorized")
  @APIResponse(responseCode = "403", description = "Forbidden")
  @APIResponse(responseCode = "500", description = "Internal server error")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<PaginatedPresentation<CustomerDto>> findCustomersByCriteria(
    @QueryParam("page")
    @DefaultValue("0")
    @Parameter(
      name = "page", in = ParameterIn.QUERY, example = "0"
    )
    int page,

    @QueryParam("pageSize")
    @DefaultValue("10")
    @Parameter(
      name = "pageSize", in = ParameterIn.QUERY, example = "10"
    )
    int pageSize,

    @QueryParam("sortBy")
    @DefaultValue("name")
    @Parameter(
      name = "sortBy", in = ParameterIn.QUERY, example = "name"
    )
    String sortBy,

    @QueryParam("sortDirection")
    @DefaultValue("asc")
    @Parameter(
      name = "sortDirection", in = ParameterIn.QUERY, example = "asc"
    )
    String sortDirection,

    @QueryParam("search")
    @DefaultValue("")
    @Parameter(
      name = "search", in = ParameterIn.QUERY, example = "search"
    )
    String search
  );

}
