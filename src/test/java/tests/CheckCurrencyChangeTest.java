package tests;

import data.Configuration;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CurrencyConverterPage;
import utils.Logger;

public class CheckCurrencyChangeTest extends BaseTest {

    @Test
    public void chechCurrencyChangeTest(){
        Logger.start("Check currency change");
        CurrencyConverterPage converterPage = new CurrencyConverterPage(driver);

        converterPage
                .open(Configuration.CONVERTER_PAGE_URL)
                .openCalendar()
                .selectPrevSaturday();

        Double sundayCurrencyRate = converterPage.getCurrencyRate();

        converterPage
                .openCalendar()
                .selectPrevSunday();

        Double saturdayCurrencyRate = converterPage.getCurrencyRate();


        Assert.assertEquals(sundayCurrencyRate, saturdayCurrencyRate, "Currency Rate was changed during weekends");
    }
}
