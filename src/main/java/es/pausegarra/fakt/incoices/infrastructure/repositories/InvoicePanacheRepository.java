package es.pausegarra.fakt.incoices.infrastructure.repositories;

import es.pausegarra.fakt.incoices.domain.entities.InvoiceEntity;
import es.pausegarra.fakt.incoices.domain.repositories.InvoicesRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InvoicePanacheRepository implements InvoicesRepository, PanacheRepository<InvoiceEntity> {

  @Override
  public InvoiceEntity create(InvoiceEntity invoice) {
    persist(invoice);

    return invoice;
  }

}
