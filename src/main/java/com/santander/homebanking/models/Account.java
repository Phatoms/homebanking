package com.santander.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String number;

    @Column(name = "creation_date")
    LocalDate creationDate;

    private Double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    Set<DebitCard> debitCards = new HashSet<>();

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    Set<DebitCardTransaction> debitCardTransactions = new HashSet<>();




    public Account(String number, LocalDate creationDate, Double balance) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public Account() {
    }

    public long getId() {
        return id;
    }

    @JsonIgnore()
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransactions(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
    }

    public Set<DebitCard> getDebitCards() {
        return debitCards;
    }

    public void addDebitCard(DebitCard debitCard){
        debitCard.setAccount(this);
        debitCards.add(debitCard);
    }

    public Set<DebitCardTransaction> getDebitCardTransactions() {
        return debitCardTransactions;
    }

    public void addDebitCardTransaction(DebitCardTransaction debitCardTransaction) {
        debitCardTransaction.addAccount(this);
        this.debitCardTransactions.add(debitCardTransaction);
    }

}
