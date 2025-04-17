package bank.credit.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import bank.credit.entity.CreditCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditCardRepository extends ReactiveMongoRepository<CreditCard, String>{
	Mono<CreditCard> findByCardsNumber(String cardsNumber);
}
