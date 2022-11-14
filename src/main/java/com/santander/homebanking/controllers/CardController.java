package com.santander.homebanking.controllers;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.dtos.CardSimpleDTO;
import com.santander.homebanking.repositories.CardRepository;
import com.santander.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
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
    CardRepository cardRepository;

    @Autowired
    CardService cardService;

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

        if (cardService.addCard(cardColor, cardType, authentication)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/cards/payment")
    public ResponseEntity<Object> addPayment(@RequestParam @NotBlank String cardColor, @RequestParam @NotBlank String cardType,
                                          Authentication authentication) {

        if (cardService.addCard(cardColor, cardType, authentication)){ // servicio.agregarpago();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


}
