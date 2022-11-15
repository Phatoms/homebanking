package com.santander.homebanking.services;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.repositories.CreditCardRepository;
import com.santander.homebanking.repositories.CreditCardTransactionRepository;
import com.santander.homebanking.utils.CardUtils;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private EmailSenderService senderService;

    @Autowired
    MessageSource messages;

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

    private ResponseUtils validateCreditCard(String cardColor, Long maxLimit,
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
                                                  String thruDate,
                                                  HttpSession session){
        //ResponseUtils res = new ResponseUtils(false, 403, "card.pretransaction.validate.success");

        //LocalDate localDate = LocalDate.parse(date.toString()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        ResponseUtils res = validateCreditCardData(cardHolder, cardNumber, amount, cvv, thruDate, session);


        LocalDateTime cctLocalTime = LocalDateTime.now();
        String cctToken = CardUtils.generateToken();
        Float interestRate = 0f;

        List<CreditCard> clientCreditCardsList = client.getCreditCards().stream().filter(card -> card.getNumber().equals(cardNumber)).collect(Collectors.toList());
        CreditCard clientCreditCard = clientCreditCardsList.get(0);

        if(!res.getDone()){
            return res;
        }

        CreditCardTransaction newCreditCardTransaction = new CreditCardTransaction(amount, description, cctLocalTime, cctToken, Status.PENDING, payments, interestRate,clientCreditCard);

        creditCardTransactionRepository.save(newCreditCardTransaction);

        res.setArgs(new String[]{String.valueOf(newCreditCardTransaction.getId())});

        try {
            senderService.sendEmail(client.getEmail(),
                    messages.getMessage("email.subject", null, LocaleContextHolder.getLocale()),
                    client.getFirstName(),
                    CardType.CREDIT.toString(),
                    cctToken);
        } catch (MessagingException e){
            return new ResponseUtils(false, 500, "transaction.validation.failure.emailNotSent");
        }

        return res;
    }

    public ResponseUtils validateCreditCardTransaction(Long id, String token){
        ResponseUtils res = new ResponseUtils(false, 500, "transaction.validation.failure.emailNotSent");

        CreditCardTransaction transaction = creditCardTransactionRepository.findById(id).orElse(null);

        if(transaction==null){
            return new ResponseUtils(false, 403, "transaction.validation.failure.noExist");
        }

        if(!transaction.getToken().equals(token)){
            return new ResponseUtils(false, 403, "transaction.validation.failure.invalidToken");
        }

        return res;
    }

    private ResponseUtils validateCreditCardData(String cardHolder,
                                                String cardNumber,
                                                Double amount,
                                                Integer cvv,
                                                String thruDate,
                                                HttpSession session){

        ResponseUtils res = new ResponseUtils(true, 200, "card.pretransaction.validate.success");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/yyyy");
        LocalDate formattedThruDate = (LocalDate) dateTimeFormatter.parse(thruDate);

        client = (Client) session.getAttribute("client");

        // Existe el cliente...
        if(client == null){
            return new ResponseUtils(false, 400, "client.session.validation.failure");
        }

        List<CreditCard> clientCreditCardsList = client.getCreditCards().stream().filter(card -> card.getNumber().equals(cardNumber)).collect(Collectors.toList());

        // Existe la cuenta en la lista del cliente...
        if(clientCreditCardsList.size() == 0){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.noCreditCards");
        }

        CreditCard clientCreditCard = clientCreditCardsList.get(0);
        LocalDate thruDateCard = LocalDate.parse(clientCreditCard.getThruDate().format(dateTimeFormatter));

        if(!cardHolder.equals(clientCreditCard.getCardHolder())){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.wrongOwner");
        }

        if(!cvv.equals(clientCreditCard.getCvv())){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.wrongCvv");
        }

        if(!(thruDateCard.isEqual(formattedThruDate))){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.wrongThruDate");
        }

        if(clientCreditCard.getThruDate().isBefore(LocalDate.now())){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.cardExpired");
        }

        return res;
    }
}
