package com.santander.homebanking.repositories;

import com.santander.homebanking.models.TransactionCreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TransactionCreditCardRepository extends JpaRepository<TransactionCreditCard, Long> {
}
