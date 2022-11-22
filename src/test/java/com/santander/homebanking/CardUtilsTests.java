package com.santander.homebanking;

import com.santander.homebanking.models.InterestRate;
import com.santander.homebanking.repositories.InterestRateRepository;
import com.santander.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests {

    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void cardNumberContainsSeparator(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,containsString("-"));
    }

    @Test
    public void cvvIsCreated(){
        Integer cvv = CardUtils.getCvv();
        assertThat(cvv,notNullValue());
    }

    @Test
    public void cvvGreaterThan100(){
        Integer cvv = CardUtils.getCvv();
        assertThat(cvv,greaterThanOrEqualTo(100));
    }

    @Test
    public void tokenIsCreated(){
        String token = CardUtils.generateToken();
        assertThat(token, notNullValue());
        assertThat(token, hasLength(6));
    }

    @Test
    public void feeIsCreated(){
        Double fee = CardUtils.getFee(12, new InterestRate(12, 0.06), 20000.0);
        System.out.println(fee);
        assertThat(fee, is(not(nullValue())));
        assertThat(fee, is(3353.66));
    }


}
