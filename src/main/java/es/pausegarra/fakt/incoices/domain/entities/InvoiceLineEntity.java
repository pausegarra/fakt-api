package es.pausegarra.fakt.incoices.domain.entities;

import es.pausegarra.fakt.common.domain.audit.AuditFields;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "invoice_lines")
@Getter
@Builder
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class InvoiceLineEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "invoice_id", nullable = false)
  private final InvoiceEntity invoice;

  @Column(name = "line_number", nullable = false)
  private final String description;

  @Column(name = "quantity", nullable = false)
  private final Double quantity;

  @Column(name = "unit_price", nullable = false)
  private final BigDecimal unitPrice;

  @Column(name = "total", nullable = false)
  private final BigDecimal total;

  @Embedded
  private final AuditFields audit;

}
