package com.santander.homebanking;

import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.*;
import com.santander.homebanking.services.CreditCardService;
import com.santander.homebanking.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@SpringBootApplication
@EnableScheduling
public class HomebankingApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	CreditCardService creditCardService;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  CardRepository cardRepository,
									  CreditCardRepository creditCardRepository,
									  DebitCardRepository debitCardRepository,
									  AccountRepository accountRepository,
									  LoanRepository loanRepository,
									  ClientLoansRepository clientLoansRepository,
									  TransactionRepository transactionRepository,
									  InterestRateRepository interestRateRepository,
									  CreditCardTransactionRepository creditCardTransactionRepository
	){
		return (args) -> {

//			Client client1 = new Client("tomas", "quinteros", "tomas.quinteros35@gmail.com", passwordEncoder.encode("123"));
			Client client1 = new Client("tomas", "quinteros", "juanpedro4288@gmail.com", passwordEncoder.encode("123"));
			Client client2 = new Client("jose", "perez", "jose@mindhub.com", passwordEncoder.encode("password2"));

			Client client3 = new Client("admin", "admin", "admin@admin", passwordEncoder.encode("admin"));
			Client client4 = new Client("antonio", "fumero", "aefumero@gmail.com", passwordEncoder.encode("123"));

			Account account1 = new Account("VIN005", LocalDate.parse("2022-09-08"), 100000.0);
			Account account2 = new Account("VIN006", LocalDate.parse("2022-09-10"), 40000.0);
			Account account3 = new Account("VIN007", LocalDate.parse("2022-09-08"), 12000.0);
			Account account4 = new Account("VIN008", LocalDate.parse("2022-09-08"), 36000.0);

			LocalDate localDate1 = LocalDate.parse("2022-09-08");
			LocalDateTime localDateTime1 = localDate1.atStartOfDay();
			Transaction t1 = new Transaction(2000.0, "Transferencia recibida", localDateTime1, TransactionType.CREDIT);
			Transaction t2 = new Transaction(4000.0, "Compra tienda xx", localDateTime1, TransactionType.DEBIT);
			Transaction t3 = new Transaction(1000.0, "Transferencia recibida", localDateTime1, TransactionType.CREDIT);
			Transaction t4 = new Transaction(200.0, "Compra tienda xx", localDateTime1, TransactionType.DEBIT);
			Transaction t5 = new Transaction(8000.0, "Transferencia recibida", localDateTime1, TransactionType.CREDIT);
			Transaction t6 = new Transaction(2000.0, "Compra tienda xx", localDateTime1, TransactionType.DEBIT);
			Transaction t7 = new Transaction(700.0, "Transferencia recibida", localDateTime1, TransactionType.CREDIT);
			Transaction t8 = new Transaction(2000.0, "Compra tienda xx", localDateTime1, TransactionType.DEBIT);

			account1.addTransactions(t1); // transactions
			account1.addTransactions(t2); // transactions
			account2.addTransactions(t3); // transactions
			account2.addTransactions(t4); // transactions
			account3.addTransactions(t5); // transactions
			account3.addTransactions(t6); // transactions
			account4.addTransactions(t7); // transactions
			account4.addTransactions(t8); // transactions

			account1.setClient(client1);
			account2.setClient(client1);
			account3.setClient(client3);
			account4.setClient(client3);

			client1.addAccounts(account1);
			client1.addAccounts(account2);
			client3.addAccounts(account3);
			client3.addAccounts(account3);
			client3.addAccounts(account4);

			Loan loan1 = new Loan("Hipotecario", 500000.0);
			Loan loan2 = new Loan("Personal", 100000.0);
			Loan loan3 = new Loan("Automotriz", 300000.0);

			loan1.addPayments(12);
			loan1.addPayments(24);
			loan1.addPayments(36);
			loan1.addPayments(48);
			loan1.addPayments(60);

			loan2.addPayments(6);
			loan2.addPayments(12);
			loan2.addPayments(24);

			loan3.addPayments(6);
			loan3.addPayments(12);
			loan3.addPayments(24);
			loan3.addPayments(36);

			ClientLoan clientLoan1 = new ClientLoan(400000.0, 60, client1, loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000.0, 12, client1, loan2);
			ClientLoan clientLoan3 = new ClientLoan(100000.0, 24, client3, loan2);
			ClientLoan clientLoan4 = new ClientLoan(200000.0, 36, client3, loan3);



			/// Modulo de prueba con card1
			CreditCard creditCard1 = new CreditCard("Tomas Quinteros", "1111-2222-3333-4444", 123, LocalDate.parse("2022-09-08"), LocalDate.parse("2027-09-08"), CardColor.TITANIUM, CardType.CREDIT, "1234", 200000.0, 200000.0);
			CreditCard creditCard2 = new CreditCard("Tomas Quinteros", "1111-2222-3333-4444", 123, LocalDate.parse("2022-09-08"), LocalDate.parse("2027-09-08"), CardColor.TITANIUM, CardType.CREDIT, "1234", 200000.0, 200000.0);

			client1.addCreditCard(creditCard1);
			client4.addCreditCard(creditCard2);

			CreditCard creditCard3 = new CreditCard("Juan Pedro", "2111-2222-3333-4444", 163, LocalDate.parse("2022-09-08"), LocalDate.parse("2027-09-08"), CardColor.TITANIUM, CardType.CREDIT, "1234", 200000.0, 200000.0);
			client2.addCreditCard(creditCard3);

			DebitCard debitCard = new DebitCard("Tomas Quinteros", "2222-3333-4444-5555", 123, LocalDate.parse("2022-09-08"), LocalDate.parse("2027-09-08"), CardColor.GOLD, CardType.DEBIT, "1234");
			account1.addDebitCard(debitCard);




			Double baseInterestRate = 0.0;
			for (int i = 1; i < 25; i++){
				baseInterestRate += 0.005;
				baseInterestRate = new BigDecimal(baseInterestRate).setScale(2, RoundingMode.HALF_UP).doubleValue();
				InterestRate interestRate = new InterestRate(i, baseInterestRate);
				interestRateRepository.save(interestRate);
			}

			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);
			clientRepository.save(client4);


			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			creditCardRepository.save(creditCard1);
			creditCardRepository.save(creditCard2);
			creditCardRepository.save(creditCard3);
			debitCardRepository.save(debitCard);

			CreditCardTransaction creditCardTransaction1 = new CreditCardTransaction(100000.0, "Televisor Samsung 50 pulgadas", LocalDateTime.now(),
					"123456", Status.PASSED, 12, 0.12, creditCard1);

			CreditCardTransaction creditCardTransaction2 = new CreditCardTransaction(280000.0, "Celular Samsung S22", LocalDateTime.now(),
					"123456", Status.PASSED, 24, 0.2, creditCard1);

			CreditCardTransaction creditCardTransaction3 = new CreditCardTransaction(3800.0, "Supermercado Disco", LocalDateTime.now(),
					"123456", Status.PASSED, 3, 0.03, creditCard1);

			CreditCardTransaction creditCardTransaction4 = new CreditCardTransaction(3800.0, "Supermercado Disco", LocalDateTime.now(),
					"123456", Status.PASSED, 3, 0.03, creditCard2);

			creditCardTransactionRepository.save(creditCardTransaction1);
			creditCardTransactionRepository.save(creditCardTransaction2);
			creditCardTransactionRepository.save(creditCardTransaction3);
			creditCardTransactionRepository.save(creditCardTransaction4);

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			clientLoansRepository.save(clientLoan1);
			clientLoansRepository.save(clientLoan2);
			clientLoansRepository.save(clientLoan3);
			clientLoansRepository.save(clientLoan4);

			transactionRepository.save(t1);
			transactionRepository.save(t2);
			transactionRepository.save(t3);
			transactionRepository.save(t4);
			transactionRepository.save(t5);
			transactionRepository.save(t6);
			transactionRepository.save(t7);
			transactionRepository.save(t8);


		};
	}
}
