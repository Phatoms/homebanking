package com.santander.homebanking;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.CardRepository;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.repositories.CreditCardTransactionRepository;
import com.santander.homebanking.repositories.DebitCardTransactionRepository;
import com.santander.homebanking.services.implement.CardImplService;
import com.santander.homebanking.utils.ResponseUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class CardImplServiceTest {

    HttpSession session = mock(HttpSession.class);
    private CardRepository cardRepository = mock(CardRepository.class);
    private ClientRepository clientRepository = mock(ClientRepository.class);
    private CreditCardTransactionRepository creditCardTransactionRepository = mock(CreditCardTransactionRepository.class);
    private DebitCardTransactionRepository debitCardTransactionRepository = mock(DebitCardTransactionRepository.class);

    List<Client> clients = Arrays.asList(
            new Client("tomas", "quinteros", "tomas.quinteros35@gmail.com", "123")
    );
    List<Card> cards = Arrays.asList(
            new CreditCard("Tomas Quinteros", "1111-2222-3333-4444", 123, LocalDate.parse("2022-09-08"), LocalDate.parse("2027-09-08"), CardColor.TITANIUM, CardType.CREDIT, "1234", 200000.0, 200000.0),
            new DebitCard("Tomas Quinteros", "2222-3333-4444-5555", 123, LocalDate.parse("2022-09-08"), LocalDate.parse("2027-09-08"), CardColor.GOLD, CardType.DEBIT, "1234")
    );


    CardImplService cardImplService = spy(new CardImplService(cardRepository, clientRepository, creditCardTransactionRepository, debitCardTransactionRepository));

    @Before
    public void initData(){
        clients.get(0).addCreditCards((CreditCard) cards.get(0));
        cardImplService.setClient(clients.get(0));
    }

    @Test
    public void newBasicCardTest(){
        doReturn(new ResponseUtils(true, 201, "card.validation.success")).when(cardImplService).
                validateCard("GOLD", CardType.DEBIT, session);

        CardDTO testCardDTO = cardImplService.newBasicCard("GOLD", CardType.DEBIT, session);

        assertThat(testCardDTO.getCardHolder(), is("tomas quinteros"));
        assertThat(testCardDTO.getColor(), is(CardColor.GOLD));
        assertThat(testCardDTO.getType(), is(CardType.DEBIT));
    }

    @Test
    public void validateCardTest(){
        when(session.getAttribute("client")).thenReturn(clients.get(0));
        ResponseUtils res = cardImplService.validateCard("GOLD", CardType.DEBIT, session);

        assertThat(res.getDone(), is(true));
        assertThat(res.getMessage(), is("card.validation.success"));
    }
}
