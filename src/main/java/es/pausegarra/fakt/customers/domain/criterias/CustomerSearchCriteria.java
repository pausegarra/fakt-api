package es.pausegarra.fakt.customers.domain.criterias;

import es.pausegarra.fakt.common.domain.criteria.Criteria;
import es.pausegarra.fakt.common.domain.pagination_and_sorting.Page;
import es.pausegarra.fakt.common.domain.pagination_and_sorting.Sort;
import es.pausegarra.fakt.common.domain.pagination_and_sorting.SortDirection;
import lombok.Getter;

@Getter
public class CustomerSearchCriteria extends Criteria {

  private final String search;

  private CustomerSearchCriteria(int page, int pageSize, String sortBy, String sortDirection, String search) {
    super(new Page(page, pageSize), new Sort(sortBy, SortDirection.valueOf(sortDirection.toUpperCase())));
    this.search = search;
  }

  public static CustomerSearchCriteria create(
    int page,
    int pageSize,
    String sortBy,
    String sortDirection,
    String search
  ) {
    return new CustomerSearchCriteria(page, pageSize, sortBy, sortDirection, search);
  }

}
