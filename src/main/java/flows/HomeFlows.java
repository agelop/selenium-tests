package flows;


import org.openqa.selenium.WebDriver;
import page.objects.HomePage;
import page.objects.BasePage;

public class HomeFlows extends BasePage {

    HomePage homepage = new HomePage(driver);

    public HomeFlows(WebDriver driver) {
        super(driver);
    }

    public String SignIn(String username, String password) {

        homepage.clickUserIcon();

        homepage.fillUsername(username);
        homepage.fillPassword(password);

        homepage.clickSignin();

        return homepage.getLoggedUser();

    }

    public void SignOut() {

        homepage.clickUserIcon();
        homepage.clickSignout();

    }




}
