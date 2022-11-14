package com.santander.homebanking.dtos;

import com.santander.homebanking.models.Card;
import com.santander.homebanking.models.CreditCard;

public class CreditCardDTO extends CardDTO{
    private Long maxLimit;
    private Long availableLimit;

    public CreditCardDTO(CreditCard creditCard) {
        super(creditCard);
        this.maxLimit = creditCard.getMaxLimit();
        this.availableLimit = creditCard.getAvailableLimit();
    }

    public Long getMaxLimit() {
        return maxLimit;
    }

    public Long getAvailableLimit() {
        return availableLimit;
    }
}
