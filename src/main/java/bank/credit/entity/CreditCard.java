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
@Document(collection = "credit_cards")
public class CreditCard {
	@Id
    private String id;
	private String cardsNumber;
    //private String customerId;
    private String documentNumber;
    private BigDecimal creditLimit;
    private BigDecimal balance;
    private CardOwnerType ownerType; // PERSONAL, BUSINESS
    private LocalDate issueDate;
}
