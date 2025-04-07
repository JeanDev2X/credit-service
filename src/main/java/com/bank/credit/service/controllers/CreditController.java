package com.bank.credit.service.controllers;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bank.credit.service.dto.CreditResponse;
import com.bank.credit.service.entity.Credit;
import com.bank.credit.service.service.CreditService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/credits")
public class CreditController {
	@Autowired
    private CreditService creditService;

    @PostMapping
    public Mono<Credit> createCredit(@RequestBody Credit credit) {
        return creditService.createCredit(credit);
    }

    @GetMapping("/customer/{documentNumber}")
    public Flux<Credit> getCreditsByDocumentNumber(@PathVariable String documentNumber) {
        return creditService.getCreditsByDocumentNumber(documentNumber);
    }
    
    @GetMapping("/by-credit-number/{creditNumber}")
    public Mono<CreditResponse> getByCreditNumber(@PathVariable String creditNumber) {
        return creditService.getByCreditNumber(creditNumber)
                .map(credit -> CreditResponse.builder()
                        .id(credit.getId())
                        .creditNumber(credit.getCreditNumber())
                        .documentNumber(credit.getDocumentNumber())
                        .balance(credit.getBalance())
                        .type(credit.getType())
                        .build());
    }

    @GetMapping("/{id}")
    public Mono<CreditResponse> getCreditById(@PathVariable String id) {
        return creditService.getCreditById(id)
                .map(credit -> CreditResponse.builder()
                        .id(credit.getId())
                        .documentNumber(credit.getDocumentNumber())
                        .balance(credit.getBalance())
                        .build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCredit(@PathVariable String id) {
        return creditService.deleteCredit(id);
    }

    @PostMapping("/{id}/charge")
    public Mono<Credit> chargeCreditCard(@PathVariable String id, @RequestParam BigDecimal amount) {
        return creditService.chargeCreditCard(id, amount);
    }
    
    @PutMapping("/{id}")
    public Mono<CreditResponse> updateCredit(@PathVariable String id, @RequestBody Credit credit) {
        credit.setId(id);
        return creditService.updateCredit(id, credit)
                .map(updatedCredit -> CreditResponse.builder()
                        .id(updatedCredit.getId())
                        .documentNumber(updatedCredit.getDocumentNumber())
                        .balance(updatedCredit.getBalance())
                        .build());
    }
}
