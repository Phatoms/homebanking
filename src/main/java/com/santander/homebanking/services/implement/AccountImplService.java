package com.santander.homebanking.services.implement;

import com.santander.homebanking.dtos.AccountDTO;
import com.santander.homebanking.models.Account;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.repositories.AccountRepository;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.services.AccountService;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountImplService implements AccountService {

    private final int MAX_ACCOUNTS = 3;
    private final int CANT_DIGITS = 3;

    private ClientRepository clientRepository;

    private AccountRepository accountRepository;

    @Autowired
    public AccountImplService(ClientRepository clientRepository,
                              AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }



    public Set<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toSet());
    }

    public AccountDTO getAccount(Long id){
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    public AccountDTO getAccountByNumber(String number){
        return accountRepository.findByNumber(number).map(AccountDTO::new).orElse(null);
    }

    public Set<AccountDTO> getAccountByCreationDate(String creationDate) throws DateTimeParseException {
        LocalDate ld = LocalDate.parse(creationDate);
        return accountRepository.findByCreationDate(ld).stream().map(AccountDTO::new).collect(Collectors.toSet());
    }

    public Set<AccountDTO> getAccountByBalanceGreaterThan(Long balance){
        return accountRepository.findByBalanceGreaterThan(balance).stream().map(AccountDTO::new).collect(Collectors.toSet());
    }

    public Set<AccountDTO> getAccountByBalance(Long balance){
        return accountRepository.findByBalance(balance).stream().map(AccountDTO::new).collect(Collectors.toSet());
    }

    public Set<AccountDTO> getAccountByNumberInj(String number){
        return accountRepository.findByNumberInj(number).stream().map(AccountDTO::new).collect(Collectors.toSet());
    }

    public Set<AccountDTO> getCurrentAccounts(HttpSession session){
        return ((Client)session.getAttribute("client")).getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());
    }

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


    public AccountDTO getAccountByIdCurrentClient(Long id, Client client){

        if (client == null){
            return null;
        }

        List<Account> accounts = client.getAccounts().stream()
                .filter(account1 -> account1.getId() == id).collect(Collectors.toList());

        if (accounts.size() == 0){
            return null;
        }

        AccountDTO accountDTO = new AccountDTO(accounts.get(0));

        return accountDTO;
    }
}
