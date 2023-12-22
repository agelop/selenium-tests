package selenium.tests;

import flows.MainFlows;
import flows.HomeFlows;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.objects.MainPage;
import page.objects.HomePage;
import tests.BaseTest;

import java.util.concurrent.TimeUnit;

public class LoginTest extends BaseTest {

    //private static final String ADV_WEBSITE  = "http://nimbusserver.aos.com:8000/#/";
    private static final String ADV_WEBSITE  = "http://www.advantageonlineshopping.com";


    private static final String ADV_LOGIN    = "Mercury"; //"insert login name here";
    private static final String ADV_PASSWORD = "Mercury"; //"insert password here";

    @Test
    public void checkLoggedUser() {

        //Navigate to Advantage Shopping
        getDriver().get(ADV_WEBSITE);

        //Configure Wait
        WebDriverWait wait;
        wait = (new WebDriverWait(getDriver(), 30));
        wait.pollingEvery(5, TimeUnit.SECONDS);

        //Waiting Page To Fully Load
        wait.until(ExpectedConditions.attributeToBe(By.className("loader"),"style", "display: none; opacity: 0;"));

        //SignIn to Advantage - Click User Icon
        WebElement userButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.id("hrefUserIcon")));
        userButton1.click();

        //Enter User Name
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        usernameField.sendKeys(ADV_LOGIN);

        //Enter Password
        WebElement passwdField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwdField.sendKeys(ADV_PASSWORD);

        //Click SignIn Button
        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("sign_in_btn")));
        signInButton.click();

        //Waiting Page to Refresh
        wait.until(ExpectedConditions.attributeToBe(By.className("PopUp"),"style", "display: none;"));

        //Verify Logged USer on the Page
        WebElement loggedUserMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"menuUserLink\"]/span")));
        String loggedUser = loggedUserMenu.getText();
        Assert.assertEquals(ADV_LOGIN, loggedUser);

        //Sign out User
        WebElement userButton2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("hrefUserIcon")));
        userButton2.click();

        //Click Signout Menu
        WebElement signoutMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"loginMiniTitle\"]/label[3]")));
        signoutMenu.click();


    }

    @Test
    public void checkLoggedUser1() {

        getDriver().get(ADV_WEBSITE);
        HomePage homepage = new HomePage(getDriver());

        homepage.clickUserIcon();

        homepage.fillUsername(ADV_LOGIN);
        homepage.fillPassword(ADV_PASSWORD);

        homepage.clickSignin();

        Assert.assertEquals(ADV_LOGIN,homepage.getLoggedUser());

        homepage.clickUserIcon();
        homepage.clickSignout();

    }

    @Test
    public void checkLoggedUser2() {

        getDriver().get(ADV_WEBSITE);
        MainPage mainpage = new MainPage(getDriver());

        mainpage.clickUserIcon();

        mainpage.fillUsername(ADV_LOGIN);
        mainpage.fillPassword(ADV_PASSWORD);

        mainpage.clickSignin();

        Assert.assertEquals(ADV_LOGIN,mainpage.getLoggedUser());

        mainpage.clickUserIcon();
        mainpage.clickSignout();

    }

    @Test
    public void checkLoggedUser3() {

        getDriver().get(ADV_WEBSITE);
        MainFlows flows = new MainFlows(getDriver());

        String loggedUser = flows.SignIn(ADV_LOGIN, ADV_PASSWORD);

        Assert.assertEquals(ADV_LOGIN,loggedUser);

        flows.SignOut();

    }


    @Test
    public void checkLoggedUser4() {

        getDriver().get(ADV_WEBSITE);
        HomeFlows flows = new HomeFlows(getDriver());

        Assert.assertEquals(ADV_LOGIN,flows.SignIn(ADV_LOGIN, ADV_PASSWORD));

        flows.SignOut();

    }

}
