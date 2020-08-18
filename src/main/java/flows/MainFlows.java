package flows;


import org.openqa.selenium.WebDriver;
import page.objects.MainPage;
import page.objects.BasePage;

public class MainFlows extends BasePage {

    MainPage mainpage = new MainPage(driver);

    public MainFlows(WebDriver driver) {
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
