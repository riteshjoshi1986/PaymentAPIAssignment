package tests.pagesTest;

import io.restassured.RestAssured;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverInfo;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.pages.HomePage;
import tests.setups.Setups;
import org.testng.Assert;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;

public class HomePageTest {
    private WebDriver driver;
    private HomePage browser;
    private Setups setups = new Setups();
    private String username = "riteshjoshi1986@gmail.com";
    private String pswd = "Aug@2021";
    private String dprn = "123456789";

    @BeforeTest
    public void setUp() {
        setups.setPropertyOS();
        //driver = new ChromeDriver();
        driver = new FirefoxDriver();
        this.browser = new HomePage(driver);
        setups.homePageSetup(driver);
    }

    @AfterMethod
    public void captureScreenFailed(ITestResult result) {

        if(ITestResult.FAILURE==result.getStatus()) {
            setups.captureScreenShot(driver,result.getName());
        }
    }


    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @DataProvider(name="appNames")
    public Object[][] getDataFromDataprovider(){
        return new Object[][]
                {
                        { "Visa"}
                };

    }

    @Test (dataProvider="appNames")
    public void TC1_test_createApp(String appName) {
        browser.clickLoginButton();
        browser.loginPortal(username,pswd);
        browser.checkLoginSuccessful(username.substring(1, 10));
        browser.clickMyApp();
        browser.creatApp(appName);
    }


    @Test (dataProvider="appNames")
    public void TC2_test_ExecuteAPI(String appName) {
        browser.clickLoginButton();
        browser.loginPortal(username,pswd);
        browser.checkLoginSuccessful(username.substring(1, 10));
        browser.clickAPIs();
        browser.clickfifBranchAPIs();
        browser.clickfifBranchSandbox();
        browser.updateDPRN(dprn);
        browser.authenticateOauth(appName);
        browser.sendRequest();
        browser.validateResponseAndDprn(dprn);
    }

    @Test (dataProvider="appNames")
    public void TC3_test_APIProgramatically(String appName) {
        browser.clickLoginButton();
        browser.loginPortal(username,pswd);
        browser.checkLoginSuccessful(username.substring(1, 10));
        browser.clickMyApp();
        browser.getcKeycSecret(appName);
        browser.clickAPIs();
        browser.clickAccessTokenGenerator();
        browser.clickGenerateAccessToken();
        browser.authenticateAccessToken(browser.consumerKey,browser.consumerSecret);
        browser.sendRequest();
        String token = browser.getAccessToken().replaceAll("[\"]", "");
        System.out.println(token);
        //String token = "ugJus7eHcQgy3SINqdygKehIOOvP";
        RestAssured.given()
                .header("Accept","application/vnd.fif.api.v1+json")
                .header("Authorization","Bearer "+token)
                .when()
                .get("https://api.payments.ca/fif-branch-sandbox/branches/"+dprn)
                .then()
                .statusCode(200)
                .body("identificationNumber", equalTo(dprn+"7"))
                .log().all();
    }

    @Test (dataProvider="appNames")
    public void TC4_test_deleteApp(String appName) {
        browser.clickLoginButton();
        browser.loginPortal(username,pswd);
        browser.checkLoginSuccessful(username.substring(1, 10));
        browser.clickMyApp();
        browser.deleteApp(appName);
    }


}
