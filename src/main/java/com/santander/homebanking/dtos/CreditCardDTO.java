package com.santander.homebanking.dtos;

import com.santander.homebanking.models.Card;
import com.santander.homebanking.models.CreditCard;

public class CreditCardDTO extends CardDTO{
    private Double maxLimit;
    private Double availableLimit;

    public CreditCardDTO(CreditCard creditCard) {
        super(creditCard);
        this.maxLimit = creditCard.getMaxLimit();
        this.availableLimit = creditCard.getAvailableLimit();
    }

    public Double getMaxLimit() {
        return maxLimit;
    }

    public Double getAvailableLimit() {
        return availableLimit;
    }
}
