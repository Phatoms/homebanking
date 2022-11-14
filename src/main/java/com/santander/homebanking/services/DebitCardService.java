package com.santander.homebanking.services;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.AccountRepository;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.repositories.DebitCardRepository;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Set;
import java.util.stream.Collectors;

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

    private Account account;
    public ResponseUtils addCard(String cardColor, String accountNumber,
                                 HttpSession session) {

        ResponseUtils res = validateDebitCard(cardColor, accountNumber, session);

        if (!res.getDone()){
            return res;
        }

        DebitCard debitCard = new DebitCard(card.getCardHolder(), card.getNumber(), card.getCvv(),
                card.getFromDate(), card.getThruDate(), card.getColor(), card.getType(),card.getPin());


        debitCardRepository.save(debitCard);

        account.addDebitCard(debitCard);
        accountRepository.save(account);

        return res;
    }

    public ResponseUtils validateDebitCard(String cardColor, String accountNumber,
                                           HttpSession session){
        ResponseUtils res = new ResponseUtils(true, 200, "card.validation.success");
        card = cardService.newBasicCard(cardColor, CardType.DEBIT, session);

        if (card == null){
            return new ResponseUtils(false, 400, "card.validation.failure");
        }

        client = (Client) session.getAttribute("client");

        if (client == null){
            return new ResponseUtils(false, 400, "card.validation.failure");
        }

        Set<Account> accounts = client.getAccounts();

        if (accounts == null) {
            return new ResponseUtils(false, 400, "card.validation.failure");
        }

        account = accounts.stream().filter(account1 -> account1.getNumber().equals(accountNumber))
                .collect(Collectors.toList()).get(0);

        if (account == null){
            return new ResponseUtils(false, 400, "card.validation.failure");
        }

        return res;
    }
}
