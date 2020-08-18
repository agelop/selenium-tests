package flows;


import org.openqa.selenium.WebDriver;
import page.objects.AosMainPage;
import page.objects.BasePage;

public class AosFlows extends BasePage {

    AosMainPage mainpage = new AosMainPage(driver);

    public AosFlows(WebDriver driver) {
        super(driver);
    }

    public String SignIn(String username, String password) {

        mainpage.clickUserIcon();

        mainpage.fillUsername(username);
        mainpage.fillPassword(password);

        mainpage.clickSignin();

        return mainpage.getLoggedUser();

    }

    public void SignOut() {

        mainpage.clickUserIcon();
        mainpage.clickSignout();

    }




}
