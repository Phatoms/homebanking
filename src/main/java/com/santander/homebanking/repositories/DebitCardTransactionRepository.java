package com.santander.homebanking.repositories;

import com.santander.homebanking.models.DebitCardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DebitCardTransactionRepository extends JpaRepository<DebitCardTransaction, Long> {
}
