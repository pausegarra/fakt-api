package es.pausegarra.fakt.customers.application.services.find_customers_by_citeria;

public record FIndCustomersByCriteriaDto(
  int page, int pageSize, String sortBy, String sortDirection, String search
) {

  public static FIndCustomersByCriteriaDto create(
    int page,
    int pageSize,
    String sortBy,
    String sortDirection,
    String search
  ) {
    return new FIndCustomersByCriteriaDto(page, pageSize, sortBy, sortDirection, search);
  }

}
