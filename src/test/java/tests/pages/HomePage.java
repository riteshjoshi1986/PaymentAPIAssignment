package tests.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;


public class HomePage {
    private WebDriver driver;
    public String consumerKey;
    public String consumerSecret;

    public HomePage(WebDriver driver){
        this.driver = driver;
    }

    public void waitForElementVisible(WebDriver driver, String locator) {
        WebDriverWait wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));

    }

    public void clickLoginButton() {
        driver.findElement(By.xpath("//*[@id=\"navbar\"]//*[@href='/user/login']")).click();
    }

    public void loginPortal(String username, String password) {
        waitForElementVisible(driver,"//*[@id=\"login\"]//*[@class='modal-content']");
        driver.findElement(By.id("edit-name")).clear();
        driver.findElement(By.id("edit-name")).sendKeys(username);
        driver.findElement(By.id("edit-pass")).clear();
        driver.findElement(By.id("edit-pass")).sendKeys(password);
        driver.findElement(By.id("edit-submit")).click();
    }

    public void checkLoginSuccessful(String username) {
        waitForElementVisible(driver,"(//*[@id=\"navbar\"]//*[@href='/user'])[1]");
        Boolean isLogin = driver.findElement(By.xpath("(//*[@id=\"navbar\"]//*[@href='/user'])[1]")).getText().contains(username);
        Assert.assertTrue(isLogin);
    }

    public void clickMyApp() {
        driver.findElement(By.xpath("(//a[contains(text(),'My Apps')])[1]")).click();
        waitForElementVisible(driver,"//a[contains(text(),' Add a new App')]");
    }

    public void creatApp(String appName) {
        driver.findElement(By.xpath("//a[contains(text(),' Add a new App')]")).click();
        waitForElementVisible(driver,"//h1[@class='page-title']");
        driver.findElement(By.id("edit-human")).clear();
        driver.findElement(By.id("edit-human")).sendKeys(appName);
        driver.findElement(By.xpath("//*[@id=\"api_product\"]/div[3]/label/span")).click();
        //Boolean isClicked = driver.findElement(By.xpath("//*[@id=\"api_product\"]/div[3]/label/span")).isSelected();
        //Assert.assertTrue(isClicked);
        driver.findElement(By.id("edit-submit")).click();
        waitForElementVisible(driver,"//a[contains(text(),' Add a new App')]");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class=\"error-summary\"]")).getText(),"App Created!");

    }

    public void getcKeycSecret(String appName){
        WebElement element = driver.findElement(By.xpath("//*[contains(a,'"+appName+"')]"));
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);",element);
        element.click();

        WebElement keys=driver.findElement(By.xpath("(//*[contains(a,'"+appName+"')]//following::div//*[@class='title'])[1]"));
        je.executeScript("arguments[0].scrollIntoView(true);",keys);
        consumerKey = driver.findElement(By.xpath("(//*[contains(a,'"+appName+"')]//following::div//*[@class='key'])[1]")).getText();
        consumerSecret = driver.findElement(By.xpath("(//*[contains(a,'"+appName+"')]//following::div//*[@class='key'])[2]")).getText();
        System.out.println(consumerKey);
        System.out.println(consumerSecret);
    }

    public void deleteApp(String appName) {
        waitForElementVisible(driver,"//h1[@class='page-title']");

        driver.findElement(By.xpath("//*[contains(a,'"+appName+"')]")).click();
        //Boolean isClicked = driver.findElement(By.xpath("//*[@id=\"api_product\"]/div[3]/label/span")).isSelected();
        //Assert.assertTrue(isClicked);
        WebElement element = driver.findElement(By.xpath("//*[contains(a,'"+appName+"')]//following::div//a[text()='Delete']"));
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);",element);

        driver.findElement(By.xpath("//*[contains(a,'"+appName+"')]//following::div//a[text()='Delete']")).click();
        waitForElementVisible(driver,"//*[@id='devconnect_developer_application_delete']");
        Boolean isAppThere = driver.findElement(By.xpath("//*[@id=\"devconnect_developer_application_delete\"]/div")).getText().contains(appName.toLowerCase());
        Assert.assertTrue(isAppThere);
        driver.findElement(By.xpath("//*[@value=\"Delete App\"]")).click();
        waitForElementVisible(driver,"//a[contains(text(),' Add a new App')]");
        Boolean isAppDeleted = driver.findElement(By.xpath("//div[@class='alert alert-block alert-dismissible alert-success messages status']")).getText().contains("App Deleted!");
        Assert.assertTrue(isAppDeleted);

    }

    public void clickAPIs() {
        driver.findElement(By.xpath("(//a[@href='/apis'])[1]")).click();
        waitForElementVisible(driver,"//h1[@class='page-title']");
    }

    public void clickAccessTokenGenerator() {
        driver.findElement(By.xpath("//a[@href='/access-token-generation/apis']")).click();
        waitForElementVisible(driver,"//h1[@class='page-title']");
    }

    public void clickGenerateAccessToken() {
        driver.findElement(By.xpath("//a[@href='/access-token-generation/apis/post/accesstoken']")).click();
        waitForElementVisible(driver,"//h1[@class='page-title']");
    }

    public void authenticateAccessToken(String consumerKey, String consumerSecret) {
        WebElement element = driver.findElement(By.xpath("//a[@href='#basicauth_modal']"));
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);",element);
        element.click();
        waitForElementVisible(driver,"//*[@class='modal-content']");

        driver.findElement(By.id("inputEmail")).clear();
        driver.findElement(By.id("inputEmail")).sendKeys(consumerKey);

        driver.findElement(By.id("inputPassword")).clear();
        driver.findElement(By.id("inputPassword")).sendKeys(consumerSecret);

        driver.findElement(By.xpath("(//*[@class='modal-footer']//*[text()='Save'])[1]")).click();

        waitForElementVisible(driver,"//*[@id='send_request']");
    }


    public String getAccessToken() {
        driver.findElement(By.xpath("//*[@id='link_response_tab']")).click();
        WebElement element = driver.findElement(By.xpath("(//span[text()='\"access_token\"']//following::span//following::span)[1]"));
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);",element);
        String token=element.getText();
        return token;
    }

    public void clickfifBranchAPIs() {
        WebElement element = driver.findElement(By.xpath("//a[@href='/fif-branch-api/apis']"));
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);",element);
        driver.findElement(By.xpath("//a[@href='/fif-branch-api/apis']")).click();
        waitForElementVisible(driver,"//a[text()='fif-branch-sandbox']");
    }

    public void clickfifBranchSandbox() {
        WebElement element = driver.findElement(By.xpath("//a[text()='fif-branch-sandbox']"));
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);",element);
        driver.findElement(By.xpath("//a[text()='fif-branch-sandbox']")).click();
        waitForElementVisible(driver,"//button[@id='send_request']");
    }

    public void updateDPRN(String dprn) {
        WebElement element = driver.findElement(By.xpath("//*[@class='template_param']"));
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);",element);

        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        element.sendKeys(Keys.DELETE);
        element.sendKeys(Keys.DELETE);
        element.sendKeys(Keys.DELETE);
        element.sendKeys(Keys.DELETE);
        element.sendKeys(Keys.DELETE);
        element.sendKeys(dprn);
    }

    public void authenticateOauth(String apiKey) {
        WebElement element = driver.findElement(By.xpath("//a[@href='#oauth2_modal']"));
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);",element);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='#oauth2_modal']"))).click();
        waitForElementVisible(driver,"//*[@id='smartdocs-oauth-additions-form']");
        Select APIKey = new Select(driver.findElement(By.id("edit-user-app")));
        APIKey.selectByVisibleText(apiKey);
        driver.findElement(By.xpath("//*[@value='Generate OAuth Token']")).click();
        waitForElementVisible(driver,"//a[text()='Authenticated']");
        Assert.assertEquals(element.getText(),"Authenticated");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void sendRequest()  {
        try{
            Thread.sleep(5000);
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        WebElement element = driver.findElement(By.xpath("//*[@id='send_request']"));
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);",element);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='send_request']"))).click();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void validateResponseAndDprn(String dprn){
        WebElement element = driver.findElement(By.xpath("//*[@id='link_response_tab']"));
        //JavascriptExecutor je = (JavascriptExecutor) driver;
        //je.executeScript("arguments[0].scrollIntoView(true);",element);
        element.click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        String respCode = driver.findElement(By.xpath("//*[@id='response-content']/strong")).getText();
        Boolean isSuccess = respCode.contains("200 OK");
        Assert.assertTrue(isSuccess);
        String Actual_dprn = driver.findElement(By.xpath("//span[text()='\"identificationNumber\"']//following::span//following::span")).getText();
        Assert.assertEquals(Actual_dprn,"\""+dprn+"7\"");

    }



}
