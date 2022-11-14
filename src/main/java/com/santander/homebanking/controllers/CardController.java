package com.santander.homebanking.controllers;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.dtos.CardSimpleDTO;
import com.santander.homebanking.models.CardType;
import com.santander.homebanking.repositories.CardRepository;
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

import javax.validation.constraints.NotBlank;
import java.util.Set;

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

    @PostMapping(value = "/clients/current/cards")
    public ResponseEntity<Object> addCard(@RequestParam @NotBlank String cardColor, @RequestParam @NotBlank String cardType,
                                          Authentication authentication) {
        ResponseUtils res = new ResponseUtils(false, 400, "card.type.failure");

        try {
            if (CardType.valueOf(cardType).equals(CardType.CREDIT)){
                res = creditCardService.addCard(cardColor, cardType, authentication);
            } else if (CardType.valueOf(cardType).equals(CardType.CREDIT)){
                res = debitCardService.addCard(cardColor, cardType, authentication);
            }
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(
                    messages.getMessage(res.getMessage(), res.getArgs(), LocaleContextHolder.getLocale()),
                    HttpStatus.valueOf(res.getStatusCode()));
        }

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

/*    @PostMapping(value = "/cards/payment")
    public ResponseEntity<Object> addPayment(@RequestParam @NotBlank String cardColor, @RequestParam @NotBlank String cardType,
                                          Authentication authentication) {

        if (cardService.addCard(cardColor, cardType, authentication)){ // servicio.agregarpago();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }*/


}
