package com.santander.homebanking.services;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.models.Account;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.models.CreditCard;
import com.santander.homebanking.models.DebitCard;
import com.santander.homebanking.repositories.AccountRepository;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.repositories.CreditCardRepository;
import com.santander.homebanking.repositories.DebitCardRepository;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DebitCardService {

    @Autowired
    private CardService cardService;

    @Autowired
    private DebitCardRepository debitCardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    private CardDTO card;

    private Client client;

    private Set<Account> accounts;
    public ResponseUtils addCard(String cardColor, String cardType,
                                 Authentication authentication) {

        ResponseUtils res = validateCreditCard(cardColor, cardType, authentication);

        if (!res.getDone()){
            return res;
        }

        DebitCard debitCard = new DebitCard(card.getCardHolder(), card.getNumber(), card.getCvv(),
                card.getFromDate(), card.getThruDate(), card.getColor(), card.getType(),card.getPin());


/*
        debitCardRepository.save(debitCard);
        client.addDebitCards(debitCard);
        accounts.addDebitCards(debitCard);

        clientRepository.save(client);
        accountRepository.save(accounts);
*/

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
