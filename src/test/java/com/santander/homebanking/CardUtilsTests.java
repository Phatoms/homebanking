package com.santander.homebanking;

import com.santander.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

}
