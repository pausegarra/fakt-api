package es.pausegarra.fakt.incoices.application.services.create_invoice;

import es.pausegarra.fakt.common.application.interfaces.Service;
import es.pausegarra.fakt.customers.application.dto.CustomerDto;
import es.pausegarra.fakt.customers.application.services.find_customer_by_id.FindCustomerByIdDto;
import es.pausegarra.fakt.customers.domain.entities.CustomerEntity;
import es.pausegarra.fakt.incoices.application.dto.InvoiceDto;
import es.pausegarra.fakt.incoices.domain.entities.InvoiceEntity;
import es.pausegarra.fakt.incoices.domain.entities.InvoiceLineEntity;
import es.pausegarra.fakt.incoices.domain.repositories.InvoicesRepository;
import es.pausegarra.fakt.mother.CustomerMother;
import es.pausegarra.fakt.mother.InvoiceLineMother;
import es.pausegarra.fakt.mother.InvoiceMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateInvoiceServiceTest {

  @Mock
  private InvoicesRepository invoicesRepository;

  @Mock
  private Service<FindCustomerByIdDto, CustomerDto> findCustomerByIdService;

  @InjectMocks
  private CreateInvoiceService createInvoiceService;

  @Test
  public void shouldCreteInvoice() {
    InvoiceEntity invoice = InvoiceMother.random().build();
    List<InvoiceLineEntity> lines = List.of(
      InvoiceLineMother.random().invoice(invoice).build(),
      InvoiceLineMother.random().invoice(invoice).build()
    );
    invoice = invoice.withLines(lines);
    when(invoicesRepository.create(any(InvoiceEntity.class)))
      .thenReturn(invoice);
    when(findCustomerByIdService.handle(any(FindCustomerByIdDto.class)))
      .thenReturn(CustomerDto.fromEntity(invoice.getCustomer()));

    CreateInvoiceDto dto = CreateInvoiceDto.from(
      invoice.getInvoiceNumber(),
      invoice.getCustomer().getId().toString(),
      invoice.getDate(),
      List.of(
        CreateInvoiceLineDto.from(
          lines.get(0).getDescription(),
          lines.get(0).getQuantity(),
          lines.get(0).getUnitPrice(),
          lines.get(0).getTotal()
        ),
        CreateInvoiceLineDto.from(
          lines.get(1).getDescription(),
          lines.get(1).getQuantity(),
          lines.get(1).getUnitPrice(),
          lines.get(1).getTotal()
        )
      )
    );
    InvoiceDto result = createInvoiceService.handle(dto);

    assertNotNull(result);
    assertEquals(invoice.getId(), result.id());
    assertEquals(invoice.getInvoiceNumber(), result.invoiceNumber());
    assertEquals(invoice.getCustomer().getId(), result.customer().id());
    assertEquals(invoice.getDate(), result.date());
    assertEquals(2, result.lines().size());
  }

}