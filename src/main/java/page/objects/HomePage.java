package page.objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class HomePage extends BasePage {

    @FindBy(className = "loader")
    private WebElement loadPage;

    @FindBy(id = "hrefUserIcon")
    private WebElement userIcon;

    @FindBy(xpath = "//*[@id=\"menuUserLink\"]/span")
    private WebElement loggeduser;

    @FindBy(xpath = "//*[@id=\"loginMiniTitle\"]/label[3]")
    private WebElement signoutMenu;

    @FindBy(name = "username")
    private WebElement username;

    @FindBy(name = "password")
    private WebElement password;

    @FindBy(id = "sign_in_btn")
    private WebElement signin;

    public HomePage(WebDriver driver) {
        super(driver);
        waitForLoadPage();
    }

    private void waitForLoadPage() {
        wait.until(ExpectedConditions.attributeToBe(loadPage,"style", "display: none; opacity: 0;"));
    }

    public void clickUserIcon() { this.userIcon.click(); }

    public String getLoggedUser() {
        wait.until(ExpectedConditions.visibilityOf(loggeduser));
        return this.loggeduser.getText();
    }

    public void clickSignout() { this.signoutMenu.click(); }

    public void fillUsername(String email) { wait.until(ExpectedConditions.visibilityOf(this.username)).sendKeys(email); }

    public void fillPassword(String password) { this.password.sendKeys(password);  }

    public void clickSignin() { wait.until(ExpectedConditions.elementToBeClickable(this.signin)).click(); }

}
