package com.santander.homebanking.dtos;

import com.santander.homebanking.models.Card;

public class CardSimpleDTO {

    private String cardHolder;

    private String number;

    private Integer cvv;

    public CardSimpleDTO(Card card) {
        this.cardHolder = card.getCardHolder();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
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
}
