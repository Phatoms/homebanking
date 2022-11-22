package com.santander.homebanking;

import com.santander.homebanking.dtos.FeesDTO;
import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.CreditCardRepository;
import com.santander.homebanking.repositories.CreditCardTransactionRepository;
import com.santander.homebanking.repositories.InterestRateRepository;
import com.santander.homebanking.services.CreditCardService;
import com.santander.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CreditCardServiceTests {

    @MockBean
    InterestRateRepository interestRateRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @MockBean
    CreditCardTransactionRepository creditCardTransactionRepository;

    @Test
    public void getFeesTest(){

        FeesDTO feesDTO = new FeesDTO(20000.0, new Integer[]{1,6,12});

        HashMap<String, Double> fees = new HashMap<>();

        when(interestRateRepository.findByFeeNumber(1)).thenReturn(Optional.of(new InterestRate(1, 	0.005)));
        when(interestRateRepository.findByFeeNumber(6)).thenReturn(Optional.of(new InterestRate(6, 0.03)));
        when(interestRateRepository.findByFeeNumber(12)).thenReturn(Optional.of(new InterestRate(12, 0.06)));


        for(Integer payments : feesDTO.getPayments()){
            InterestRate interestRate = interestRateRepository.findByFeeNumber(payments).orElse(null);
            assertThat(interestRate, is(not(nullValue())));
            Double amount = feesDTO.getAmount();
            Double fee = CardUtils.getFee(payments, interestRate, amount);
            fees.put(String.valueOf(payments), fee);
        }

        assertThat(fees, is(not(nullValue())));

    }

    @Autowired
    CreditCardService creditCardService;

/*    @Test
    public void validateCreditCardTransactionTest(){
        Client client = new Client("Tomas", "Quinteros", "tomas.quinteros35@gmail.com", "123");
        CreditCard creditCard = new CreditCard();
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("client")).thenReturn(client);
        when(creditCardTransactionRepository.findById(1L)).thenReturn(new CreditCardTransaction(20000.0,
                "test", LocalDateTime.now(), "123456", Status.PENDING, "12", "0.06", new CreditCard()))
        creditCardService.validateCreditCardTransaction(1L, "123456", session);
        *//*

        when(countryRepository.findAll()).thenReturn(Collections.emptyList());

        assertThat(countryService.findAll()).isEmpty();
        verify(countryRepository, times(1)).findAll();


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

         *//*
    }*/

    @Test
    public void createCreditCardTransactionTest(){
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(new CreditCard("Tomas Quinteros", "1111-2222-3333-4444", 123, LocalDate.parse("2022-09-08"), LocalDate.parse("2027-09-08"), CardColor.TITANIUM, CardType.CREDIT, "1234", 200000.0, 200000.0)));

        CreditCard creditCard = creditCardRepository.findById(1L).orElse(null);
        CreditCardTransaction creditCardTransaction = new CreditCardTransaction(100000.0, "Televisor Samsung 50 pulgadas", LocalDateTime.now(),
                "123456", Status.PENDING, 12, 0.12, creditCard);

        assertThat(creditCardTransaction.getCreditCard().getCardHolder(), is("Tomas Quinteros"));
        assertThat(creditCardTransaction.getCreditCard().getNumber(), is("1111-2222-3333-4444"));
        assertThat(creditCardTransaction.getAmount(), is(100000));
        assertThat(creditCardTransaction.getStatus(), is(Status.PENDING));

    }


}
