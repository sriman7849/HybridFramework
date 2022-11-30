package page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonActions;
import utils.DBConnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class LoginEformPage extends CommonActions {
    Logger log = Logger.getLogger(LoginEformPage.class);
    WebDriver driver;
    /* it's finding username text box element in eform Application */
    @FindBy(xpath = "//input[@id='userName']")
    WebElement uname;
    /* it's finding password text box element in eform Application */
    @FindBy(xpath = "//input[@placeholder='Password']")
    WebElement Password;
    /* it's finding Log in button element in eform Application */
    @FindBy(xpath = "//button[normalize-space()='Sign in']")
    WebElement submit;
    /* it is used to close the pop-up in eform Application */
    @FindBy(xpath = "//div[@class='modal-footer border-top-0']/child::button[1]")
    WebElement closeform;


    //Constructor to get the multiple data sets to automate.
    public LoginEformPage(WebDriver rDriver) {
        driver = rDriver;

        PageFactory.initElements(rDriver, this);
    }

    /* This method is used to fill the credential from DataBase  */
    public void logInThroughDataProvider(String username, String password) throws Exception {
        sendKeysWebElement(uname, username);
        log.info("entered username credential as : " + username);
        Thread.sleep(2000);
        sendKeysWebElement(Password, password);
        log.info("entered password credential as : " + password);
        clickingOnWebElement(submit, 1);

    }

    /* This method is used to fill the credential from property  */
    public void logIn() throws IOException, InterruptedException {
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/configuration/config.properties");
        Properties prop = new Properties();
        prop.load(fis);
        sendKeysWebElement(uname, prop.getProperty("username"));
        Thread.sleep(2000);
        sendKeysWebElement(Password, prop.getProperty("password"));
        clickingOnWebElement(submit, 1);
    }

    public void logInAndClosePopUp() {
        clickingOnWebElement(closeform, 3);
    }
}