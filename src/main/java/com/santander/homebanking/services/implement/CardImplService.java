package com.santander.homebanking.services.implement;

import com.santander.homebanking.dtos.CardDTO;
import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.CardRepository;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.repositories.CreditCardTransactionRepository;
import com.santander.homebanking.repositories.DebitCardTransactionRepository;
import com.santander.homebanking.services.CardService;
import com.santander.homebanking.utils.CardUtils;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Service
public class CardImplService implements CardService {

    private CardRepository cardRepository;
    private ClientRepository clientRepository;
    private CreditCardTransactionRepository creditCardTransactionRepository;
    private DebitCardTransactionRepository debitCardTransactionRepository;

    @Autowired
    public CardImplService(CardRepository cardRepository, ClientRepository clientRepository, CreditCardTransactionRepository creditCardTransactionRepository, DebitCardTransactionRepository debitCardTransactionRepository) {
        this.cardRepository = cardRepository;
        this.clientRepository = clientRepository;
        this.creditCardTransactionRepository = creditCardTransactionRepository;
        this.debitCardTransactionRepository = debitCardTransactionRepository;
    }

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


    //second, minute, hour, day of month, month, day(s) of week
    @Scheduled(cron = "1 * * * * *")
    public void chanceToReject(){
        // me traigo todas
        List<CreditCardTransaction> setCreditTransactions = creditCardTransactionRepository.findAll();

        long timeWaiting = 1;

        // las recorro y veo si tienen pendiente, y paso mas de timeWaiting min... lo cambio a reject
        for (CreditCardTransaction transaction : setCreditTransactions) {
            if(transaction.getStatus() == Status.PENDING){

                long minutes = Math.abs(ChronoUnit.MINUTES.between(LocalDateTime.now(), transaction.getTime()));

                if(minutes >= timeWaiting){
                    transaction.setStatus(Status.REJECTED);
                    creditCardTransactionRepository.save(transaction);
                }
            }
        }

        List<DebitCardTransaction> setDebitTransactions = debitCardTransactionRepository.findAll();
        for (DebitCardTransaction transaction : setDebitTransactions) {
            if(transaction.getStatus() == Status.PENDING){

                long minutes = Math.abs(ChronoUnit.MINUTES.between(LocalDateTime.now(), transaction.getTime()));

                if(minutes >= timeWaiting){
                    transaction.setStatus(Status.REJECTED);
                    debitCardTransactionRepository.save(transaction);
                }
            }
        }
    }



}
