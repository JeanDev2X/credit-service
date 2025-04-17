package bank.credit.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import bank.credit.dto.CreditCardDTO;
import bank.credit.entity.CreditCard;
import bank.credit.repository.CreditCardRepository;
import bank.credit.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

	@Autowired
	private CreditCardRepository creditCardRepository;

	@Autowired
	private WebClient.Builder webClientBuilder;

	private static final String CUSTOMER_SERVICE_URL = "http://localhost:8020/customers";

	@Override
	public Mono<CreditCard> createCreditCard(CreditCardDTO dto) {
		return verifyCustomerExists(dto.getDocumentNumber()).flatMap(valid -> {
			CreditCard card = new CreditCard();
			card.setCardsNumber(dto.getCardsNumber());
			card.setDocumentNumber(dto.getDocumentNumber());
			card.setCreditLimit(dto.getCreditLimit());
			card.setBalance(dto.getBalance());
			card.setOwnerType(dto.getOwnerType());
			card.setIssueDate(LocalDate.now());
			return creditCardRepository.save(card);
		});
	}

	private Mono<Boolean> verifyCustomerExists(String documentNumber) {
		return webClientBuilder.build().get().uri(CUSTOMER_SERVICE_URL + "/document/{documentNumber}", documentNumber)
				.retrieve().bodyToMono(Void.class).thenReturn(true).onErrorResume(e -> Mono
						.error(new RuntimeException("Cliente no encontrado con documento: " + documentNumber)));
	}

	@Override
	public Flux<CreditCard> getAllCreditCards() {
		return creditCardRepository.findAll();
	}

	@Override
	public Mono<CreditCard> getByCardNumber(String cardsNumber) {
		return creditCardRepository.findByCardsNumber(cardsNumber);
	}

	@Override
	public Mono<CreditCard> chargeCreditCard(String cardsNumber, BigDecimal amount) {
		return creditCardRepository.findByCardsNumber(cardsNumber).flatMap(card -> {
			if (card.getBalance().compareTo(amount) < 0) {
				return Mono.error(new RuntimeException("Fondos insuficientes en la tarjeta de crédito."));
			}
			card.setBalance(card.getBalance().subtract(amount));
			return creditCardRepository.save(card);
		});
	}

	// pagar credito-tarjeta
	@Override
	public Mono<CreditCard> payCreditCard(String cardsNumber, BigDecimal amount) {
		return creditCardRepository.findByCardsNumber(cardsNumber).flatMap(card -> {
			// Aumentamos el balance (disminuye la deuda)
			BigDecimal newBalance = card.getBalance().add(amount);

			// No debe superar el límite de crédito
			if (newBalance.compareTo(card.getCreditLimit()) > 0) {
				return Mono.error(new IllegalArgumentException("El pago excede el límite de la tarjeta"));
			}

			card.setBalance(newBalance);
			return creditCardRepository.save(card);
		});
	}
}
