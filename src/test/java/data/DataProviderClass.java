package data;

import org.testng.annotations.DataProvider;

public class DataProviderClass {

    @DataProvider(name = "checkCurrencyRate")
    public static Object[][] checkCurrencyRate() {
        return new Object[][]{
                {100, Currency.EURO},
                {200, Currency.USD},
                {300, Currency.ZL}
                //to add another test data
        };
    }

}
