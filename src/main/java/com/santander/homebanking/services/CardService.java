package com.santander.homebanking.services;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.models.*;
import com.santander.homebanking.utils.ResponseUtils;

import javax.servlet.http.HttpSession;
import java.util.Set;

public interface CardService {

    Set<CardDTO> getCardHolders(String cardHolder);

    Set<CardDTO> getCardByNumber(String number);

    Set<CardDTO> getCardByCardType(String cardType);

    CardDTO newBasicCard(String cardColor, CardType cardType,
                                HttpSession session);
    ResponseUtils validateCard(String cardColor, CardType cardType,
                                      HttpSession session);
    void chanceToReject();

    public Client getClient();

    public void setClient(Client client);



}
