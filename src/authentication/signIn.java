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

    @Before
    public void beforeTesting() throws IOException {
        testData = new Properties();
        testData.load(new FileInputStream("properties/signIn.properties"));
        System.setProperty("webdriver.chrome.driver", "E:\\Selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        websiteURL = "https://www.codecademy.com/login";
    }

    @Test
    public void testLogInValid() {

        int noOfVTests = Integer.parseInt(testData.getProperty("noOfVTests"));
        while (noOfVTests > 0) {

            log.info("testing valid signin credentials: " + testData.getProperty("eV" + noOfVTests) + "and" + testData.getProperty("pV" + noOfVTests));

            driver.get(websiteURL);

            driver.findElement(By.id("user_login")).sendKeys(testData.getProperty("eV" + noOfVTests));
            driver.findElement(By.id("user_password")).sendKeys(testData.getProperty("pV" + noOfVTests));
            driver.findElement(By.id("user_submit")).click();
            System.out.println(driver.getTitle());
            assertEquals("Dashboard | Codecademy", driver.getTitle());
            noOfVTests--;
        }

    }

    @Test
    public void testLogInInValid() {

        int noOfIVTests = Integer.parseInt(testData.getProperty("noOfIVTests"));
        while (noOfIVTests > 0) {

            log.info("testing invalid signin credentials: " + testData.getProperty("eIV" + noOfIVTests) + "and" + testData.getProperty("pIV" + noOfIVTests));

            driver.get(websiteURL);

            /*driver.findElement(By.xpath("/*//*[@id=\"user_login\"]")).sendKeys(testData.getProperty("eV" + noOfIVTests));
            driver.findElement(By.xpath("/*//*[@id=\"user_password\"]")).sendKeys(testData.getProperty("pV" + noOfIVTests));*/

            driver.findElement(By.id("user_login")).sendKeys(testData.getProperty("eIV" + noOfIVTests));
            driver.findElement(By.id("user_password")).sendKeys(testData.getProperty("pIV" + noOfIVTests));
            driver.findElement(By.id("user_submit")).click();
            System.out.println(driver.getTitle());
            noOfIVTests--;
        }
        assertEquals("Log in | Codecademy", driver.getTitle());
    }

    @After
    public void afterTesting() {
        driver.quit();
    }
}
