package es.pausegarra.fakt.common.domain.pagination_and_sorting;

import java.util.List;

public record Paginated<E>(
  List<E> data,
  int page,
  int pageSize,
  int totalPages,
  long totalElements,
  boolean hasNextPage,
  boolean hasPreviousPage
) {}
