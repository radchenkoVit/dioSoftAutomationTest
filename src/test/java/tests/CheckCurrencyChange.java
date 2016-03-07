package tests;

import data.Configuration;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CurrencyConverterPage;

public class CheckCurrencyChange extends BaseTest {

    @Test
    public void chechCurrencyChange(){
        CurrencyConverterPage converterPage = new CurrencyConverterPage(driver);

        converterPage
                .open(Configuration.CONVERTER_PAGE_URL)
                .openCalendar();

        converterPage.selectPrevSunday();
        Double sundayCurrencyRate = converterPage.getCurrencyRate();

        converterPage.openCalendar();
        converterPage.selectPrevSaturday();
        Double saturdayCurrencyRate = converterPage.getCurrencyRate();


        Assert.assertEquals(sundayCurrencyRate, saturdayCurrencyRate, "Currency Rate was Changed during weekends");
    }
}
