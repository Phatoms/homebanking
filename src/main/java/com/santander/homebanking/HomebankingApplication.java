package com.santander.homebanking;

import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {


	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		System.out.println("borrame, estoy probando git");
		SpringApplication.run(HomebankingApplication.class, args);

	}


	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, CardRepository cardRepository, AccountRepository accountRepository,
									  LoanRepository loanRepository, ClientLoansRepository clientLoansRepository, TransactionRepository transactionRepository
	){
		return (args) -> {

			Client client1 = new Client("tomas", "quinteros", "tomas@mindhub.com", passwordEncoder.encode("password1"));
			Client client2 = new Client("jose", "perez", "jose@mindhub.com", passwordEncoder.encode("password2"));
			Client client3 = new Client("admin", "admin", "admin@admin", passwordEncoder.encode("admin"));

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

			Card card1 = new Card("Tomas Quinteros","3325-6745-7876-4445", 990, LocalDate.parse("2022-09-08"), LocalDate.parse("2027-09-08"), CardColor.GOLD, CardType.CREDIT );
			Card card2 = new Card("Admin","1925-6745-7876-4445", 220, LocalDate.parse("2022-09-08"), LocalDate.parse("2027-09-08"), CardColor.SILVER, CardType.CREDIT );
			Card card3 = new Card("Admin","8889-6745-7876-4445", 350, LocalDate.parse("2022-09-08"), LocalDate.parse("2027-09-08"), CardColor.TITANIUM, CardType.DEBIT );

			client1.addCards(card1);
			client3.addCards(card2);
			client3.addCards(card3);

			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);

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
