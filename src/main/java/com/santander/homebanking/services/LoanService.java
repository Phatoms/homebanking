package com.santander.homebanking.services;

import com.santander.homebanking.dtos.ClientLoanDTO;
import com.santander.homebanking.dtos.LoanApplicationDTO;
import com.santander.homebanking.dtos.LoanDTO;
import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.AccountRepository;
import com.santander.homebanking.repositories.ClientLoansRepository;
import com.santander.homebanking.repositories.LoanRepository;
import com.santander.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientLoansRepository clientLoansRepository;
    private Loan loan;
    private Account account;

    public Set<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toSet());
    }

    public Set<LoanDTO> getLoansByNameStartingWith(String name) {
        return loanRepository.findByNameStartingWith(name).stream().map(LoanDTO::new).collect(Collectors.toSet());
    }

    public Set<LoanDTO> getLoansByMaxAmountLessThan(Double number) {
        return loanRepository.findByMaxAmountLessThanEqual(number).stream().map(LoanDTO::new).collect(Collectors.toSet());
    }

    public Set<ClientLoanDTO> getLoansCurrentClient(HttpSession session){
        Client client = (Client) session.getAttribute("client");
        if (client == null){
            return null;
        }
        Set<ClientLoanDTO> clientLoanDTOS = client.getClientLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toSet());
        return clientLoanDTOS;
    }

    @Transactional
    public Boolean newLoan(LoanApplicationDTO loanApplicationDTO, Client client){
        if (!validateNewLoan(loanApplicationDTO, client)){
            return false;
        }

        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount() * 1.2,
                loanApplicationDTO.getPayments(),
                client,
                loan);

        Transaction transaction = new Transaction(loanApplicationDTO.getAmount() * 1.2, loan.getName() + " loan approved.",
                LocalDateTime.now(), TransactionType.CREDIT);

        account.addTransactions(transaction);
        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        client.addClientLoans(clientLoan);

        clientLoansRepository.save(clientLoan);
        accountRepository.save(account);
        transactionRepository.save(transaction);

        return true;
    }


    private Boolean validateNewLoan(LoanApplicationDTO loanApplicationDTO, Client client){
        Boolean result = false;
        loan = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);
        List<Loan> loans = client.getLoans().stream()
                .filter(loan1 -> loan1.getId().equals(loanApplicationDTO.getLoanId())).collect(Collectors.toList());
        //Si existe el prestamo
        if (loan == null){
            return result;
        }

        //Si el monto no excede el maximo
        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()){
            return result;
        }

        //Si la cantidad de cuotas está entre las permitidas
        if (loan.getPayments().stream()
                .filter(payment -> loanApplicationDTO.getPayments().equals(payment))
                .count() == 0){
            return result;
        }

        //Si la cuenta de destino existe
        List<Account> accounts = client.getAccounts().stream()
                .filter(account1 -> account1.getNumber().equals(loanApplicationDTO.getToAccountNumber())).collect(Collectors.toList());
        if (accounts.size() == 0){
            return result;
        }

        account = accounts.get(0);

        //Si la cuenta de destino pertenece al cliente autenticado
        if(client.getAccounts().stream()
                .filter(currentClientAccount -> currentClientAccount.getNumber().equals(account.getNumber())).count() == 0){
            return result;
        }

        return true;
    }
}
