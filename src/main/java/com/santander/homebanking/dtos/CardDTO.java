package com.santander.homebanking.dtos;

import com.santander.homebanking.models.Card;
import com.santander.homebanking.models.CardColor;
import com.santander.homebanking.models.CardType;

import javax.persistence.Column;
import java.time.LocalDate;

public class CardDTO {
    private Long id;

    private String cardHolder;

    private String number;

    private Integer cvv;

    private LocalDate fromDate;

    private LocalDate thruDate;

    private CardColor color;

    private CardType type;

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.color = card.getColor();
        this.type = card.getType();
    }

    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public CardColor getColor() {
        return color;
    }

    public CardType getType() {
        return type;
    }
}
