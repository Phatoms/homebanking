package com.santander.homebanking.dtos;

import com.santander.homebanking.models.Account;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.models.Loan;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    private Set<AccountDTO> accounts;

    private Set<ClientLoanDTO> loans;

    private Set<CardDTO> cards;


    public ClientDTO(Client client) {
        this.id = client.getId();

        this.firstName = client.getFirstName();

        this.lastName = client.getLastName();

        this.email = client.getEmail();

        this.accounts = client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());

        this.loans = client.getClientLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toSet());

/*        this.cards = client.getCards().stream().map(CardDTO::new).collect(Collectors.toSet());*/
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public void setAccounts(Set<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }

}
