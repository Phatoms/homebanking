package com.santander.homebanking.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    Set<Account> accounts = new HashSet<>();

/*    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();*/

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<DebitCard> debitCards = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<CreditCard> creditCards = new HashSet<>();

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;

    public Client() { }

    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public String toString() {
        return firstName + " " + lastName;
    }

    public void addAccounts(Account account){
        account.setClient(this);
        accounts.add(account);
    }

    @JsonIgnore
    public Set<Loan> getLoans() {
        return clientLoans.stream().map(loan -> loan.getLoan()).collect(Collectors.toSet());
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void addClientLoans(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }

    public Set<Card> getCards() {
        Set<Card> cards = new HashSet<>();
        cards.addAll(debitCards);
        cards.addAll(creditCards);

        return cards;
    }

/*    public void addCards(Card card) {
        card.setClient(this);
        cards.add(card);
    }*/

    public Set<DebitCard> getDebitCards() {
        return debitCards;
    }

    public void addDebitCards(DebitCard debitCard) {
        debitCard.setClient(this);
        debitCards.add(debitCard);
    }

    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void addCreditCards(CreditCard creditCard) {
        creditCard.setClient(this);
        creditCards.add(creditCard);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}