package bank.credit.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import bank.credit.dto.LoanDTO;
import bank.credit.entity.Loan;
import bank.credit.entity.LoanType;
import bank.credit.repository.LoanRepository;
import bank.credit.service.LoanService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private WebClient.Builder webClientBuilder;

	private static final String CUSTOMER_SERVICE_URL = "http://localhost:8020/customers";

	@Override
	public Mono<Loan> createLoan(LoanDTO dto) {
		return verifyCustomerExists(dto.getDocumentNumber()).flatMap(valid -> {
			if (dto.getType() == LoanType.PERSONAL) {
				return loanRepository.findByDocumentNumberAndType(dto.getDocumentNumber(), LoanType.PERSONAL)
						.flatMap(existing -> Mono.<Loan>error(new RuntimeException("Ya existe un préstamo personal")))
						.switchIfEmpty(Mono.defer(() -> {
							Loan loan = new Loan(null, dto.getCreditNumber(), dto.getDocumentNumber(), dto.getAmount(),
									dto.getAmount(), dto.getType(), LocalDate.now());
							return loanRepository.save(loan);
						}));
			} else {
				Loan loan = new Loan(null, dto.getCreditNumber(), dto.getDocumentNumber(), dto.getAmount(),
						dto.getAmount(), dto.getType(), LocalDate.now());
				return loanRepository.save(loan);
			}
		});
	}
	
	private Mono<Boolean> verifyCustomerExists(String documentNumber) {
	    return webClientBuilder.build()
	        .get()
	        .uri(CUSTOMER_SERVICE_URL + "/document/{documentNumber}", documentNumber)
	        .retrieve()
	        .bodyToMono(Void.class)
	        .thenReturn(true)
	        .onErrorResume(e -> Mono.error(new RuntimeException("Cliente no encontrado con documento: " + documentNumber)));
	}

	@Override
	public Flux<Loan> getAllLoans() {
		return loanRepository.findAll();
	}

	@Override
	public Mono<Loan> getByCreditNumber(String creditNumber) {
		return loanRepository.findByCreditNumber(creditNumber);
	}

	//pagar credito-prestamo
	@Override
	public Mono<Loan> payLoan(String creditNumber, BigDecimal amount) {
		return loanRepository.findByCreditNumber(creditNumber)
	            .flatMap(loan -> {
	                if (loan.getBalance().compareTo(amount) < 0) {
	                    return Mono.error(new IllegalArgumentException("El monto excede el saldo de la deuda"));
	                }
	                loan.setBalance(loan.getBalance().subtract(amount));
	                return loanRepository.save(loan);
	            });
	}
	
	public Flux<Loan> findByDocumentNumber(String documentNumber) {
	    return loanRepository.findByDocumentNumber(documentNumber);
	}

}
