package com.santander.homebanking.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.models.ClientLoan;
import com.santander.homebanking.models.Loan;

public class ClientLoanDTO {

    private Long id;

    private Long loanID;

    private String name;

    private Double amount;

    private Integer payments;

    private LoanDTO loanDTO;



    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.loanDTO = new LoanDTO(clientLoan.getLoan());
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public Long getLoanID() {
        return loanDTO.getId();
    }

    public String getName() {
        return loanDTO.getName();
    }
}
