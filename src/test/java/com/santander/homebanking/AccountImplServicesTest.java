package com.santander.homebanking;

import com.santander.homebanking.dtos.AccountDTO;
import com.santander.homebanking.models.Account;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.repositories.AccountRepository;
import com.santander.homebanking.repositories.CardRepository;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.services.implement.AccountImplService;
import com.santander.homebanking.services.implement.ClientImplService;
import com.santander.homebanking.utils.ResponseUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
        }
        /*
            public ResponseUtils addAccount(Client client){
        ResponseUtils response = new ResponseUtils(true, 201, "account.validation.success");


        if (client.getAccounts().size() >= MAX_ACCOUNTS){

            response = new ResponseUtils(false, 400, "account.validation.failure.maxAccounts",
                    new String[]{String.valueOf(MAX_ACCOUNTS)});

            return response;
        }

        String accountNumber = "VIN-" + new Random().nextInt((int)(Math.pow(10, CANT_DIGITS)));
        Account account = new Account(accountNumber, LocalDate.now(), 0.0);
        client.addAccounts(account);
        accountRepository.save(account);

        return response;
    }
*/

}
