package com.santander.homebanking.services;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.AccountRepository;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.repositories.DebitCardRepository;
import com.santander.homebanking.repositories.DebitCardTransactionRepository;
import com.santander.homebanking.utils.CardUtils;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Locale;
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

    @Autowired
    private DebitCardTransactionRepository debitCardTransactionRepository;

    @Autowired
    private EmailSenderService senderService;

    @Autowired
    private MessageSource messages;

    private CardDTO card;

    private Client client;

    private Account account;

    /* Pre transaction */
    private Set<DebitCard> debitCardsFromClient;

    private DebitCard debitCardToDebit;

    private Account accountToDebit;
    /*      ------     */

    /* Transaction */
    private DebitCardTransaction transaction;

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


    public ResponseUtils createPreTransaction(String numberCardDebit,
                                           String carhHolder,
                                           String description,
                                           Double amount,
                                           Integer cvv,
                                           String thruDate,
                                           HttpSession session){

        ResponseUtils res = validatePreTransactionDebit(numberCardDebit,carhHolder, amount, cvv, thruDate, session);

        if (!res.getDone()){
            return res;
        }

        String token = CardUtils.generateToken();

        DebitCardTransaction transaction = new DebitCardTransaction(amount, description, LocalDateTime.now(), token, Status.PENDING, accountToDebit);

        debitCardTransactionRepository.save(transaction);

        res.setArgs(new String[]{String.valueOf(transaction.getId())});

        try {
            senderService.sendEmail(client.getEmail(),
                    messages.getMessage("email.subject", null, LocaleContextHolder.getLocale()),
                    client.getFirstName(),
                    CardType.DEBIT.toString(),
                    token);
        } catch (MessagingException e){
            return new ResponseUtils(false, 500, "pre-transaction.validation.failure.emailNotSent");
        }


        return res;
    }

    public ResponseUtils validatePreTransactionDebit(String numberCardDebit,
                                                     String carhHolder,
                                                     Double amount,
                                                     Integer cvv,
                                                     String thruDate,
                                                     HttpSession session){

        ResponseUtils res = new ResponseUtils(true, 200, "card.pretransaction.validate.success");

        client = (Client) session.getAttribute("client");

        if(client == null){
            return new ResponseUtils(false, 400, "client.session.validation.failure");
        }

        debitCardsFromClient = client.getDebitCards();

        if (debitCardsFromClient.size() == 0){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.noDebitCards");
        }

        debitCardToDebit = debitCardsFromClient.stream().filter(debitCard -> debitCard.getNumber().equals(numberCardDebit)).collect(Collectors.toList()).get(0);

        if(!carhHolder.equals(debitCardToDebit.getCardHolder())){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.wrongOwner");
        }

        if(!cvv.equals(debitCardToDebit.getCvv())){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.wrongCvv");
        }


        String thruDateCard = debitCardToDebit.getThruDate().toString().substring(0,7);

        if(!(thruDateCard.equals(thruDate))){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.wrongThruDate");
        }


        if(debitCardToDebit.getThruDate().isBefore(LocalDate.now())){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.cardExpired");
        }

        Set<Account> setAccountClient = client.getAccounts();

        if(setAccountClient.size() == 0){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.noAccounts");
        }

        accountToDebit = setAccountClient.stream().filter(account1 -> account1.getDebitCards().contains(debitCardToDebit)).collect(Collectors.toList()).get(0);

        if(amount > accountToDebit.getBalance()){
            return new ResponseUtils(false, 400, "pre-transaction.validation.failure.insufficentMoney");
        }

        return res;
    }


    public ResponseUtils confirmTransaction(Long id,
                                            String token,
                                            HttpSession session){

        ResponseUtils res = validateConfirmTransaction(id, token, session);

        if(!res.getDone()){
            return res;
        }

        Account accounToDebit = transaction.getAccount();
        accounToDebit.setBalance(accounToDebit.getBalance() - transaction.getAmount());
        transaction.setStatus(Status.PASSED);

        accountRepository.save(accounToDebit);

        Client oldClient = (Client) session.getAttribute("client");
        Client client = clientRepository.findByEmail(oldClient.getEmail()).orElse(null);
        session.setAttribute("client", client);

        return res;
    }

    private ResponseUtils validateConfirmTransaction(Long id,
                                                     String token,
                                                     HttpSession session){

        transaction = debitCardTransactionRepository.findById(id).orElse(null);

        if(transaction == null){
            return new ResponseUtils(false, 400, "transaction.validation.failure.transaction-missing");
        }

        if (transaction.getStatus().equals(Status.PASSED) || transaction.getStatus().equals(Status.REJECTED)){
            return new ResponseUtils(false, 403, "transaction.validation.failure.transaction-invalid");
        }

        if(!(transaction.getToken().equals(token))){
            return new ResponseUtils(false, 400, "transaction.validation.failure.transaction-wrong-token");
        }


        return new ResponseUtils(true, 200, "transaction.validation.success");
    }



}
