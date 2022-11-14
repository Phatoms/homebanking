package com.santander.homebanking.dtos;

import com.santander.homebanking.models.Card;
import com.santander.homebanking.models.DebitCard;

public class DebitCardDTO extends CardDTO{

    public DebitCardDTO(DebitCard debitCard) {
        super(debitCard);
    }


}
