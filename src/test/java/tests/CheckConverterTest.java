package tests;

import data.Configuration;
import data.Currency;
import data.DataProviderClass;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CurrencyConverterPage;
import utils.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CheckConverterTest extends BaseTest {

    @Test(dataProvider = "checkCurrencyRate", dataProviderClass = DataProviderClass.class)
    public void checkCurrencyRate(int amount, Currency baseCurrency){
        Logger.start("Check Currency");
        CurrencyConverterPage converterPage = new CurrencyConverterPage(driver);

        converterPage
                .open(Configuration.CONVERTER_PAGE_URL)
                .enterQuotedCurrencyAmount(amount)
                .clickOnBaseDropDownInput()
                .selectBaseCurrency(baseCurrency);

        Double currencyRate = Currency.getCurrencyRate(Currency.USD, baseCurrency);
        Double convertedAmount = converterPage.getConvertedAmount();

        Assert.assertEquals(round(amount * currencyRate), round(convertedAmount));
    }

    public static double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
