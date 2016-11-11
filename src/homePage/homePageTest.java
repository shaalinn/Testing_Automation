package homePage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by shalin on 11/8/2016.
 */
public class homePageTest {
    private WebDriver driver;
    private String websiteURL;
    private int count;

    @Before
    public void setupDriver() {
        System.setProperty("webdriver.chrome.driver", "E:\\Selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        //websiteURL = "https://www.surveymonkey.com/";
        websiteURL = "https://www.codecademy.com/";
        count = 0;
    }

    @Test
    public void linkTesting() {

        driver.get(websiteURL);
        List<WebElement> links = driver.findElements(By.tagName("a"));
        ArrayList<String> linkNames = new ArrayList<String>();
        for (WebElement link : links) {
            if (count < 50) {
                if (link.getText().length() > 0) {
                    linkNames.add(link.getText());
                }
                count++;
            }
        }
        System.out.println(linkNames.size());
        String cssClassName, id;

        for (String linkName : linkNames) {
            try {
                if (linkName.equals("Survey Services") || linkName.equals("Home") || linkName.equals("Examples") || linkName.equals("How It Works"))
                    continue;
                System.out.println(linkName);
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                WebElement currentElement = driver.findElement(By.linkText(linkName));
                cssClassName = currentElement.getAttribute("class");
                id = currentElement.getAttribute("id");
                if (cssClassName.contains("element-invisible") ||
                        cssClassName.contains("accessibility-aid js-skip-to-content") ||
                        cssClassName.contains("dropdown-toggle") ||
                        id.contains("scrolldown")) {
                    continue;
                }
                jse.executeScript("scroll(0," + currentElement.getLocation().y + (-350) + ")");
                currentElement.click();

                System.out.println("Title of page: " + driver.getTitle());

                driver.navigate().back();
            } catch (NoSuchElementException e) {
                System.out.println(linkName + ": generated exception");
                /*driver.get(websiteURL);*/
            }
        }

    }

    @Test
    public void testImages() {
        driver.get(websiteURL);
        List<WebElement> images = driver.findElements(By.tagName("img"));

        for (WebElement image : images) {
            String altValue = image.getAttribute("alt");
            if (altValue != null && !altValue.equals("")) {
                // image has alt attribute
                System.out.println("Alt found as : " + altValue);
            } else {
                System.out.println("Missing Alt for src : " + image.getAttribute("src"));
            }
        }
    }

    @After
    public void afterTest() {
        driver.quit();
    }

}