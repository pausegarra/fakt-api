package es.pausegarra.fakt.auth.application.servies.find_aiuth_roles;

public record FindAuthRolesDto(
  String token, String clientId
) {

  public static FindAuthRolesDto from(String token, String clientId) {
    return new FindAuthRolesDto(token, clientId);
  }

}
