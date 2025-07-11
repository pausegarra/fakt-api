package es.pausegarra.fakt.auth.infrastructure.rest;

import es.pausegarra.fakt.auth.application.dto.PermissionsDto;
import es.pausegarra.fakt.auth.application.servies.find_aiuth_roles.FindAuthRolesDto;
import es.pausegarra.fakt.auth.infrastructure.config.KeycloakConfig;
import es.pausegarra.fakt.auth.infrastructure.spec.FindAuthRolesApiSpec;
import es.pausegarra.fakt.common.application.interfaces.Service;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class FindAuthRolesResource implements FindAuthRolesApiSpec {

  private final Service<FindAuthRolesDto, PermissionsDto> service;

  @Inject
  JsonWebToken jsonWebToken;

  private final KeycloakConfig keycloakConfig;

  @Override
  @Authenticated
  public RestResponse<PermissionsDto> getPermissionsAndRoles() {
    FindAuthRolesDto dto = FindAuthRolesDto.from(
      jsonWebToken.getRawToken(),
      keycloakConfig.clientId()
    );
    PermissionsDto permissionsDto = service.handle(dto);

    return RestResponse.ok(permissionsDto);
  }

}
