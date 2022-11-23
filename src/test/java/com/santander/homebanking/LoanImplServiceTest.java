package com.santander.homebanking;


import com.santander.homebanking.dtos.AccountDTO;
import com.santander.homebanking.dtos.ClientLoanDTO;
import com.santander.homebanking.dtos.LoanApplicationDTO;
import com.santander.homebanking.dtos.LoanDTO;
import com.santander.homebanking.models.Account;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.models.ClientLoan;
import com.santander.homebanking.models.Loan;
import com.santander.homebanking.repositories.AccountRepository;
import com.santander.homebanking.repositories.ClientLoansRepository;
import com.santander.homebanking.repositories.LoanRepository;
import com.santander.homebanking.repositories.TransactionRepository;
import com.santander.homebanking.services.implement.LoanImplService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class LoanImplServiceTest {

    private LoanRepository loanRepository = mock(LoanRepository.class);
    private AccountRepository accountRepository = mock(AccountRepository.class);
    private TransactionRepository transactionRepository = mock(TransactionRepository.class);
    private ClientLoansRepository clientLoansRepository = mock(ClientLoansRepository.class);
    private LoanImplService loanImplService= new LoanImplService(loanRepository, accountRepository, transactionRepository, clientLoansRepository);

    List<Loan> loans = Arrays.asList(
        new Loan("Hipotecario", 500000.0),
        new Loan("Personal", 100000.0),
        new Loan("Automotriz", 300000.0)
    );

    Client clientTest = new Client("tomas", "quinteros", "tomas.quinteros35@gmail.com", "123");
    Account accountTest = new Account("VIN-005", LocalDate.now(), 20000.0);
    LoanApplicationDTO loanApplicationDTOTest = new LoanApplicationDTO(1L, 20000.0, 12, "VIN-005");

    @Before
    public void initDataTest(){
        loans.get(0).setId(1L);
        loans.get(1).setId(1L);
        loans.get(2).setId(1L);
        loans.get(0).addPayments(12);
        loans.get(0).addPayments(24);
        loans.get(0).addPayments(36);
        loans.get(0).addPayments(48);
        loans.get(0).addPayments(60);

        loans.get(1).addPayments(6);
        loans.get(1).addPayments(12);
        loans.get(1).addPayments(24);

        loans.get(2).addPayments(6);
        loans.get(2).addPayments(12);
        loans.get(2).addPayments(24);
        loans.get(2).addPayments(36);
        System.out.println("before");

        loanApplicationDTOTest.setLoanId(1L);
        clientTest.addAccounts(accountTest);
        ClientLoan clientLoan1 = new ClientLoan(50000.0, 3, clientTest, loans.get(0));
        ClientLoan clientLoan2 = new ClientLoan(20000.0, 6, clientTest, loans.get(1));
        clientTest.addClientLoans(clientLoan1);
        clientTest.addClientLoans(clientLoan2);
    }


    @Test
    public void getLoansTest(){
        when(loanRepository.findAll()).thenReturn(loans);
        Set<LoanDTO> loanDTOS = loanImplService.getLoans();
        Assertions.assertEquals(loanDTOS.size(), loans.size());
    }

    @Test
    public void validateNewLoanTest(){

        when(loanRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(loans.get(0)));

        boolean result = loanImplService.validateNewLoan(loanApplicationDTOTest, clientTest);
        System.out.println(clientTest);
        assertThat(result, is(true));
    }

}
