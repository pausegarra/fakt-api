package es.pausegarra.fakt.customers.infrastructure.requests;

public record CreateCustomerRequest(
  String name,
  String email,
  String country,
  String nif,
  String address,
  String postcode,
  String city,
  String county
) {}
