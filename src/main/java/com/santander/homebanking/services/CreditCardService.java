package com.santander.homebanking.services;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.dtos.FeesDTO;
import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.repositories.CreditCardRepository;
import com.santander.homebanking.repositories.CreditCardTransactionRepository;
import com.santander.homebanking.repositories.InterestRateRepository;
import com.santander.homebanking.utils.CardUtils;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CreditCardService {

    @Autowired
    private CardService cardService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CreditCardTransactionRepository creditCardTransactionRepository;
    @Autowired
    private InterestRateRepository interestRateRepository;
    @Autowired
    private EmailSenderService senderService;
    @Autowired
    MessageSource messages;
    private CardDTO card;
    private Client client;

    private CreditCard clientCreditCard;
    private InterestRate interestRate;
    public ResponseUtils addCard(String cardColor, Double maxLimit,
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

    private ResponseUtils validateCreditCard(String cardColor, Double maxLimit,
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
                                                  Integer payments,
                                                  Integer cvv,
                                                  String thruDate,
                                                  HttpSession session){

        ResponseUtils res = validateCreditCardData(cardHolder, cardNumber, amount, cvv, thruDate, payments, session);

        if(!res.getDone()){
            return res;
        }

        String token = CardUtils.generateToken();

        CreditCardTransaction newCreditCardTransaction = new CreditCardTransaction(amount, description,
                LocalDateTime.now(), token, Status.PENDING, payments, interestRate.getInterestRate(),clientCreditCard);

        creditCardTransactionRepository.save(newCreditCardTransaction);

        res.setArgs(new String[]{String.valueOf(newCreditCardTransaction.getId())});

        try {
            senderService.sendEmailConfirmToken(client.getEmail(),
                    messages.getMessage("email.subject", null, LocaleContextHolder.getLocale()),
                    client.getFirstName(),
                    CardType.CREDIT.toString(),
                    token);
        } catch (MessagingException e){
            return new ResponseUtils(false, 500, "transaction.validation.failure.emailNotSent");
        }

        return res;
    }

    public ResponseUtils validateCreditCardTransaction(Long id, String token, HttpSession session){
        ResponseUtils res = new ResponseUtils(true, 200, "transaction.validation.success");

        CreditCardTransaction transaction = creditCardTransactionRepository.findById(id).orElse(null);

        if(transaction==null){
            return new ResponseUtils(false, 403, "pre-transaction.validation.failure.noExist");
        }

        if (transaction.getStatus().equals(Status.PASSED) || transaction.getStatus().equals(Status.REJECTED)){
            return new ResponseUtils(false, 403, "transaction.validation.failure.transaction-invalid");
        }

        if(!transaction.getToken().equals(token)){
            return new ResponseUtils(false, 403, "transaction.validation.failure.transaction-wrong-token");
        }

        transaction.setStatus(Status.PASSED);

        clientCreditCard.setAvailableLimit(clientCreditCard.getAvailableLimit() - transaction.getAmount());

        creditCardRepository.save(clientCreditCard);

        creditCardTransactionRepository.save(transaction);

        clientService.updateClientSession(session);

        return res;
    }



    private ResponseUtils validateCreditCardData(String cardNumber,
                                                String cardHolder,
                                                Double amount,
                                                Integer cvv,
                                                String thruDate,
                                                Integer payments,
                                                HttpSession session){

        ResponseUtils res = new ResponseUtils(true, 200, "card.pretransaction.validate.success");

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

        clientCreditCard = clientCreditCardsList.get(0);

        String thruDateCard = clientCreditCard.getThruDate().toString().substring(0,7);

        if(amount > clientCreditCard.getAvailableLimit()){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.limit-exceeded");
        }


        if(!(thruDateCard.equals(thruDate))){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.wrongThruDate");
        }

        if(clientCreditCard.getThruDate().isBefore(LocalDate.now())){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.cardExpired");
        }

        if(!cardHolder.equals(clientCreditCard.getCardHolder())){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.wrongOwner");
        }

        if(!cvv.equals(clientCreditCard.getCvv())){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.wrongCvv");
        }

        interestRate = interestRateRepository.findByFeeNumber(payments).orElse(null);
        if (interestRate == null){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.wrong-interest-rate");
        }


        return res;
    }

    //second, minute, hour, day of month, month, day(s) of week
    @Scheduled(cron = "1 * * * * *")
    public void chanceToReject(){
        // me traigo todas
        List<CreditCardTransaction> setTransactions = creditCardTransactionRepository.findAll();

        long timeWaiting = 1;

        // las recorro y veo si tienen pendiente, y paso mas de timeWaiting min... lo cambio a reject
        for (CreditCardTransaction transaction : setTransactions) {
            if(transaction.getStatus() == Status.PENDING){

                long minutes = Math.abs(ChronoUnit.MINUTES.between(LocalDateTime.now(), transaction.getTime()));

                if(minutes >= timeWaiting){
                    transaction.setStatus(Status.REJECTED);
                    creditCardTransactionRepository.save(transaction);
                }
            }
        }
    }


    public HashMap<String, Double> getFees(FeesDTO feesDTO){

        HashMap<String, Double> fees = new HashMap<>();

        fees.put(String.valueOf(feesDTO.getPayments()[0]), 2000.0);

        for(Integer payments : feesDTO.getPayments()){
            InterestRate interestRate = interestRateRepository.findByFeeNumber(payments).orElse(null);
            if (interestRate == null){
                return new HashMap<>();
            }

            Double amount = feesDTO.getAmount();

            Double fee = CardUtils.getFee(payments, interestRate, amount);
            fees.put(String.valueOf(payments), fee);
        }

        return fees;
    }

    //@Scheduled(cron = "1 * * * * *")
    public void creditCardStatement(){
        List<Client> clients = clientRepository.findAll();

        if (clients == null){
            System.out.println("salio mal");
            return;
        }

        for (Client c : clients){ // Por cada cliente
            Set<CreditCard> creditCards = c.getCreditCards();
            if (creditCards.size() != 0){
                for (CreditCard creditCard : creditCards) { // Por cada tarjeta de credito del cliente
                    Set<CreditCardTransaction> creditCardTransactions = creditCard.getTransactions();
                    if (creditCardTransactions.size() != 0){

                        Set<CreditCardTransaction> creditCardTransactionsPassed = creditCardTransactions.
                                stream().filter(cct -> cct.getStatus() == Status.PASSED).collect(Collectors.toSet());

                        if (creditCardTransactionsPassed.size() != 0){
                            try {
                                senderService.sendEmailCreditCardStatement(c.getEmail(),
                                        messages.getMessage("email.subject.credit-card-statement", null, LocaleContextHolder.getLocale()),
                                        c.getFirstName() + " " + c.getLastName(),
                                        creditCardTransactionsPassed);
                            } catch (MessagingException e){
                                e.printStackTrace();
                                return;
                            }
                        }
                        /*for (CreditCardTransaction creditCardTransaction : creditCardTransactions) { // Por cada transaccion de tarjeta de credito
                            if (creditCardTransaction.getStatus() == Status.PASSED) {
                                try {
                                    senderService.sendEmailCreditCardStatement(c.getEmail(),
                                            messages.getMessage("email.subject.credit-card-statement", null, LocaleContextHolder.getLocale()),
                                            c.getFirstName() + " " + c.getLastName());
                                } catch (MessagingException e){
                                    e.printStackTrace();
                                    return;
                                }

                            }
                        }*/
                    }
                }


            }
        }
    }


}
