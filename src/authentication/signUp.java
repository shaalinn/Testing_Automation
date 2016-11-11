package authentication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by shalin on 11/10/2016.
 */
public class signUp {

    private WebDriver driver;
    private String websiteURL;
    private Properties testData;

    @Before
    public void beforeTesting() throws IOException {
        testData = new Properties();
        testData.load(new FileInputStream("properties/signUp.properties"));
        System.setProperty("webdriver.chrome.driver", "E:\\Selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        websiteURL = "https://www.codecademy.com/register";

    }

    @Test
    public void testSignUpValid() {

        int noOfVTests = Integer.parseInt(testData.getProperty("noOfVTests"));
        while (noOfVTests > 0) {
            driver.get(websiteURL);

            driver.findElement(By.id("user_email")).sendKeys(testData.getProperty("eV" + noOfVTests));
            driver.findElement(By.id("user_password")).sendKeys(testData.getProperty("pV" + noOfVTests));
            driver.findElement(By.id("user_username")).sendKeys(testData.getProperty("pV" + noOfVTests));
            driver.findElement(By.id("user_signup_submit_2")).click();
            noOfVTests--;
            System.out.println(driver.getTitle());
            assertEquals("Dashboard | Codecademy", driver.getTitle());
        }

    }

    @Test
    public void testSignUpInValid() {

        int noOfIVTests = Integer.parseInt(testData.getProperty("noOfIVTests"));
        while (noOfIVTests > 0) {
            driver.get(websiteURL);

            driver.findElement(By.id("user_email")).sendKeys(testData.getProperty("eIV" + noOfIVTests));
            driver.findElement(By.id("user_password")).sendKeys(testData.getProperty("pIV" + noOfIVTests));
            driver.findElement(By.id("user_username")).sendKeys(testData.getProperty("uIV" + noOfIVTests));
            WebElement errorElement = driver.findElement(By.xpath("//*[@id=\"new-user\"]/div[4]/div"));
            try {
                assertTrue(errorElement.isDisplayed());
            } catch (AssertionError e) {
                System.out.println("Error:Invalid credentials accepted on signup page..!!!!! testcase no: " + noOfIVTests);
            }
            noOfIVTests--;
        }

    }

    @After
    public void afterTesting() {
        driver.quit();
    }

}
