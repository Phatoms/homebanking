package com.santander.homebanking.dtos;

import com.santander.homebanking.models.Account;
import com.santander.homebanking.models.DebitCardTransaction;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private long id;

    private String number;

    LocalDate creationDate;

    private Double balance;

    private Set<TransactionDTO> transactions;

    private Set<DebitCardDTO> debitCardDTOS;

    private Set<DebitCardTransactionDTO> debitCardTransactionsDtos;


    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toSet());
        this.debitCardDTOS = account.getDebitCards().stream().map(DebitCardDTO::new).collect(Collectors.toSet());
        this.debitCardTransactionsDtos = account.getDebitCardTransactions().stream().map(DebitCardTransactionDTO::new).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public Set<DebitCardDTO> getDebitCardDTOS() {
        return debitCardDTOS;
    }

    public Set<DebitCardTransactionDTO> getDebitCardTransactionsDTOS() {
        return debitCardTransactionsDtos;
    }
}
