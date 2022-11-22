package com.santander.homebanking.services;


import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.dtos.FeesDTO;
import com.santander.homebanking.models.*;
import com.santander.homebanking.utils.CardUtils;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface CreditCardService {

    Optional<CreditCard> getSessionCreditCardById(Long cardId, HttpSession httpSession);

    ResponseUtils addCard(String cardColor, Double maxLimit,
                                 HttpSession session);

    private ResponseUtils validateCreditCard(String cardColor, Double maxLimit,
                                             HttpSession session) {
        return null;
    }


    /** En este método, nos encargamos de crear una nueva transacion de tarjeta de crédito
     *
     * /
     */
    ResponseUtils createPendingCreditCardTransaction(String cardHolder,
                                                            String cardNumber,
                                                            Double amount,
                                                            String description,
                                                            Integer payments,
                                                            Integer cvv,
                                                            String thruDate,
                                                            HttpSession session);

    ResponseUtils validateCreditCardTransaction(Long id, String token, HttpSession session);


    private ResponseUtils validateCreditCardData(String cardNumber,
                                                 String cardHolder,
                                                 Double amount,
                                                 Integer cvv,
                                                 String thruDate,
                                                 Integer payments,
                                                 HttpSession session) {
        return null;
    }

    HashMap<String, Double> getFees(FeesDTO feesDTO);

    void creditCardStatement();

    public CardDTO getCard();

    public void setCard(CardDTO card);
    public Client getClient();

    public void setClient(Client client);
    public CreditCard getClientCreditCard();

    public void setClientCreditCard(CreditCard clientCreditCard);

    public InterestRate getInterestRate();

    public void setInterestRate(InterestRate interestRate);
}
