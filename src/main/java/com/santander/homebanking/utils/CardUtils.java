package com.santander.homebanking.utils;

import java.util.Random;

public final class CardUtils {
    private CardUtils() {
    }

    public static Integer getCvv() {
        Integer cvv = 100 + new Random().nextInt(999 - 100);
        return cvv;
    }

    public static String getCardNumber() {
        String cardNumber = (int)((Math.random() * (9999 - 1000)) + 1000) +
                "-" +   (int)((Math.random() * (9999 - 1000)) + 1000) +
                "-" +   (int)((Math.random() * (9999 - 1000)) + 1000) +
                "-" +   (int)((Math.random() * (9999 - 1000)) + 1000);
        return cardNumber;
    }

    public static String generateToken(){
        String token = String.valueOf((int)((Math.random() * (999999 - 100000)) + 100000));
        return token;
    }
}
