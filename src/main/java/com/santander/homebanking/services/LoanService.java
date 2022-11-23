package com.santander.homebanking.services;

import com.santander.homebanking.dtos.ClientLoanDTO;
import com.santander.homebanking.dtos.LoanApplicationDTO;
import com.santander.homebanking.dtos.LoanDTO;
import com.santander.homebanking.models.Client;

import javax.servlet.http.HttpSession;
import java.util.Set;

public interface LoanService {

    Set<LoanDTO> getLoans();

    Set<LoanDTO> getLoansByNameStartingWith(String name);

    Set<LoanDTO> getLoansByMaxAmountLessThan(Double number);

    Set<ClientLoanDTO> getLoansCurrentClient(HttpSession session);

    Boolean newLoan(LoanApplicationDTO loanApplicationDTO, Client client);

    Boolean validateNewLoan(LoanApplicationDTO loanApplicationDTO, Client client);

}
