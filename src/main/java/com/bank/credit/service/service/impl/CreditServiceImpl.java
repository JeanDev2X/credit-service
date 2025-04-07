package com.bank.credit.service.service.impl;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bank.credit.service.entity.Credit;
import com.bank.credit.service.repository.CreditRepository;
import com.bank.credit.service.service.CreditService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService{
	
	@Autowired
    private CreditRepository creditRepository;

    @Autowired
    private WebClient.Builder webClientBuilder; // Para llamar al Customer Service

    private static final String CUSTOMER_SERVICE_URL = "http://localhost:8020/customers";

    @Override
    public Mono<Credit> createCredit(Credit credit) {
        return verifyCustomerExists(credit.getDocumentNumber())
                .then(creditRepository.save(credit));
    }

    @Override
    public Flux<Credit> getCreditsByDocumentNumber(String documentNumber) {
        return creditRepository.findByDocumentNumber(documentNumber);
    }

    @Override
    public Mono<Credit> getCreditById(String id) {
        return creditRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteCredit(String id) {
        return creditRepository.deleteById(id);
    }

    @Override
    public Mono<Credit> chargeCreditCard(String id, BigDecimal amount) {
        return creditRepository.findById(id)
                .flatMap(credit -> {
                    if (!"CREDIT_CARD".equalsIgnoreCase(credit.getType())) {
                        return Mono.error(new IllegalArgumentException("El crédito no es una tarjeta de crédito."));
                    }
                    if (credit.getBalance().compareTo(amount) < 0) {
                        return Mono.error(new IllegalArgumentException("Fondos insuficientes en la tarjeta de crédito."));
                    }
                    return verifyCustomerExists(credit.getDocumentNumber()) // Validar que el cliente existe
                            .then(Mono.defer(() -> {
                                credit.setBalance(credit.getBalance().subtract(amount));
                                return creditRepository.save(credit);
                            }));
                });
    }

    private Mono<Void> verifyCustomerExists(String documentNumber) {
        return webClientBuilder.build()
                .get()
                .uri(CUSTOMER_SERVICE_URL + "/{documentNumber}", documentNumber)
                .retrieve()
                .bodyToMono(Void.class) // Solo valida si el cliente existe
                .onErrorResume(e -> Mono.error(new IllegalArgumentException("Cliente no encontrado")));
    }
    
    @Override
    public Mono<Credit> updateCredit(String id, Credit credit) {
        return creditRepository.findById(id)
                .flatMap(existingCredit -> {
                    existingCredit.setBalance(credit.getBalance());
                    // Puedes agregar más campos si deseas actualizarlos también
                    return creditRepository.save(existingCredit);
                });
    }
    
    @Override
    public Mono<Credit> getByCreditNumber(String creditNumber) {
        return creditRepository.findByCreditNumber(creditNumber);
    }
    
}
