package com.santander.homebanking.dtos;

import com.santander.homebanking.models.Card;
import com.santander.homebanking.models.CardColor;
import com.santander.homebanking.models.CardType;
import com.santander.homebanking.models.Client;

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

    private String pin;


    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.color = card.getColor();
        this.type = card.getType();
        this.pin = card.getPin();
    }

    public CardDTO(Long id, String cardHolder, String number, Integer cvv, LocalDate fromDate, LocalDate thruDate,
                   CardColor color, CardType type, String pin) {
        this.id = id;
        this.cardHolder = cardHolder;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.color = color;
        this.type = type;
        this.pin = pin;
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

    public String getPin() {
        return pin;
    }
}
