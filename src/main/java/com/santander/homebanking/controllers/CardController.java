package com.santander.homebanking.controllers;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.dtos.CardSimpleDTO;
import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.CardRepository;
import com.santander.homebanking.repositories.DebitCardTransactionRepository;
import com.santander.homebanking.services.CardService;
import com.santander.homebanking.services.CreditCardService;
import com.santander.homebanking.services.DebitCardService;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Validated
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardService cardService;

    @Autowired
    private MessageSource messages;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private DebitCardService debitCardService;

    @Autowired
    private  DebitCardTransactionRepository debitCardTransactionRepository;


    @GetMapping(value = "/cards/card-holder/{cardHolder}")
    public Set<CardDTO> getCardHolders(@PathVariable String cardHolder){
        return cardService.getCardHolders(cardHolder);
    }

    @GetMapping(value = "/cards/{number}")
    public Set<CardDTO> getCardByNumber(@PathVariable String number){
        return cardService.getCardByNumber(number);
    }

    @GetMapping(value = "/cards/cardType/{cardType}")
    public Set<CardDTO> getCardByCardType(@PathVariable String cardType){
        try {
            return cardService.getCardByCardType(cardType);
        }catch (IllegalArgumentException e){
            return null;
        }
    }

/*    @GetMapping(value = "/cards")
    public Set<CardDTO> findAllCards(){
        return cardService.findAllCards();
    }

    @GetMapping(value = "/cards-string")
    public Set<String> findAllCardsString(){
        return cardService.findAllCardsString();
    }

    @GetMapping(value = "/cards-dto")
    public Set<CardSimpleDTO> findAllCardsDTO(){
        return cardService.findAllCardsDTO();
    }*/

    @PostMapping(value = "/clients/current/creditCards")
    public ResponseEntity<Object> addCreditCards(@RequestParam @NotBlank String cardColor,
                                                 @RequestParam Long maxLimit,
                                                 HttpSession session) {
        ResponseUtils res = creditCardService.addCard(cardColor, maxLimit, session);

        if (res.getDone()){
            return new ResponseEntity<>(
                    messages.getMessage(res.getMessage(), null, LocaleContextHolder.getLocale()),
                    HttpStatus.valueOf(res.getStatusCode()));
        } else{
            return new ResponseEntity<>(
                    messages.getMessage(res.getMessage(), res.getArgs(), LocaleContextHolder.getLocale()),
                    HttpStatus.valueOf(res.getStatusCode()));
        }
    }

    @PostMapping(value = "/clients/current/debitCards")
    public ResponseEntity<Object> addDebitCards(@RequestParam @NotBlank String cardColor,
                                                @RequestParam @NotBlank String accountNumber,
                                                HttpSession session) {
        ResponseUtils res = debitCardService.addCard(cardColor, accountNumber, session);

        if (res.getDone()){
            return new ResponseEntity<>(
                    messages.getMessage(res.getMessage(), null, LocaleContextHolder.getLocale()),
                    HttpStatus.valueOf(res.getStatusCode()));
        } else{
            return new ResponseEntity<>(
                    messages.getMessage(res.getMessage(), res.getArgs(), LocaleContextHolder.getLocale()),
                    HttpStatus.valueOf(res.getStatusCode()));
        }
    }

    // Pagar con debito, pasar cvv, pin   pre transaccion return id de la transaccion
    @PostMapping(value = "/clients/current/debitCards/pagar")
    public ResponseEntity<Object> preTransactionDebit(@RequestParam  String numberCardDebit,
                                                      @RequestParam  String carhHolder,
                                                      @RequestParam  String description,
                                                      @RequestParam  Double amount,
                                                      @RequestParam  Integer cvv,
                                                      HttpSession session){

        ResponseUtils res = debitCardService.createTransaction(numberCardDebit,carhHolder, description, amount, cvv, session);

        if (res.getDone()){
            return new ResponseEntity<>(
                    res.getArgs()[0],
                    HttpStatus.valueOf(res.getStatusCode()));
        } else{
            return new ResponseEntity<>(
                    messages.getMessage(res.getMessage(), res.getArgs(), LocaleContextHolder.getLocale()),
                    HttpStatus.valueOf(res.getStatusCode()));
        }

    }
    @PatchMapping(value = "/clients/current/debitCards/{id}")
    public ResponseEntity<Object> confirmTransaction(@PathVariable Long id){
        DebitCardTransaction transaction = debitCardTransactionRepository.findById(id).orElse(null);


        if(transaction == null){
            return new ResponseEntity<>("Ups ocurrio un error, no se encuentra la transaccion", HttpStatus.FORBIDDEN);
        }
        // Si encontro la transaccion, se completa restando a la cuenta el monto...
        Account accounToDebit = transaction.getAccount();
        accounToDebit.setBalance(accounToDebit.getBalance() - transaction.getAmount());
        transaction.setStatus(Status.PASSED);

        return new ResponseEntity<>("Transaccion confirmada.", HttpStatus.ACCEPTED);
    }

}
