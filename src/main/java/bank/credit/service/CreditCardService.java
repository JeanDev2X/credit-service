package bank.credit.service;

import java.math.BigDecimal;

import bank.credit.dto.CreditCardDTO;
import bank.credit.entity.CreditCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditCardService {
	Mono<CreditCard> createCreditCard(CreditCardDTO dto);
    Flux<CreditCard> getAllCreditCards();
    Mono<CreditCard> getByCardNumber(String cardsNumber);
    Mono<CreditCard> chargeCreditCard(String cardsNumber, BigDecimal amount);
    public Mono<CreditCard> payCreditCard(String cardsNumber, BigDecimal amount);
}
