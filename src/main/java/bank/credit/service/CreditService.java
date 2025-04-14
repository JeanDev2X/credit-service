package bank.credit.service;

import java.math.BigDecimal;

import bank.credit.entity.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {
	Mono<Credit> createCredit(Credit credit);
    Flux<Credit> getCreditsByDocumentNumber(String documentNumber);
    Mono<Credit> getCreditById(String id);
    Mono<Void> deleteCredit(String id);
    Mono<Credit> chargeCreditCard(String id, BigDecimal amount);
    Mono<Credit> updateCredit(String id, Credit credit);
    Mono<Credit> getByCreditNumber(String creditNumber);
}	
