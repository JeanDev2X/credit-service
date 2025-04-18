package bank.credit.service;

import java.math.BigDecimal;

import bank.credit.dto.LoanDTO;
import bank.credit.entity.Loan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanService {
	Mono<Loan> createLoan(LoanDTO dto);

	Flux<Loan> getAllLoans();

	Mono<Loan> getByCreditNumber(String creditNumber);

	Mono<Loan> payLoan(String creditNumber, BigDecimal amount);
	
	Flux<Loan> findByDocumentNumber(String documentNumber);
}
