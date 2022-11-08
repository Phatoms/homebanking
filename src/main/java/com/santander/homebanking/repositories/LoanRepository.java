package com.santander.homebanking.repositories;

import com.santander.homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByMaxAmountLessThanEqual(Double maxAmount);

    Optional<Loan> findByNameStartingWith(String name);

}
