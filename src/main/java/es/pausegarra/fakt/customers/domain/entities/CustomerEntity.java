package es.pausegarra.fakt.customers.domain.entities;

import es.pausegarra.fakt.common.domain.audit.AuditFields;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", columnDefinition = "uuid")
  private final UUID id;

  private final String name;

  private final String contactName;

  @Column(name = "email", unique = true)
  private final String email;

  private final String country;

  @Column(name = "nif", unique = true)
  private final String nif;

  private final String address;

  private final String postcode;

  private final String city;

  private final String county;

  @Column(name = "email_extra_recipients")
  private final String emailExtraRecipients;

  @Embedded
  private final AuditFields audit;

  public static CustomerEntity create(
    String name,
    String contactName,
    String email,
    String country,
    String nif,
    String address,
    String postcode,
    String city,
    String county,
    String emailExtraRecipients
  ) {
    return new CustomerEntity(null, name, contactName, email, country, nif, address, postcode, city, county, emailExtraRecipients, new AuditFields());
  }

  public CustomerEntity update(
    String name,
    String contactName,
    String email,
    String country,
    String nif,
    String address,
    String postcode,
    String city,
    String county,
    String emailExtraRecipients
  ) {
    return new CustomerEntity(
      id,
      name,
      contactName,
      email,
      country,
      nif,
      address,
      postcode,
      city,
      county,
      emailExtraRecipients,
      audit
    );
  }

}
