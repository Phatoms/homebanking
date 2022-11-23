package com.santander.homebanking;

import com.santander.homebanking.services.implement.CreditCardImplService;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(value = Suite.class)
@Suite.SuiteClasses({
        CardUtilsTests.class,
        CardImplServiceTest.class,
        CreditCardImplServiceTests.class,
        ClientImplServiceTests.class
})
public class AllTests {
}
