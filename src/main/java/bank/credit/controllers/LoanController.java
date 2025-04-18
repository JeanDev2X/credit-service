package bank.credit.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bank.credit.dto.CreditCardDTO;
import bank.credit.dto.LoanDTO;
import bank.credit.entity.CreditCard;
import bank.credit.entity.Loan;
import bank.credit.service.CreditCardService;
import bank.credit.service.LoanService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/loans")
public class LoanController {
	
	@Autowired
    private LoanService loanService;
	
	@Autowired
	private CreditCardService creditCardService;

	@PostMapping
    public Mono<Loan> createLoan(@RequestBody LoanDTO dto) {
        return loanService.createLoan(dto);
    }

    @GetMapping
    public Flux<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/{creditNumber}")
    public Mono<Loan> getLoanByCreditNumber(@PathVariable String creditNumber) {
        return loanService.getByCreditNumber(creditNumber);
    }

    @PostMapping("/{creditNumber}/pay")
    public Mono<Loan> payLoan(@PathVariable String creditNumber, @RequestParam BigDecimal amount) {
        return loanService.payLoan(creditNumber, amount);
    }
    
 // Credit Card Endpoints

    @PostMapping("/credit-cards")
    public Mono<CreditCard> createCreditCard(@RequestBody CreditCardDTO dto) {
        return creditCardService.createCreditCard(dto);
    }

    @GetMapping("/credit-cards")
    public Flux<CreditCard> getAllCreditCards() {
        return creditCardService.getAllCreditCards();
    }

    @GetMapping("/credit-cards/{cardNumber}")
    public Mono<CreditCard> getCreditCardByCardNumber(@PathVariable String cardNumber) {
        return creditCardService.getByCardNumber(cardNumber);
    }

    @PostMapping("/credit-cards/{cardNumber}/charge")
    public Mono<CreditCard> chargeCreditCard(@PathVariable String cardNumber, @RequestParam BigDecimal amount) {
        return creditCardService.chargeCreditCard(cardNumber, amount);
    }
    
    @PostMapping("/credit-cards/{cardsNumber}/pay")
    public Mono<CreditCard> payCreditCard(@PathVariable String cardsNumber, @RequestParam BigDecimal amount) {
        return creditCardService.payCreditCard(cardsNumber, amount);
    }
    
    @GetMapping("/by-document/{documentNumber}")
    public Flux<Loan> getLoansByDocument(@PathVariable String documentNumber) {
        return loanService.findByDocumentNumber(documentNumber);
    }
    
    @GetMapping("/credit-cards/by-document/{documentNumber}")
    public Flux<CreditCard> getCreditCardsByDocument(@PathVariable String documentNumber) {
        return creditCardService.findByDocumentNumber(documentNumber);
    }
    
    //proy 3
    
    @GetMapping("/has-overdue-loans/{documentNumber}")
    public Mono<Boolean> hasOverdueLoans(@PathVariable String documentNumber) {
        return loanService.findByDocumentNumber(documentNumber)
                .filter(loan -> loan.getBalance().compareTo(BigDecimal.ZERO) > 0)
                .any(loan -> loan.getStartDate().isBefore(LocalDate.now().minusDays(30))); // deuda vencida si han pasado 30 días
    }
    
    @GetMapping("/has-overdue-credit-cards/{documentNumber}")
    public Mono<Boolean> hasOverdueCreditCards(@PathVariable String documentNumber) {
        return creditCardService.findByDocumentNumber(documentNumber)
                .filter(card -> card.getBalance().compareTo(BigDecimal.ZERO) > 0)
                .any(card -> card.getDueDate() != null && card.getDueDate().isBefore(LocalDate.now()));
    }
    
}
