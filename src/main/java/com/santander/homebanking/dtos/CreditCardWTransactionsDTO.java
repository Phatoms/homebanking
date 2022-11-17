package com.santander.homebanking.dtos;

import com.santander.homebanking.models.CreditCard;
import com.santander.homebanking.models.CreditCardTransaction;
import com.santander.homebanking.models.Status;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CreditCardWTransactionsDTO extends CreditCardDTO{

    public Set<CreditCardTransactionDTO> creditCardTransactions = new HashSet<>();

    public CreditCardWTransactionsDTO(CreditCard creditCard) {
        super(creditCard);
        this.creditCardTransactions = creditCard.getTransactions().stream().filter(creditCardTransaction ->
                creditCardTransaction.getStatus() == Status.PASSED).map(CreditCardTransactionDTO::new).collect(Collectors.toSet());
    }

    public Set<CreditCardTransactionDTO> getCreditCardTransactions() {
        return creditCardTransactions;
    }
}
