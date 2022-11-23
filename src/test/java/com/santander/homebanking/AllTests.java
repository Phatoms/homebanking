package com.santander.homebanking;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(value = Suite.class)
@Suite.SuiteClasses({
        CardUtilsTests.class,
        CardImplServiceTest.class
})
public class AllTests {
}
