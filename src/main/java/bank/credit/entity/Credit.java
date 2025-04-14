package bank.credit.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

@Data
@Document(collection = "credits")
public class Credit {
	@Id
    private String id;
	@NotBlank
    private String creditNumber; // Nuevo campo único, similar a accountNumber
    private String documentNumber;
    private String type; // PERSONAL, BUSINESS, CREDIT_CARD
    private BigDecimal balance;//saldo
    private BigDecimal amount;//monto
    private int termMonths;//plazo Meses
    private BigDecimal interestRate;//tasa de interés
}
