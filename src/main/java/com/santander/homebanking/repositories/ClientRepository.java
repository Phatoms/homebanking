package com.santander.homebanking.repositories;

import com.santander.homebanking.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    List<Client> findByFirstNameOrderByLastNameDesc(String name);

    List<Client> findByCardsColor(CardColor cardColor);

    List<Client> findDistinctByClientLoansAmountGreaterThan(Double amount);

    List<Client> findDistinctByClientLoansPaymentsGreaterThan(Integer amount);

}
