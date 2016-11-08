package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by shalin on 11/8/2016.
 */
public class Webdriver_class {

        @Test
        public void testGoogleSearch(){
            System.setProperty("webdriver.gecko.driver","E:\\Selenium\\geckodriver.exe");
            WebDriver driver = new FirefoxDriver();
            driver.get("http://google.com");
            System.out.println(driver.getTitle());
            WebElement searchBar = driver.findElement(By.id("lst-ib"));
            searchBar.sendKeys("hello");
            searchBar.submit();

            driver.close();
        }


}
