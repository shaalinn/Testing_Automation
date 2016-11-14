package authentication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by shalin on 11/10/2016.
 */
public class signIn {

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(signIn.class);
    private WebDriver driver;
    private String websiteURL;
    private Properties testData;
    private Random rand;

    @Before
    public void beforeTesting() throws IOException {
        testData = new Properties();
        testData.load(new FileInputStream("properties/signIn.properties"));
        System.setProperty("webdriver.chrome.driver", "E:\\Selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        websiteURL = "https://www.codecademy.com/login";
        rand = new Random();
    }

    @Test
    public void testLogInValid() {

        int ValidTests = Integer.parseInt(testData.getProperty("noOfVTests"));
        while (ValidTests > 0) {

            log.info("testing valid signin credentials: " + testData.getProperty("eV" + ValidTests) + "and" + testData.getProperty("pV" + ValidTests));

            driver.get(websiteURL);

            driver.findElement(By.id("user_login")).sendKeys(testData.getProperty("eV" + ValidTests));
            driver.findElement(By.id("user_password")).sendKeys(testData.getProperty("pV" + ValidTests));
            driver.findElement(By.id("user_submit")).click();
            System.out.println(driver.getTitle());
            assertEquals("Dashboard | Codecademy", driver.getTitle());
            ValidTests--;
        }

    }

    @Test
    public void testLogInInValid() {

        int inValidTests = Integer.parseInt(testData.getProperty("noOfIVTests"));
        while (inValidTests > 0) {

            log.info("testing invalid signin credentials: " + testData.getProperty("eIV" + inValidTests) + "and" + testData.getProperty("pIV" + inValidTests));

            driver.get(websiteURL);

            /*driver.findElement(By.xpath("/*//*[@id=\"user_login\"]")).sendKeys(testData.getProperty("eV" + noOfIVTests));
            driver.findElement(By.xpath("/*//*[@id=\"user_password\"]")).sendKeys(testData.getProperty("pV" + noOfIVTests));*/

            driver.findElement(By.id("user_login")).sendKeys(testData.getProperty("eIV" + inValidTests) + rand.nextInt(2));
            driver.findElement(By.id("user_password")).sendKeys(testData.getProperty("pIV" + inValidTests) + rand.nextInt(2));
            driver.findElement(By.id("user_submit")).click();
            System.out.println(driver.getTitle());
            inValidTests--;
        }
        assertEquals("Log in | Codecademy", driver.getTitle());
    }

    @After
    public void afterTesting() {
        driver.quit();
    }
}
