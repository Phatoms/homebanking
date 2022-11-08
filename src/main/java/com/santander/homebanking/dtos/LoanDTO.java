package com.santander.homebanking.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.santander.homebanking.models.ClientLoan;
import com.santander.homebanking.models.Loan;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LoanDTO {

    private Long Id;

    private String name;

    private Double maxAmount;

    private List<Integer> payments = new ArrayList<>();

    public LoanDTO(Loan loan) {
        Id = loan.getId();
        name = loan.getName();
        maxAmount = loan.getMaxAmount();
        payments = loan.getPayments();
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }
/*
    @JsonIgnore
    public Set<ClientLoanDTO> getClientLoans() {
        return clientLoans;
    }*/
}
