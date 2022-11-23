package com.santander.homebanking;

import com.santander.homebanking.dtos.ClientDTO;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.repositories.AccountRepository;
import com.santander.homebanking.repositories.CardRepository;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.services.implement.ClientImplService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ClientImplServiceTests {

    ClientRepository clientRepository = mock(ClientRepository.class);
    AccountRepository accountRepository = mock(AccountRepository.class);
    CardRepository cardRepository = mock(CardRepository.class);
    HttpSession session = mock(HttpSession.class);

    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    ClientImplService clientImplService = new ClientImplService(clientRepository, accountRepository,
            cardRepository, passwordEncoder);

    List<Client> clients = Arrays.asList(
            new Client("tomas", "quinteros", "tomas.quinteros351@gmail.com", "123"),
            new Client("admin", "admin", "admin@admin", "admin")
    );

    @Test
    public void getClientByEmailTest(){
        when(clientRepository.findByEmail("tomas.quinteros35@gmail.com")).thenReturn(Optional.of(clients.get(0)));
        ClientDTO clientTomas = clientImplService.getClientByEmail("tomas.quinteros35@gmail.com");
        Assert.assertEquals(clients.get(0).getEmail(), clientTomas.getEmail());
    }

    @Test
    public void getCurrentTest(){
        when(session.getAttribute("client")).thenReturn(clients.get(0));
        when(clientRepository.findByEmail(clients.get(0).getEmail())).thenReturn(Optional.of(clients.get(0)));
        ClientDTO client = clientImplService.getCurrent(session);

        assertThat(client, is(not(nullValue())));
        assertThat(client.getEmail(), is("tomas.quinteros351@gmail.com"));
        assertThat(client.getFirstName(), is("tomas"));
        assertThat(client.getLastName(), is("quinteros"));
    }

}
