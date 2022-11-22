package com.santander.homebanking;

import static org.mockito.Mockito.*;

import com.santander.homebanking.dtos.AccountDTO;
import com.santander.homebanking.dtos.ClientDTO;
import com.santander.homebanking.models.Account;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.repositories.AccountRepository;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.services.implement.AccountImplService;
import com.santander.homebanking.services.implement.ClientImplService;
import com.santander.homebanking.utils.ResponseUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountImplServicesTest{

        ClientRepository clientRepository = mock(ClientRepository.class);
        AccountRepository accountRepository = mock(AccountRepository.class);

        ClientImplService clientImplService = new ClientImplService(clientRepository);
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
                for (Client client: clients) {
                        clientRepository.save(client);
                }

                clients.get(0).addAccounts(accounts.get(0));
                clients.get(1).addAccounts(accounts.get(1));

                for (Account account: accounts) {
                        accountRepository.save(account);
                }

                System.out.println(clientRepository.findAll());
                System.out.println(accountRepository.findAll());
        }


        Set<AccountDTO> getAccounts(){
                when(accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toSet()))
                        .thenReturn(Optional.of(accounts.stream().collect(Collectors.toSet())));

                Set<AccountDTO> accountDTOSet = accountImplService.getAccounts();

                Assert.assertEquals(accounts.size(), accountDTOSet.size());
        };



        @Test
        public void getClientByEmailTest(){
                when(clientRepository.findByEmail("tomas.quinteros35@gmail.com")).thenReturn(Optional.of(clients.get(0)));
                ClientDTO clientTomas = clientImplService.getClientByEmail("tomas.quinteros35@gmail.com");
                Assert.assertEquals(clients.get(0).getEmail(), clientTomas.getEmail());
        }


//        AccountDTO getAccount(Long id);
//
//        AccountDTO getAccountByNumber(String number);
//
//        Set<AccountDTO> getAccountByCreationDate(String creationDate);
//
//        Set<AccountDTO> getAccountByBalanceGreaterThan(Long balance);
//
//        Set<AccountDTO> getAccountByBalance(Long balance);
//
//        Set<AccountDTO> getAccountByNumberInj(String number);
//
//        Set<AccountDTO> getCurrentAccounts(HttpSession session);
//
//        ResponseUtils addAccount(Client client);
//
//        AccountDTO getAccountByIdCurrentClient(Long id, Client client);


}
