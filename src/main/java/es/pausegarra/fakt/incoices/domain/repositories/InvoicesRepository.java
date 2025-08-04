package es.pausegarra.fakt.incoices.domain.repositories;

import es.pausegarra.fakt.incoices.domain.entities.InvoiceEntity;

public interface InvoicesRepository {

  InvoiceEntity create(InvoiceEntity invoice);

}
