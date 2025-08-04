package es.pausegarra.fakt.incoices.application.services.create_invoice;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.application.services.find_customer_by_id.FindCustomerByIdDto;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.incoices.application.dto.InvoiceDto;
import es.pausegarra.fakt.incoices.domain.entities.InvoiceEntity;
import es.pausegarra.fakt.incoices.domain.repositories.InvoicesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@ApplicationScoped
public class CreateInvoiceService implements Service<CreateInvoiceDto, InvoiceDto> {

  private final InvoicesRepository invoicesRepository;

  private final Service<FindCustomerByIdDto, CustomerDto> findCustomerByIdService;

  @Override
  public InvoiceDto handle(CreateInvoiceDto dto) {
    CustomerEntity customer = findCustomer(dto.customerId());
    return null;
  }

  private CustomerEntity findCustomer(UUID customerId) {
    FindCustomerByIdDto dto = FindCustomerByIdDto.from(customerId.toString());
    CustomerDto customer = findCustomerByIdService.handle(dto);

    return customer.toEntity();
  }

}
