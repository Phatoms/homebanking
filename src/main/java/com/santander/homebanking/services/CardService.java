package com.santander.homebanking.services;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.dtos.CardSimpleDTO;
import com.santander.homebanking.models.Card;
import com.santander.homebanking.models.CardColor;
import com.santander.homebanking.models.CardType;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.repositories.CardRepository;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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


    public Set<CardDTO> getCardHolders(String cardHolder){
        return cardRepository.findByCardHolder(cardHolder).stream().map(CardDTO::new).collect(Collectors.toSet());
    }

    public Set<CardDTO> getCardByNumber(String number){
        return cardRepository.findByNumber(number).stream().map(CardDTO::new).collect(Collectors.toSet());
    }

    public Set<CardDTO> getCardByCardType(String cardType){
        try {
            return cardRepository.findByType(CardType.valueOf(cardType)).stream().map(CardDTO::new).collect(Collectors.toSet());
        }catch (IllegalArgumentException e){
            return null;
        }
    }

    public Set<CardDTO> findAllCards(){
        return cardRepository.findAllCards().stream().map(CardDTO::new).collect(Collectors.toSet());
    }

    public Set<String> findAllCardsString(){
        return cardRepository.findAllCardsString().stream().collect(Collectors.toSet());
    }

    public Set<CardSimpleDTO> findAllCardsDTO(){
        return cardRepository.findAllCards().stream().map(CardSimpleDTO::new).collect(Collectors.toSet());
    }

    public Boolean addCard(String cardColor, String cardType,
                           Authentication authentication) {

        Boolean result = false;
        Client client = clientRepository.findByEmail(authentication.getName()).orElse(null);

        if(client == null){
            return result;
        }

        try {
            CardColor.valueOf(cardColor);
            CardType.valueOf(cardType);
        } catch (IllegalArgumentException e){
            return result;
        }


        Long numTarjetasMismoTipo = client.getCards().stream().
                filter(card -> card.getType() == CardType.valueOf(cardType)).count();
        if (numTarjetasMismoTipo >= 3){
            return result;
        }

        Integer cvv = CardUtils.getCvv();
        String cardNumber = CardUtils.getCardNumber();

        String cardHolder = client.getFirstName() + " " + client.getLastName();
        LocalDate initialDate = LocalDate.now();
        LocalDate thruDate = initialDate.plusYears(5);
        Card card = new Card(cardHolder, cardNumber.toString(), cvv, initialDate, thruDate, CardColor.valueOf(cardColor),
                CardType.valueOf(cardType));

        card.setClient(client);
        cardRepository.save(card);
        result = true;

        return result;
    }




}
