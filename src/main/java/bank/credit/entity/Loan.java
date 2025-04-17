package bank.credit.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "loans")
public class Loan {
	@Id
    private String id;
    private String creditNumber;      // Número del préstamo
    private String documentNumber;    // Documento del cliente
    private BigDecimal amount;        // Monto total del préstamo
    private BigDecimal balance;       // Saldo pendiente (deuda actual)
    private LoanType type;            // PERSONAL o BUSINESS
    private LocalDate startDate;      // Fecha de creación
}
