package com.santander.homebanking.repositories;

import com.santander.homebanking.models.Card;
import com.santander.homebanking.models.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {

/*    List<Card> findByCardHolder (String cardHolder);

    List<Card> findByNumber (String number);

    List<Card> findByType (CardType cardType);

    @Query(value = "SELECT * FROM Card WHERE type = 1 ORDER by cvv DESC", nativeQuery = true)
    List<Card> findAllCards();

    @Query(value = "SELECT number,cvv,card_holder FROM Card WHERE type = 1 ORDER by cvv DESC", nativeQuery = true)
    List<String> findAllCardsString();

    @Query(value = "SELECT number,cvv,card_holder FROM Card WHERE type = 1 ORDER by cvv DESC", nativeQuery = true)
    List<Card> findAllCardsDTO();*/

}
