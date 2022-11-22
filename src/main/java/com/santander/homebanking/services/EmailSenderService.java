package com.santander.homebanking.services;

import com.santander.homebanking.models.CreditCardTransaction;

import javax.mail.MessagingException;
import java.util.Set;

public interface EmailSenderService {
    void sendEmailConfirmToken(String toEmail,
                                      String subject,
                                      String user,
                                      String cardType,
                                      String token) throws MessagingException;

    void sendEmailCreditCardStatement(String toEmail,
                                             String subject,
                                             String name,
                                             Set<CreditCardTransaction> creditCardTransactions) throws MessagingException;



}
