package pages;

import data.Currency;
import data.Waiting;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.Logger;

import java.util.List;

public class CurrencyConverterPage extends BasePage {
    public CurrencyConverterPage(WebDriver driver) {
        super(driver);
    }

    private static String currencyDropDownItemString = ".//div[@id='%s_currency_selector']//div[span[contains(@class, '%s')]]";

    private static By quotedAmountInput = By.xpath(".//input[@id='quote_amount_input']");
    private static By baseAmountInput = By.xpath(".//input[@id='base_amount_input']");
    private static By baseDropDownElemnt = By.xpath(".//div[@id='base_currency']");
    private static By dateRewindButton = By.xpath(".//div[@id='date_rewind']");
    private static By openCalendarButton = By.xpath(".//a[@id='end_date_button']");
    private static By closeCalendarButton = By.xpath(".//div[@class='closeCalendar']");

    //Calendar's locators
    private static By saturdaysCalendarElement = By.xpath(".//td[contains(@class, 'calendarWeekend') and not(contains(@class, 'Invalid'))][2]");
    private static By sundaysCalendarElement = By.xpath(".//td[contains(@class, 'calendarWeekend') and not(contains(@class, 'Invalid'))][1]");

    private static By updateCurrencyLoader = By.xpath(".//div[@id=preloader][@style='']");

    public CurrencyConverterPage open(final String url){
        driver.get(url);
        waitPageToLoad();
        Logger.info("Open page, url: " + url);
        return this;
    }

    public CurrencyConverterPage enterQuotedCurrencyAmount(final float amount){
        sendKeys(quotedAmountInput, Float.toString(amount));
        return this;
    }

    public CurrencyConverterPage clickOnBaseDropDownInput(){
        click(baseDropDownElemnt);
        return this;
    }

    public CurrencyConverterPage clickOnPreviousDate(){
        click(dateRewindButton);
        return this;
    }

    public CurrencyConverterPage openCalendar(){
        click(openCalendarButton);
        return this;
    }

    public CurrencyConverterPage closeCalendar(){
        click(closeCalendarButton);
        return this;
    }

    public CurrencyConverterPage selectPrevSunday(){
        selectPrevDay(sundaysCalendarElement);
        return this;
    }

    public CurrencyConverterPage selectPrevSaturday(){
        selectPrevDay(saturdaysCalendarElement);
        return this;
    }

    private void selectPrevDay(final By locator){
        List<WebElement> days = driver.findElements(locator);
        WebElement dayElement = days.get(days.size() - 1);
        dayElement.click();
        waitUntilCurrencyRateChange();
    }

    public CurrencyConverterPage selectBaseCurrency(final Currency currency){
        String formattedCurrencyDropDownItemString = String.format(currencyDropDownItemString, "base", currency.getCode());
        By currencyDropDownLocator = By.xpath(formattedCurrencyDropDownItemString);

        WebElement currencyDropDownElement = find(currencyDropDownLocator);

        if (!currencyDropDownElement.isDisplayed()) {
            JavascriptExecutor je = (JavascriptExecutor) driver;
            je.executeScript("arguments[0].scrollIntoView(true);", currencyDropDownElement);
        }

        currencyDropDownElement.click();
        return this;
    }

    public Double getQuotedAmount(){
        WebElement element = find(quotedAmountInput);
        return Double.valueOf(element.getAttribute("value").replaceAll(",", ""));
    }

    public Double getConvertedAmount(){
        WebElement element = find(baseAmountInput);
        return Double.valueOf(element.getAttribute("value").replaceAll(",", ""));
    }

    public Double getCurrencyRate(){
        if (!getQuotedAmount().equals(1d)){
            return getQuotedAmount() / getConvertedAmount();
        } else {
            return getConvertedAmount();
        }
    }

    @Override
    protected void waitPageToLoad() {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(baseAmountInput));
        } catch (TimeoutException e){
            Assert.fail("Page was not loaded during 10 seconds");
        }
    }

    private void waitUntilCurrencyRateChange(){
        new WebDriverWait(driver, Waiting.SHORT).until(ExpectedConditions.invisibilityOfElementLocated(updateCurrencyLoader));
    }
}
