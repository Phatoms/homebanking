package com.santander.homebanking.services.implement;

import com.santander.homebanking.dtos.TransactionDTO;
import com.santander.homebanking.models.Account;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.models.Transaction;
import com.santander.homebanking.models.TransactionType;
import com.santander.homebanking.repositories.AccountRepository;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.repositories.TransactionRepository;
import com.santander.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionImplService implements TransactionService {

    private ClientRepository clientRepository;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private Account accountTo;
    private Account accountFrom;
    private Client client;
    @Autowired
    public TransactionImplService(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }


    public Set<TransactionDTO> getTransactionByDescriptionOrAmount(String description,Long amount){
        return transactionRepository.findByDescriptionOrAmount(description, amount).stream().map(TransactionDTO::new).collect(Collectors.toSet());
    }

    public Set<TransactionDTO> getTransactionByTransactionType(String transactionType){
        return transactionRepository.findByType(TransactionType.valueOf(transactionType)).stream().map(TransactionDTO::new).collect(Collectors.toSet());
    }

    @Transactional
    public Boolean addTransactions(Double amount,
                                   String accountFromNumber,
                                   String accountToNumber,
                                   String description,
                                   String email){

        if (!validate(amount, accountFromNumber, accountToNumber, description, email)){
            return false;
        }

        Transaction transactionDebit = new Transaction(-amount, description + " - a " + accountTo.getNumber(),
                LocalDateTime.now(), TransactionType.DEBIT);
        transactionDebit.setAccount(accountFrom);

        Transaction transactionCredit = new Transaction(amount, description  + " - de " + accountFrom.getNumber(),
                LocalDateTime.now(), TransactionType.CREDIT);
        transactionCredit.setAccount(accountTo);

        transactionRepository.save(transactionDebit);
        transactionRepository.save(transactionCredit);

        accountFrom.setBalance(accountFrom.getBalance() - amount);
        accountTo.setBalance(accountTo.getBalance() - amount);

        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);

        return true;
    }

    private Boolean validate(Double amount,
                             String accountFromNumber,
                             String accountToNumber,
                             String description,
                             String email){

        Boolean result = false;

        if(accountFromNumber == accountToNumber){
            return result;
        }

        accountTo = accountRepository.findByNumber(accountToNumber).orElse(null);

        if (accountTo == null){
            return result;
        }

        accountFrom = accountRepository.findByNumber(accountFromNumber).orElse(null); // 7
        if (accountFrom == null){
            return result;
        }

        client = clientRepository.findByEmail(email).orElse(null);
        if (client == null){
            return result;
        }

        Set<Account> accounts = client.getAccounts().stream().filter(account -> account.getNumber().equals(accountFromNumber))
                .collect(Collectors.toSet());

        if (accounts.size() == 0){
            return result;
        }

        if(accountFrom.getBalance() < amount){
            return result;
        }

        result = true;
        return result;
    }

}
