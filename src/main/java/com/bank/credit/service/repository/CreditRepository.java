package com.bank.credit.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.bank.credit.service.entity.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditRepository extends ReactiveMongoRepository<Credit, String>{
	Flux<Credit> findByDocumentNumber(String documentNumber);
	Mono<Credit> findByCreditNumber(String creditNumber);
}
