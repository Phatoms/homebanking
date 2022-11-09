package com.santander.homebanking.repositories;

import com.santander.homebanking.models.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {
}
