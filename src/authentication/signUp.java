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
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by shalin on 11/10/2016.
 */
public class signUp {

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(signUp.class);
    private WebDriver driver;
    private String websiteURL;
    private Properties testData;
    private Random rand;

    @Before
    public void beforeTesting() throws IOException {
        testData = new Properties();
        testData.load(new FileInputStream("properties/signUp.properties"));
        System.setProperty("webdriver.chrome.driver", "E:\\Selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        websiteURL = "https://www.codecademy.com/register";
        rand = new Random();
    }

    @Test
    public void testSignUpValid() {

        int validTests = Integer.parseInt(testData.getProperty("noOfVTests"));
        while (validTests > 0) {

            log.info("testing valid signUp credentials: " + testData.getProperty("eV" + validTests) + "and" + testData.getProperty("pV" + validTests));


            driver.get(websiteURL);

            driver.findElement(By.id("user_email")).sendKeys(testData.getProperty("eV" + validTests));
            driver.findElement(By.id("user_password")).sendKeys(testData.getProperty("pV" + validTests));
            driver.findElement(By.id("user_username")).sendKeys(testData.getProperty("pV" + validTests));
            driver.findElement(By.id("user_signup_submit_2")).click();
            validTests--;
            System.out.println(driver.getTitle());
            assertEquals("Dashboard | Codecademy", driver.getTitle());
        }

    }

    @Test
    public void testSignUpInValid() {

        int inValidTests = Integer.parseInt(testData.getProperty("noOfIVTests"));
        while (inValidTests > 0) {

            log.info("testing valid signin credentials: " + testData.getProperty("eV" + inValidTests) + "and" + testData.getProperty("pV" + inValidTests));


            driver.get(websiteURL);


            driver.findElement(By.id("user_email")).sendKeys(testData.getProperty("eIV" + inValidTests) + rand.nextInt(2));
            driver.findElement(By.id("user_password")).sendKeys(testData.getProperty("pIV" + inValidTests) + rand.nextInt(2));
            driver.findElement(By.id("user_username")).sendKeys(testData.getProperty("uIV" + inValidTests) + rand.nextInt(2));
            WebElement errorElement = driver.findElement(By.xpath("//*[@id=\"new-user\"]/div[4]/div"));
            try {
                assertTrue(errorElement.isDisplayed());
                driver.get(websiteURL);
            } catch (AssertionError e) {
                System.out.println("Error:Invalid credentials accepted on signup page..!!!!! testcase no: " + inValidTests);
            }
            inValidTests--;
        }

    }

    @After
    public void afterTesting() {
        driver.quit();
    }

}
