package bank.credit.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import bank.credit.entity.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditRepository extends ReactiveMongoRepository<Credit, String>{
	Flux<Credit> findByDocumentNumber(String documentNumber);
	Mono<Credit> findByCreditNumber(String creditNumber);
}
