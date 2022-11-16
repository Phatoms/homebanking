package com.santander.homebanking.services;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.dtos.CardSimpleDTO;
import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.CardRepository;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.utils.CardUtils;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;
    private final int MAX_CARDS_TYPE = 3;

    private Client client;


    public Set<CardDTO> getCardHolders(String cardHolder){
//        return cardRepository.findByCardHolder(cardHolder).stream().map(CardDTO::new).collect(Collectors.toSet());
        return null;
    }

    public Set<CardDTO> getCardByNumber(String number){
/*        return cardRepository.findByNumber(number).stream().map(CardDTO::new).collect(Collectors.toSet());*/
        return null;
    }

    public Set<CardDTO> getCardByCardType(String cardType){
        try {
//            return cardRepository.findByType(CardType.valueOf(cardType)).stream().map(CardDTO::new).collect(Collectors.toSet());
            return null;
        }catch (IllegalArgumentException e){
            return null;
        }
    }
/*
    public Set<CardDTO> findAllCards(){
        return cardRepository.findAllCards().stream().map(CardDTO::new).collect(Collectors.toSet());
    }

    public Set<String> findAllCardsString(){
        return cardRepository.findAllCardsString().stream().collect(Collectors.toSet());
    }

    public Set<CardSimpleDTO> findAllCardsDTO(){
        return cardRepository.findAllCards().stream().map(CardSimpleDTO::new).collect(Collectors.toSet());
    }*/

    public CardDTO newBasicCard(String cardColor, CardType cardType,
                                HttpSession session) {

        ResponseUtils res = validateCard(cardColor, cardType, session);

        if (!res.getDone()){
            return null;
        }

        Integer cvv = CardUtils.getCvv();
        String cardNumber = CardUtils.getCardNumber();

        String cardHolder = client.getFirstName() + " " + client.getLastName();
        LocalDate initialDate = LocalDate.now();
        LocalDate thruDate = initialDate.plusYears(5);

        CardDTO card = new CardDTO(-1L, cardHolder, cardNumber.toString(), cvv, initialDate, thruDate, CardColor.valueOf(cardColor),
                cardType, "pin");

        return card;
    }

    public ResponseUtils validateCard(String cardColor, CardType cardType,
                             HttpSession session){
        ResponseUtils res = new ResponseUtils(true, 201, "card.validation.success");
        client = (Client) session.getAttribute("client");

        if(client == null){
            return new ResponseUtils(false, 400, "card.validation.failure");
        }

        Long numTarjetasMismoTipo = client.getCards().stream().
                filter(card -> card.getType() == cardType).count();

        if (numTarjetasMismoTipo >= 3){
            return new ResponseUtils(false, 400, "account.validation.failure.max-cards");
        }

        return res;
    }




}
