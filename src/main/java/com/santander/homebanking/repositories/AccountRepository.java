package com.santander.homebanking.repositories;

import com.santander.homebanking.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByNumber(String number);
    List<Account> findByCreationDate(LocalDate creationDate);
    List<Account> findByBalanceGreaterThan(Long balance);

    List<Account> findByClientEmail(String email);

    @Query("SELECT a FROM Account a WHERE a.balance = ?1")
    List<Account> findByBalance(Long balance);

    @Query("SELECT a FROM Account a WHERE a.balance = ?1")
    List<Account> findByNumberInj(String number);



}
