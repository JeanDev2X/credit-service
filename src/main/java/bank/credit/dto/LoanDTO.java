package bank.credit.dto;

import java.math.BigDecimal;

import bank.credit.entity.LoanType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {
	private String creditNumber;
    private String documentNumber;
    private BigDecimal amount;
    private LoanType type;
}
