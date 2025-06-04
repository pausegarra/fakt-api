package es.pausegarra.fakt.customers.infrastructure.requests;

public record UpdateCustomerRequest(
  String name,
  String email,
  String country,
  String nif,
  String address,
  String postcode,
  String city,
  String county
) {}
