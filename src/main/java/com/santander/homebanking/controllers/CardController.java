package com.santander.homebanking.controllers;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.dtos.CardSimpleDTO;
import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.AccountRepository;
import com.santander.homebanking.repositories.CardRepository;
import com.santander.homebanking.repositories.ClientRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Validated
public class CardController {

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
    
    
    ///Metodo para la recepcion de pagos usando tarjetas de credito
    @PostMapping(value = "/clients/current/creditCards/pay")
    public ResponseEntity<Object> newCreditCardTransaction(@RequestParam String cardNumberCredit,
                                                           @RequestParam String cardHolder,
                                                           @RequestParam String description,
                                                           @RequestParam Double amount,
                                                           @RequestParam Integer cvv,
                                                           @RequestParam String thruDate,
                                                           HttpSession session){


        ResponseUtils res = creditCardService.createPendingCreditCardTransaction(cardNumberCredit, cardHolder, amount, description,
                6, cvv, thruDate, session);

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

    //Confrimamos el pago de la tarjeta de credito

    public ResponseEntity<Object> validateCreditCardTransaction(@RequestParam Long id, @RequestParam String token){
        ResponseUtils res = creditCardService.validateCreditCardTransaction(id, token);

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


    // Pagar con debito, pasar cvv, pin   pre transaccion return id de la transaccion
    @PostMapping(value = "/clients/current/debitCards/pagar")
    public ResponseEntity<Object> preTransactionDebit(@RequestParam  String numberCardDebit,
                                                      @RequestParam  String carhHolder,
                                                      @RequestParam  String description,
                                                      @RequestParam  Double amount,
                                                      @RequestParam  Integer cvv,
                                                      @RequestParam String thruDate,
                                                      HttpSession session){

        ResponseUtils res = debitCardService.createPreTransaction(numberCardDebit,carhHolder, description, amount, cvv, thruDate, session);

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
    @PostMapping(value = "/clients/current/debitCards/confirm")
    public ResponseEntity<Object> confirmTransaction(@RequestParam Long id,
                                                     @RequestParam String token,
                                                     HttpSession session){

        ResponseUtils res = debitCardService.confirmTransaction(id, token, session);

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
}
