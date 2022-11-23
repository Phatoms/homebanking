package com.santander.homebanking;

import com.santander.homebanking.dtos.AccountDTO;
import com.santander.homebanking.models.Account;
import com.santander.homebanking.models.CardColor;
import com.santander.homebanking.models.CardType;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.repositories.AccountRepository;
import com.santander.homebanking.repositories.CardRepository;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.services.implement.AccountImplService;
import com.santander.homebanking.services.implement.ClientImplService;
import com.santander.homebanking.utils.ResponseUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountImplServicesTest{

        ClientRepository clientRepository = mock(ClientRepository.class);
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountImplService accountImplService = new AccountImplService(clientRepository, accountRepository);

        List<Client> clients = Arrays.asList(
                new Client("tomas", "quinteros", "tomas.quinteros35@gmail.com", "123"),
                new Client("admin", "admin", "admin@admin", "admin")
        );

        List<Account> accounts = Arrays.asList(
                new Account("VIN005", LocalDate.parse("2022-09-08"), 100000.0),
                new Account("VIN006", LocalDate.parse("2022-09-10"), 40000.0)
        );

        @Before
        public void inicData(){
                clients.get(0).addAccounts(accounts.get(0));
                clients.get(1).addAccounts(accounts.get(1));
        }


        @Test
        public void getAccounts(){
                when(accountRepository.findAll()).thenReturn(accounts);
                Set<AccountDTO> accountDTOSet = accountImplService.getAccounts();
                Assert.assertEquals(accounts.size(), accountDTOSet.size());
        }

        @Test
        public void addAccountTest(){
               // when(accountRepository.save(*)).thenReturn(new Account());

                ResponseUtils testResponse = accountImplService.addAccount(clients.get(0));

                assertThat(testResponse.getDone(), is(true));
                assertThat(testResponse.getMessage(),is("account.validation.success"));
                assertThat(testResponse.getStatusCode(), is(201));
                assertThat(clients.get(0).getAccounts().size(), is(2));
        }

}
