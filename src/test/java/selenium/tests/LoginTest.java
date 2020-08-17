package selenium.tests;

import org.junit.*;
import page.objects.AosMainPage;
import page.objects.HomePage;
import tests.BaseTest;

public class LoginTest extends BaseTest {

    //private static final String ADV_WEBSITE  = "http://nimbusserver.aos.com:8000/#/";
    private static final String ADV_WEBSITE  = "http://www.advantageonlineshopping.com";


    private static final String ADV_LOGIN    = "Mercury"; //"insert login name here";
    private static final String ADV_PASSWORD = "Mercury"; //"insert password here";

    @Test
    public void checkLoggedUser() {

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
    public void checkLoggedUser1() {

        getDriver().get(ADV_WEBSITE);

        AosMainPage mainpage = new AosMainPage(getDriver());
        mainpage.clickUserIcon();

        mainpage.fillUsername(ADV_LOGIN);
        mainpage.fillPassword(ADV_PASSWORD);

        mainpage.clickSignin();

        Assert.assertEquals(ADV_LOGIN,mainpage.getLoggedUser());

        mainpage.clickUserIcon();
        mainpage.clickSignout();

    }

}
