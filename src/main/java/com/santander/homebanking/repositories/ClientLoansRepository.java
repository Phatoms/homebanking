package com.santander.homebanking.repositories;

import com.santander.homebanking.models.ClientLoan;
import com.santander.homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientLoansRepository extends JpaRepository<ClientLoan, Long> {
}
