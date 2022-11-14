package com.santander.homebanking.services;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.repositories.CreditCardRepository;
import com.santander.homebanking.utils.CardUtils;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CreditCardService {

    @Autowired
    private CardService cardService;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private ClientRepository clientRepository;

    private CardDTO card;

    private Client client;
    public ResponseUtils addCard(String cardColor, String cardType,
                                 Authentication authentication) {

        ResponseUtils res = validateCreditCard(cardColor, cardType, authentication);

        if (!res.getDone()){
            return res;
        }

        CreditCard creditCard = new CreditCard(card.getCardHolder(), card.getNumber(), card.getCvv(),
                card.getFromDate(), card.getThruDate(), card.getColor(), card.getType(),card.getPin(),
                200000L, 200000L);


        creditCardRepository.save(creditCard);
        client.addCreditCards(creditCard);

        clientRepository.save(client);


        return res;
    }

    public ResponseUtils validateCreditCard(String cardColor, String cardType,
                                            Authentication authentication){
        ResponseUtils res = new ResponseUtils(true, 200, "card.validation.success");
        card = cardService.newBasicCard(cardColor, cardType, authentication);

        if (card == null){
            return new ResponseUtils(false, 400, "card.validation.failure");
        }

        client = card.getClient();

        return res;
    }


}
