package tests;

import data.Waiting;
import org.apache.commons.logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Logger;


import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Waiting.MIDDLE, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @AfterMethod
    public void tearDown(Method method, ITestResult testResult){
        String testName = method.getName();
        if (testResult.getStatus() == ITestResult.FAILURE) {
            Logger.fail(testName);
        } else {
            Logger.pass(testName);
        }

        driver.close();
        driver.quit();
    }
}
