package selenium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class RegressionTest {
    // This script was created against AOS 1.1.3.  Since it uses Xpath, you may need to update the script
    // if using against a different version.
    //private static final String ADV_WEBSITE  = "http://nimbusserver.aos.com:8000/#/";
    private static final String ADV_WEBSITE  = "http://www.advantageonlineshopping.com";

    //You will need to have an account created in AOS and will need to supply the credentials
    //These are known defaults as of 2018/sep/12
    private static final String ADV_LOGIN    = "Mercury"; //"insert login name here";
    private static final String ADV_PASSWORD = "Mercury"; //"insert password here";

    private static WebDriver driver;
    private static WebDriverWait wait;


    public RegressionTest() {
        //Change this constructor to private if you supply your own public constructor
    }


    @BeforeClass
    public static void setUpBeforeClass() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");

        driver = new ChromeDriver(options);

        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 30);
        wait.pollingEvery(5, TimeUnit.SECONDS);

        //driver.manage().deleteAllCookies();
        //driver.manage().window().maximize();

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

    @Before
    public void setUp() {

        //Navigate to Advantage Shopping
        driver.get(ADV_WEBSITE);

        //Waiting Page To Fully Load
        wait.until(ExpectedConditions.attributeToBe(By.className("loader"),"style", "display: none; opacity: 0;"));

        //SignIn to Advantage - Click User Icon
        WebElement userButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("hrefUserIcon")));
        userButton.click();

        //Enter User Name
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        usernameField.sendKeys(ADV_LOGIN);

        //Enter Password
        WebElement passwdField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwdField.sendKeys(ADV_PASSWORD);

        //Click SignIn Button
        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("sign_in_btnundefined")));
        signInButton.click();

        //Waiting Page to Refresh
        wait.until(ExpectedConditions.attributeToBe(By.className("PopUp"),"style", "display: none;"));

    }

    @After
    public void tearDown() {

        //Sign out User
        WebElement userButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("hrefUserIcon")));
        userButton.click();

        //Click Signout Menu
        WebElement signoutMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"loginMiniTitle\"]/label[3]")));
        signoutMenu.click();

    }

    @Test
    public void checkTabletPrice() {

        //Click on Tablets
        WebElement tabletsLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tabletsTxt")));
        tabletsLink.click();

        //Click on specific tablet
        WebElement tabletItem = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("HP Pro Tablet 608 G1")));
        tabletItem.click();

        //Check Price
        WebElement priceText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"Description\"]/h2")));
        String expectedPrice = "$479.00";
        String actualPrice = priceText.getText().trim();
        Assert.assertEquals(expectedPrice, actualPrice);

    }

    @Test
    public void checkSpeakerPrice() {

        //Click on Speakers
        WebElement speakersLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("speakersTxt")));
        speakersLink.click();

        //Click on specific speaker
        WebElement speakerItem = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("HP S9500 Bluetooth Wireless Speaker")));
        speakerItem.click();

        //Check Price
        WebElement priceText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"Description\"]/h2")));
        String expectedPrice = "$201.00";
        String actualPrice = priceText.getText().trim();
        Assert.assertEquals(expectedPrice, actualPrice);

    }

}
