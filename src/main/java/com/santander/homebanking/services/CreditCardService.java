package com.santander.homebanking.services;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.repositories.CreditCardRepository;
import com.santander.homebanking.repositories.CreditCardTransactionRepository;
import com.santander.homebanking.utils.CardUtils;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CreditCardService {

    @Autowired
    private CardService cardService;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CreditCardTransactionRepository creditCardTransactionRepository;

    private CardDTO card;

    private Client client;
    public ResponseUtils addCard(String cardColor, Long maxLimit,
                                 HttpSession session) {

        ResponseUtils res = validateCreditCard(cardColor, maxLimit, session);

        if (!res.getDone()){
            return res;
        }

        CreditCard creditCard = new CreditCard(card.getCardHolder(), card.getNumber(), card.getCvv(),
                card.getFromDate(), card.getThruDate(), card.getColor(), card.getType(),card.getPin(),
                maxLimit, maxLimit);


        creditCardRepository.save(creditCard);
        client.addCreditCards(creditCard);

        clientRepository.save(client);
        return res;
    }

    public ResponseUtils validateCreditCard(String cardColor, Long maxLimit,
                                            HttpSession session){
        ResponseUtils res = new ResponseUtils(true, 200, "card.validation.success");
        card = cardService.newBasicCard(cardColor, CardType.CREDIT, session);

        if (card == null){
            return new ResponseUtils(false, 400, "card.validation.failure");
        }

        client = (Client)session.getAttribute("client");

        if(client == null){
            return new ResponseUtils(false, 400, "card.validation.failure");
        }

        return res;
    }



    /** En este método, nos encargamos de crear una nueva transacion de tarjeta de crédito
     *
     * /
     */
    public ResponseUtils createPendingCreditCardTransaction(String cardHolder,
                                                  String cardNumber,
                                                  Double amount,
                                                  String description,
                                                  int payments,
                                                  Integer cvv,
                                                  HttpSession session)
    {
        //ResponseUtils res = new ResponseUtils(false, 403, "card.pretransaction.validate.success");
        ResponseUtils res = validateCreditCardData(cardHolder, cardNumber, amount, cvv, session);
        LocalDateTime cctLocalTime = LocalDateTime.now();
        String cctToken = CardUtils.generateToken();
        Float interesRate = 0f;

        List<CreditCard> clientCreditCardsList = client.getCreditCards().stream().filter(card -> card.getNumber().equals(cardNumber)).collect(Collectors.toList());
        CreditCard clientCreditCard = clientCreditCardsList.get(0);




        if(!res.getDone()){
            return res;
        }
//        CreditCardTransaction newCreditCardTransaction = new CreditCardTransaction(amount, description, cctLocalTime,
//                cctToken, Status.PENDING, interesRate,clientCreditCard);



        return res;
    }


    private ResponseUtils validateCreditCardData(String cardHolder,
                                                String cardNumber,
                                                Double amount,
                                                Integer cvv,
                                                HttpSession session){
        ResponseUtils res = new ResponseUtils(false, 403, "card.pretransaction.validate.success");
        client = (Client) session.getAttribute("client");

        // Existe el cliente...
        if(client == null){
            return res; // return El cliente no existe en sesion
        }

        List<CreditCard> clientCreditCardsList = client.getCreditCards().stream().filter(card -> card.getNumber().equals(cardNumber)).collect(Collectors.toList());

        // Existe la cuenta en la lista del cliente...
        if(clientCreditCardsList.size()!=1){
            return res; // return el cliente no cuenta con ese numero de cuenta, o el cliente excede
        }

        CreditCard clientCreditCard = clientCreditCardsList.get(0);

//        // El monto a gastar es menor al monto disponible.
//        if(amount > accoutToDebit.getBalance()){
//            return false; // return No dispone de fondos suficientes.
//        }

//        Set<CreditCard> clientCreditCards = client.getCreditCards();
//        CreditCard creditCardToDebit = ((List<CreditCard>) clientCreditCards.stream().filter(creditCard -> creditCard.)).get(0);

        if(!cardHolder.equals(clientCreditCard.getCardHolder())){
            return res; // return El nombre no corresponde a la tarjeta
        }

        if(cvv.equals(clientCreditCard.getCvv())){
            return res; // Return El CVV no corresponde a la tarjeta...
        }

        res = new ResponseUtils(true, 200, "card.pretransaction.validate.success");
        return res;
    }
}
