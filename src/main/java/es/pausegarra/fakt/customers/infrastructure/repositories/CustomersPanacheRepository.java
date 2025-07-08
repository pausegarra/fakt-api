package es.pausegarra.fakt.customers.infrastructure.repositories;

import es.pausegarra.fakt.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.fakt.common.infrastructure.pagination.PageInfo;
import es.pausegarra.fakt.customers.domain.criterias.CustomerSearchCriteria;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.customers.domain.repositories.CustomersRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CustomersPanacheRepository implements CustomersRepository, PanacheRepositoryBase<CustomerEntity, UUID> {

  @Override
  public Paginated<CustomerEntity> findByCriteria(CustomerSearchCriteria criteria) {
    Sort sort = Sort.by(
      criteria.getSorting().sortBy(),
      Sort.Direction.valueOf(criteria.getSorting().sortDirection().getValue())
    );
    Page page = Page.of(
      criteria.getPagination().page(),
      criteria.getPagination().pageSize()
    );

    PanacheQuery<CustomerEntity> query;

    if (criteria.getSearch() != null && !criteria.getSearch().isBlank()) {
      query = find(
        "lower(name) like ?1 or lower(email) like ?1",
        sort,
        "%" + criteria.getSearch().toLowerCase() + "%"
      ).page(page);
    } else {
      query = findAll(sort).page(page);
    }

    PageInfo pageInfo = PageInfo.fromQuery(query);

    return new Paginated<>(
      query.list(),
      pageInfo.page(),
      pageInfo.pageSize(),
      pageInfo.totalPages(),
      pageInfo.totalElements(),
      pageInfo.hasNextPage(),
      pageInfo.hasPreviousPage()
    );
  }

  @Override
  public CustomerEntity save(CustomerEntity customer) {
    return getEntityManager()
      .merge(customer);
  }

  @Override
  public Optional<CustomerEntity> findByNifOrEmail(String nif, String email) {
    return find(
      "lower(nif) like lower(?1) or lower(email) like lower(?2)",
      nif,
      email
    ).firstResultOptional();
  }

  @Override
  public Optional<CustomerEntity> getById(UUID id) {
    return findByIdOptional(id);
  }

  @Override
  public void delete(UUID id) {
    deleteById(id);
  }

  @Override
  public Optional<CustomerEntity> findByNifOrEmailWhereIdNe(String nif, String email, UUID id) {
    return find(
      "(lower(nif) like lower(?1) or lower(email) like lower(?2)) and id != ?3",
      nif,
      email,
      id
    ).firstResultOptional();
  }

}
