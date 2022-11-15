package com.santander.homebanking.repositories;

import com.santander.homebanking.models.CreditCardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CreditCardTransactionRepository extends JpaRepository<CreditCardTransaction, Long> {
}
