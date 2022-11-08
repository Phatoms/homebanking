package com.santander.homebanking.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoanApplicationDTO {

    @NotNull
    private Long loanId;
    @NotNull
    @Min(1)
    private Double amount;
    @NotNull
    @Min(1)
    private Integer payments;
    @NotNull
    @NotBlank
    @Size(min = 6)
    private String toAccountNumber;

    public LoanApplicationDTO(Long id, Double amount, Integer payments, String number) {
        this.loanId = id;
        this.amount = amount;
        this.payments = payments;
        this.toAccountNumber = number;
    }

    public Long getLoanId() {
        return loanId;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }
}
