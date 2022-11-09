package com.santander.homebanking.repositories;

import com.santander.homebanking.models.TransactionCreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionCreditCardRepository extends JpaRepository<TransactionCreditCard, Long> {
}
