package bank.credit.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class CreditResponse {
	private String id;
	private String creditNumber;
    private String documentNumber;
    private String type;
    private BigDecimal balance;
}
