package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class BaseTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeClass
    public static void setUpBeforeClass() {
        WebDriverManager.chromedriver().forceCache();
        WebDriverManager.chromedriver().version("2.41").setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");

        driver = new ChromeDriver(options);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        //Clean up and dispose of the driver
        //Good explanation of close, quit, dispose here http://stackoverflow.com/questions/15067107/difference-between-webdriver-dispose-close-and-quit
        if(null != driver) {
            driver.close();
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

}
