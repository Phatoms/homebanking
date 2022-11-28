package com.santander.homebanking;

import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IntegrationJpaTest {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientLoansRepository clientLoansRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    DebitCardRepository debitCardRepository;

    @Autowired
    CreditCardTransactionRepository  creditCardTransactionRepository;

    @Autowired
    DebitCardTransactionRepository debitCardTransactionRepository;


    @MockBean
    PasswordEncoder passwordEncoder;


    @Test
    void testFindAllClients(){
        List<Client> clients = clientRepository.findAll();
        assertFalse(clients.isEmpty());
    }

    @Test
    void testFindClientById() {
        Optional<Client> client = clientRepository.findById(1L);
        assertTrue(client.isPresent());
        assertEquals("tomas", client.orElseThrow().getFirstName());
    }

    @Test
    void testFindAccountAll(){
        List<Account> accounts = accountRepository.findAll();
        assertFalse(accounts.isEmpty());
    }

    @Test
    void testFindAccountById(){
        Optional<Account> account = accountRepository.findById(1L);
        assertTrue(account.isPresent());
        assertEquals("VIN005", account.orElseThrow().getNumber());
    }

    @Test
    void testFindAllTransaction(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertFalse(transactions.isEmpty());
    }

    @Test
    void testFindTransactionById(){
        Optional<Transaction> transaction = transactionRepository.findById(1L);
        assertTrue(transaction.isPresent());
        assertEquals(TransactionType.CREDIT, transaction.orElseThrow().getType());
    }

    @Test
    void testFindAllLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertFalse(loans.isEmpty());
    }

    @Test
    void testFindLoanById(){
        Optional<Loan> loan = loanRepository.findById(1L);
        assertTrue(loan.isPresent());
        assertEquals("Hipotecario", loan.orElseThrow().getName());
    }

    @Test
    void testFindAllClientLoans(){
        List<ClientLoan> clientLoans = clientLoansRepository.findAll();
        assertFalse(clientLoans.isEmpty());
    }

    @Test
    void testFindClientLoanById(){
        Optional<ClientLoan> clientLoan = clientLoansRepository.findById(1L);
        assertTrue(clientLoan.isPresent());
        assertEquals(400000, clientLoan.orElseThrow().getAmount());
        assertEquals(60, clientLoan.orElseThrow().getPayments());
    }

    @Test
    void testFindAllCreditCards(){
        List<CreditCard> creditCards = creditCardRepository.findAll();
        assertFalse(creditCards.isEmpty());
    }

    @Test
    void testFindAllDebitCards(){
        List<DebitCard> debitCards = debitCardRepository.findAll();
        assertFalse(debitCards.isEmpty());
    }

    @Test
    void testFindCreditCardById(){
        Optional<CreditCard> card = creditCardRepository.findById(1L);
        assertTrue(card.isPresent());
        assertEquals("Tomas Quinteros", card.orElseThrow().getCardHolder());
        assertEquals("1111-2222-3333-4444", card.orElseThrow().getNumber());
        assertEquals(123, card.orElseThrow().getCvv());
        assertEquals(CardColor.TITANIUM, card.orElseThrow().getColor());
        assertEquals(CardType.CREDIT, card.orElseThrow().getType());
        assertEquals(LocalDate.parse("2022-09-08"), card.orElseThrow().getFromDate());
        assertEquals(LocalDate.parse("2027-09-08"), card.orElseThrow().getThruDate());
    }

    @Test
    void testFindDebitCardById(){
        Optional<DebitCard> card = debitCardRepository.findById(1L);
        assertTrue(card.isPresent());
        assertEquals("Tomas Quinteros", card.orElseThrow().getCardHolder());
        assertEquals("2222-3333-4444-5555", card.orElseThrow().getNumber());
        assertEquals(123, card.orElseThrow().getCvv());
        assertEquals(CardColor.GOLD, card.orElseThrow().getColor());
        assertEquals(CardType.DEBIT, card.orElseThrow().getType());
        assertEquals(LocalDate.parse("2022-09-08"), card.orElseThrow().getFromDate());
        assertEquals(LocalDate.parse("2027-09-08"), card.orElseThrow().getThruDate());
    }

    @Test
    void testClientSave(){
        Client data = new Client("Pepe", "Grillo", "pepe@gmail.com", "123");
        Client client = clientRepository.save(data);

        assertEquals("Pepe", client.getFirstName());
        assertEquals("Grillo", client.getLastName());
        assertEquals("pepe@gmail.com", client.getEmail());
        assertEquals("123", client.getPassword());
    }

    @Test
    void testAccountSave(){
        Client client = clientRepository.findById(1L).get();
        Account data = new Account("VIN005", LocalDate.now(), 5000.0);
        data.setClient(client);
        Account account = accountRepository.save(data);

        assertEquals("VIN005", account.getNumber());
        assertEquals(5000, account.getBalance());
        assertEquals("tomas", account.getClient().getFirstName());
    }

    @Test
    void testFindAllCreditTransaction(){
        List<CreditCardTransaction> creditCardTransactions = creditCardTransactionRepository.findAll();
        assertFalse(creditCardTransactions.isEmpty());
    }

    @Test
    void testFindCreditById(){
        Optional<CreditCardTransaction> creditCardTransactions = creditCardTransactionRepository.findById(1L);
        assertTrue(creditCardTransactions.isPresent());
        assertEquals("1111-2222-3333-4444", creditCardTransactions.orElseThrow().getCreditCard().getNumber());
    }

    @Test
    void testFindAllDebitCardTransactions(){
        List<DebitCardTransaction> debitCardTransactions = debitCardTransactionRepository.findAll();
        assertFalse(debitCardTransactions.isEmpty());
    }

    @Test
    void testFindDebitCardTransactionsById(){
        Optional<DebitCardTransaction> debitCardTransactions = debitCardTransactionRepository.findById(1L);
        assertTrue(debitCardTransactions.isPresent());
        assertEquals("VIN005", debitCardTransactions.orElseThrow().getAccount().getNumber());
    }


}
