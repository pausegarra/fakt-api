package es.pausegarra.fakt.auth.domain.dto;

import java.util.List;

public record PermissionDto(
  String rsname, String rsid, List<String> scopes
) {}
