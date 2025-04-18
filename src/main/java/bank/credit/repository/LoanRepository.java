package bank.credit.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import bank.credit.entity.Loan;
import bank.credit.entity.LoanType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanRepository extends ReactiveMongoRepository<Loan, String>{
	Mono<Loan> findByDocumentNumberAndType(String documentNumber, LoanType type);
    Mono<Loan> findByCreditNumber(String creditNumber);
    Flux<Loan> findByDocumentNumber(String documentNumber);
}
