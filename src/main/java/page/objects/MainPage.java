package page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage {

    By loaderLocator = By.className("loader");

    By userIconLocator = By.id("hrefUserIcon");

    By loggeduserLocator = By.xpath("//*[@id=\"menuUserLink\"]/span");

    By signoutMenuLocator = By.xpath("//*[@id=\"loginMiniTitle\"]/label[3]");

    By usernameLocator = By.name("username");

    By passwordLocator = By.name("password");

    By signinLocator = By.id("sign_in_btnundefined");

    public MainPage(WebDriver driver) {
        super(driver);
        waitForLoadPage();
    }

    private void waitForLoadPage() {
        wait.until(ExpectedConditions.attributeToBe(loaderLocator,"style", "display: none; opacity: 0;"));
    }


    public void clickUserIcon() {
        WebElement userIcon = wait.until(ExpectedConditions.elementToBeClickable(userIconLocator));
        userIcon.click();
    }

    public String getLoggedUser() {
        WebElement loggeduser = wait.until(ExpectedConditions.visibilityOfElementLocated(loggeduserLocator));
        return loggeduser.getText();
    }

    public void clickSignout() {
        WebElement signoutMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(signoutMenuLocator));
        signoutMenu.click();
    }

    public void fillUsername(String email) {
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameLocator));
        username.sendKeys(email);
    }

    public void fillPassword(String password) {
        WebElement passwd = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLocator));
        passwd.sendKeys(password);
    }

    public void clickSignin() {
        WebElement signin = wait.until(ExpectedConditions.elementToBeClickable(signinLocator));
        signin.click();
    }
}
