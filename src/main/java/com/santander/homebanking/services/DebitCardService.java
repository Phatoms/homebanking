package com.santander.homebanking.services;

import com.santander.homebanking.models.*;
import com.santander.homebanking.utils.CardUtils;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface DebitCardService {
    ResponseUtils addCard(String cardColor, String accountNumber,
                                 HttpSession session);
    ResponseUtils validateDebitCard(String cardColor, String accountNumber,
                                           HttpSession session);
    ResponseUtils createPreTransaction(String numberCardDebit,
                                              String carhHolder,
                                              String description,
                                              Double amount,
                                              Integer cvv,
                                              String thruDate,
                                              HttpSession session);
    ResponseUtils validatePreTransactionDebit(String numberCardDebit,
                                                     String carhHolder,
                                                     Double amount,
                                                     Integer cvv,
                                                     String thruDate,
                                                     HttpSession session);


    ResponseUtils confirmTransaction(Long id,
                                            String token,
                                            HttpSession session);

    private ResponseUtils validateConfirmTransaction(Long id,
                                                     String token,
                                                     HttpSession session) {
        return null;
    }


}
