package bank.credit.dto;

import java.math.BigDecimal;

import bank.credit.entity.CardOwnerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDTO {
	private String cardsNumber;
    private String documentNumber;
    private BigDecimal creditLimit;
    private BigDecimal balance;
    private CardOwnerType ownerType; // PERSONAL, BUSINESS
}
