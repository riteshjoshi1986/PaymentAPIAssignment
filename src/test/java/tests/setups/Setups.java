package tests.setups;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Setups {

    public void setPropertyOS(){
        getPropertyForMacOS();
    }

    public void getPropertyForMacOS() {
        System.setProperty("webdriver.gecko.driver", "/Users/Ritesh/Documents/workspace/geckodriver");
    }



    private void defaultSetup(WebDriver driver) {
        driver.get("https://developer.payments.ca");
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
    }

    public void homePageSetup(WebDriver driver) {
        defaultSetup(driver);
    }

    public void captureScreenShot(WebDriver driver, String fName){
        try {
            TakesScreenshot ts=(TakesScreenshot)driver;
            File source=ts.getScreenshotAs(OutputType.FILE);
            FileHandler.copy(source, new File("Screenshots//"+fName+".png"));
            System.out.println("Screenshot taken");
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot "+e.getMessage());
        }

    }

}
