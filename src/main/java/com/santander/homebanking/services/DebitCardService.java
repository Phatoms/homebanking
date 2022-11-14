package com.santander.homebanking.services;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.models.Account;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.models.CreditCard;
import com.santander.homebanking.models.DebitCard;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.repositories.CreditCardRepository;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import java.util.Set;

public class DebitCardService {

    @Autowired
    private CardService cardService;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private ClientRepository clientRepository;

    private CardDTO card;

    private Client client;

    private Set<Account> accounts;
    public ResponseUtils addCard(String cardColor, String cardType,
                                 Authentication authentication) {

        ResponseUtils res = validateCreditCard(cardColor, cardType, authentication);

        if (!res.getDone()){
            return res;
        }

//        DebitCard creditCard = new DebitCard(card.getCardHolder(), card.getNumber(), card.getCvv(),
//                card.getFromDate(), card.getThruDate(), card.getColor(), card.getType(),card.getPin());


//        creditCardRepository.save(creditCard);
//        client.addCreditCards(creditCard);
//
//        clientRepository.save(client);


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

        if (client == null){
            return new ResponseUtils(false, 400, "card.validation.failure");
        }

        accounts = client.getAccounts();

        if (accounts == null) {
            return new ResponseUtils(false, 400, "card.validation.failure");
        }


        return res;
    }


}
