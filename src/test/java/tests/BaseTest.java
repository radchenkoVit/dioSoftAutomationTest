package tests;

import data.Waiting;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


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
        if (testResult.getStatus() == ITestResult.FAILURE) {
           //Report
        } else {
            //Report
        }

        driver.close();
        driver.quit();
    }
}
