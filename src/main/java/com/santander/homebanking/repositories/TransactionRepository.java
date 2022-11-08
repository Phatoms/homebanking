package com.santander.homebanking.repositories;

import com.santander.homebanking.models.Transaction;
import com.santander.homebanking.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByDescriptionOrAmount(String description, Long amount);

    List<Transaction> findByType(TransactionType transactionType);
}
