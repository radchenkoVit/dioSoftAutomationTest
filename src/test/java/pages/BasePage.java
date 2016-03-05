package pages;

import data.Waiting;
import data.Waiting.Polling;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import utils.Logger;

import java.util.concurrent.TimeUnit;

public abstract class BasePage {
    protected WebDriver driver;
    protected JavascriptExecutor js;

    BasePage(WebDriver driver){
        this.driver = driver;
        js = (JavascriptExecutor) driver;
    }

    protected WebElement find(final By locator){
        return find(locator, Waiting.MIDDLE);
    }

    protected WebElement find(final By locator, final int timeout){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(Polling.OFFTEN, TimeUnit.MILLISECONDS)
                .ignoring(StaleElementReferenceException.class);

        setImplicityTimeOut(Waiting.NONE);

        final WebElement[] element = {null};

        try {
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try {
                        element[0] = driver.findElement(locator);
                        return element[0] != null;
                    } catch (NoSuchElementException ex) {
                        return Boolean.FALSE;
                    }
                }
            });
        } catch (TimeoutException ex){
            Assert.fail("Element by:" + locator + " was not found");
        }

        setImplicityTimeOut(Waiting.MIDDLE);

        return element[0];
    }

    protected void sendKeys(final By locator, final String text){
        WebElement el = find(locator);
        el.clear();
        el.sendKeys(text);
        Logger.info(text + " was entered to element, by: " + locator);
    }

    protected void click(final By locator){
        WebElement element = find(locator);

        try {
            element.click();
        } catch (WebDriverException exception){
            if (exception.getMessage().contains("Element is not clickable at point")) {
                js.executeScript("arguments[0].click();", element);
            }
        }

        Logger.info("Click on element, by: " + locator);
    }

    protected void setImplicityTimeOut(int timeOut){
        driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
    }

    protected abstract void waitPageToLoad();
}
