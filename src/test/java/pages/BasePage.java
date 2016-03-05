package pages;

import data.Waiting;
import data.Waiting.Polling;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public abstract class BasePage {
    protected WebDriver driver;

    BasePage(WebDriver driver){
        this.driver = driver;
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
    }

    protected void click(final By locator){
        WebElement element = find(locator);

        try {
            element.click();
        } catch (Exception ex){
            //click by JS
        }
    }

    protected boolean isElementVisible(final By locator){
        return find(locator).isDisplayed();
    }

    protected void setImplicityTimeOut(int timeOut){
        driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
    }

    protected void waitForPageToBeLoadedByJs(){
        int count = 0;
        final JavascriptExecutor js = (JavascriptExecutor) driver;

        while (!js.executeScript("return document.readyState").equals("complete") && count < 20){
            //ReportWriter.info("Waiting for a page by document.readyState is done";
            sleep(250);
            count++;
        }
    }

    protected void sleep(long time){
        try {
            Thread.currentThread().sleep(time);
        } catch (InterruptedException e) {
            // ReportWriter.warn("Exception during call sleep, ex: e.getMessage()")
        }
    }

    protected abstract void waitPageToLoad();
}
