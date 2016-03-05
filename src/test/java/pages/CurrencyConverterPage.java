package pages;

import data.Currency;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.Logger;

public class CurrencyConverterPage extends BasePage {
    public CurrencyConverterPage(WebDriver driver) {
        super(driver);
    }

    private static String currencyDropDownItemString = ".//div[@id='%s_currency_selector']//div[span[contains(@class, '%s')]]";

    public static By quotedAmountInput = By.xpath(".//input[@id='quote_amount_input']");
    public static By baseAmountInput = By.xpath(".//input[@id='base_amount_input']");
    public static By baseDropDownElemnt = By.xpath(".//div[@id='base_currency']");

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

    public Double getConvertedAmount(){
        WebElement element = find(baseAmountInput);
        return Double.valueOf(element.getAttribute("value").replaceAll(",", ""));
    }

    @Override
    protected void waitPageToLoad() {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(baseAmountInput));
        } catch (TimeoutException e){
            Assert.fail("Page was not loaded during 10 seconds");
        }
    }
}
