package com.santander.homebanking.controllers;

import com.santander.homebanking.dtos.*;
import com.santander.homebanking.repositories.DebitCardTransactionRepository;
import com.santander.homebanking.services.implement.CardImplService;
import com.santander.homebanking.services.implement.CreditCardImplService;
import com.santander.homebanking.services.implement.DebitCardImplService;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping("/api")
@Validated
public class CardController {

    @Autowired
    private CardImplService cardImplService;

    @Autowired
    private MessageSource messages;

    @Autowired
    private CreditCardImplService creditCardImplService;

    @Autowired
    private DebitCardImplService debitCardImplService;

    @Autowired
    private  DebitCardTransactionRepository debitCardTransactionRepository;


    @GetMapping(value = "/cards/card-holder/{cardHolder}")
    public Set<CardDTO> getCardHolders(@PathVariable String cardHolder){
        return cardImplService.getCardHolders(cardHolder);
    }

    @GetMapping(value = "/cards/{number}")
    public Set<CardDTO> getCardByNumber(@PathVariable String number){
        return cardImplService.getCardByNumber(number);
    }

    @GetMapping(value = "/cards/cardType/{cardType}")
    public Set<CardDTO> getCardByCardType(@PathVariable String cardType){
        try {
            return cardImplService.getCardByCardType(cardType);
        }catch (IllegalArgumentException e){
            return null;
        }
    }

    @PostMapping(value = "/clients/current/creditCards")
    public ResponseEntity<Object> addCreditCards(@RequestParam @NotBlank String cardColor,
                                                 @RequestParam Double maxLimit,
                                                 HttpSession session) {
        ResponseUtils res = creditCardImplService.addCard(cardColor, maxLimit, session);

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
        ResponseUtils res = debitCardImplService.addCard(cardColor, accountNumber, session);

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
                                                           @RequestParam Integer payments,
                                                           @RequestParam String thruDate,
                                                           HttpSession session){


        ResponseUtils res = creditCardImplService.createPendingCreditCardTransaction(cardNumberCredit, cardHolder, amount, description,
                payments, cvv, thruDate, session);

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

    @PostMapping(value = "/clients/current/creditCards/confirm")
    public ResponseEntity<Object> confirmCreditCardTransaction(@RequestParam Long id, @RequestParam String token,
                                                                HttpSession session){
        ResponseUtils res = creditCardImplService.validateCreditCardTransaction(id, token, session);

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

    @PostMapping(value = "/clients/current/debitCards/pay")
    public ResponseEntity<Object> newDebitCardTransaction(@RequestParam  String numberCardDebit,
                                                          @RequestParam  String cardHolder,
                                                          @RequestParam  String description,
                                                          @RequestParam  Double amount,
                                                          @RequestParam  Integer cvv,
                                                          @RequestParam  String thruDate,
                                                          HttpSession session){

        ResponseUtils res = debitCardImplService.createPreTransaction(numberCardDebit,cardHolder, description, amount, cvv, thruDate, session);

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
    public ResponseEntity<Object> confirmDebitCardTransaction(@RequestParam Long id,
                                                               @RequestParam String token,
                                                               HttpSession   session){

        ResponseUtils res = debitCardImplService.confirmTransaction(id, token, session);

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

    @PostMapping(value = "/clients/current/creditCards/fees")
    public HashMap<String, Double> getFees(@RequestBody FeesDTO feesDTO){

        HashMap<String, Double> fees = creditCardImplService.getFees(feesDTO);

        return fees;
    }

    @GetMapping(value = "/clients/current/creditCards/{id}")
    public CreditCardWTransactionsDTO getCreditCardTransactions(@PathVariable long id, HttpSession session){

        return creditCardImplService.getSessionCreditCardById(id, session).map(CreditCardWTransactionsDTO::new).orElse(null);
    }








}
