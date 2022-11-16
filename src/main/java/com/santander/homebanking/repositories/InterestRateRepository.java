package com.santander.homebanking.repositories;

import com.santander.homebanking.models.InterestRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface InterestRateRepository extends JpaRepository<InterestRate, Long> {

    Optional<InterestRate> findByFeeNumber(Integer feeNumber);
}
