package com.santander.homebanking;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(value = Suite.class)
@Suite.SuiteClasses({
        CardUtilsTest.class,
        CardImplServiceTest.class,
        CreditCardImplServiceTest.class,
        ClientImplServiceTest.class,
        LoanImplServiceTest.class,
//        TransactionImplServiceTest.class
})
public class AllTests {
}
