package com.santander.homebanking;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.dtos.FeesDTO;
import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.repositories.CreditCardRepository;
import com.santander.homebanking.repositories.CreditCardTransactionRepository;
import com.santander.homebanking.repositories.InterestRateRepository;
import com.santander.homebanking.services.*;
import com.santander.homebanking.services.implement.CardImplService;
import com.santander.homebanking.services.implement.ClientImplService;
import com.santander.homebanking.services.implement.CreditCardImplService;
import com.santander.homebanking.services.implement.EmailSenderImplService;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.Test;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class CreditCardImplServiceTests {


    @Test
    public void getFeesTest() {

        InterestRateRepository interestRateRepository = mock(InterestRateRepository.class);
        CardService cardService = mock(CardImplService.class);
        ClientService clientService = mock(ClientImplService.class);
        CreditCardRepository creditCardRepository = mock(CreditCardRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CreditCardTransactionRepository creditCardTransactionRepository = mock(CreditCardTransactionRepository.class);
        EmailSenderService emailSenderService = mock(EmailSenderImplService.class);

        when(interestRateRepository.findByFeeNumber(1)).thenReturn(Optional.of(new InterestRate(1, 0.005)));
        when(interestRateRepository.findByFeeNumber(6)).thenReturn(Optional.of(new InterestRate(6, 0.03)));
        when(interestRateRepository.findByFeeNumber(12)).thenReturn(Optional.of(new InterestRate(12, 0.06)));

        CreditCardService creditCardService = new CreditCardImplService(cardService,
                clientService,
                creditCardRepository,
                clientRepository,
                creditCardTransactionRepository,
                interestRateRepository,
                emailSenderService
                );


        FeesDTO feesDTO = new FeesDTO(20000.0, new Integer[]{1, 6, 12});

        HashMap<String, Double> fees = creditCardService.getFees(feesDTO);

        assertThat(fees, is(not(nullValue())));
        assertThat(fees, is(not(anEmptyMap())));
    }


    @Test
    public void validateCreditCardTransactionTest(){
        InterestRateRepository interestRateRepository = mock(InterestRateRepository.class);
        CardService cardService = mock(CardImplService.class);
        ClientService clientService = mock(ClientImplService.class);
        CreditCardRepository creditCardRepository = mock(CreditCardRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CreditCardTransactionRepository creditCardTransactionRepository = mock(CreditCardTransactionRepository.class);
        EmailSenderService emailSenderService = mock(EmailSenderImplService.class);


        CreditCardService creditCardService = new CreditCardImplService(cardService,
                clientService,
                creditCardRepository,
                clientRepository,
                creditCardTransactionRepository,
                interestRateRepository,
                emailSenderService
        );

        creditCardService.setCard(new CardDTO(1L,"Tomas Quinteros", "1111-2222-3333-4444", 123, LocalDate.parse("2022-09-08"), LocalDate.parse("2027-09-08"), CardColor.TITANIUM, CardType.CREDIT, "1234"));
        creditCardService.setClientCreditCard(new CreditCard("Tomas Quinteros", "1111-2222-3333-4444", 123, LocalDate.parse("2022-09-08"), LocalDate.parse("2027-09-08"), CardColor.TITANIUM, CardType.CREDIT, "1234", 200000.0, 200000.0));
        creditCardService.setInterestRate(new InterestRate(12, 0.06));
        HttpSession session = mock(HttpSession.class);

        CreditCard creditCard1 = new CreditCard("Tomas Quinteros", "1111-2222-3333-4444", 123, LocalDate.parse("2022-09-08"), LocalDate.parse("2027-09-08"), CardColor.TITANIUM, CardType.CREDIT, "1234", 200000.0, 200000.0);

        CreditCardTransaction transaction = new CreditCardTransaction(100000.0,
                "Televisor Samsung 50 pulgadas", LocalDateTime.now(),
                "123456", Status.PENDING, 12, 0.05, creditCard1);

        when(creditCardTransactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        when(creditCardRepository.save(creditCardService.getClientCreditCard())).thenReturn(null);
        when(creditCardTransactionRepository.save(transaction)).thenReturn(null);
        doNothing().when(clientService).updateClientSession(session);

        ResponseUtils res = creditCardService.validateCreditCardTransaction(1L, "123456", session);

        assertThat(res.getDone(), is(true));
        assertThat(transaction.getStatus(), is(Status.PASSED));

    }

}