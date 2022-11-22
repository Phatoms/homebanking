package com.santander.homebanking.services;

import com.santander.homebanking.dtos.TransactionDTO;

import java.util.Set;
import java.util.stream.Collectors;

public interface TransactionService {
    Set<TransactionDTO> getTransactionByDescriptionOrAmount(String description, Long amount);

    Set<TransactionDTO> getTransactionByTransactionType(String transactionType);

    Boolean addTransactions(Double amount,
                                   String accountFromNumber,
                                   String accountToNumber,
                                   String description,
                                   String email);

    private Boolean validate(Double amount,
                             String accountFromNumber,
                             String accountToNumber,
                             String description,
                             String email) {
        return null;
    }


}
