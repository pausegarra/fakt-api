package es.pausegarra.fakt.incoices.domain.entities;

import es.pausegarra.fakt.common.domain.audit.AuditFields;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "invoices")
@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class InvoiceEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  @Column(name = "invoice_number", unique = true, nullable = false)
  private final String invoiceNumber;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "customer_id", nullable = false)
  private final CustomerEntity customer;

  @Column(name = "due_date")
  private final LocalDate dueDate;

  @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
  private final List<InvoiceLineEntity> lines;

  @Embedded
  private final AuditFields audit;

}
