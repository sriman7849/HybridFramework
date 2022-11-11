package tests;

import org.testng.annotations.*;
import page.EformDetails;
import page.LoginForEform;
import page.ViewStatusPg;
import utils.CommonActions;

import java.io.IOException;


public class EfromPrivilegeTest {

    public CommonActions ca;

    public LoginForEform loginForEform;
    public EformDetails ef;
    public ViewStatusPg vsp;

    /*-----------------------------------Test Scenario TS_01----------------------------------------*/

    /* TC_02 :This method is used to navigate respective BROWSER & URL*/
    @Test(priority = 1)
    public void launchBrowserTest() throws IOException {
        ca = new LoginForEform();
        ca.launchBrowser("chrome");
        ca.navigateEfromurl();
    }

    /*TC_03 :This method is used to login using credential*/
    @Test(priority = 2)
    public void EnterCredentialsTest() throws InterruptedException, IOException {
        loginForEform = new LoginForEform();
        loginForEform.logIn();
        loginForEform.logInAndClosePopUp();
    }

    /* TC_04 , TC_05 :Select Privilege request */
    @Test(priority = 3)
    public void userIsOnPrivilegeRequestPageTest() {
        ef = new EformDetails();
        ef.clickOnMenu();
    }

    /*TC_05 : This method is used to select project name */
    @Test(priority = 4)
    public void userSelectProjectTest() throws InterruptedException {
        ef = new EformDetails();
        ef.selectProjectName();
    }

    /*TC_06 ,TC_07 : This method is used to fill required information of Location */
    @Test(priority = 5)
    public void userFillsLocationDetailsTest() throws InterruptedException {
        ef = new EformDetails();
        ef.enterLocationDetails();
    }

    /*TC_08 : This method is used to fill host details */
    @Test(priority = 6)
    public void userFillsRequirementDetailsTest() throws InterruptedException {
        ef = new EformDetails();
        ef.hostName();
    }

    /*TC_09 : This method is used to for remark */
    @Test(priority = 7)
    public void userFillsRemarksTest() throws InterruptedException {
        ef = new EformDetails();
        ef.enterRemark();
    }

    /*TC_10 : This method is used for check box to accpet terms */
    @Test(priority = 8)
    public void userClickOnCheckboxTest() {
        ef.clickOnCheckBox();
    }

    /*TC_11 : This method is used to submit E-form*/
    @Test(priority = 9)
    public void userClickOnSubmitButtonTest() {
        ef.clickOnSubmitbtn();
    }

    @Test(priority = 10)
    public void QuitTest() {
        ca = new LoginForEform();
        ca.tearDown();
    }

}
/*  *//*-----------------------------------Test Scenario TS_02----------------------------------------*//*

 *//*TC_11 : This method is used to click menu Hamburger and view status*//*
    public void userClicksOnViewStatusButton() throws InterruptedException {

        vsp.Clickmenu();
        vsp.ClickViewStatusBtn();

    }

    *//*TC_11 : This method is used to enter Eform number*//*
    public void userFillsEFormNoAs() throws InterruptedException {
        Thread.sleep(3000);
        vsp.EnterEformNO();
    }

    *//*TC_11 : This method is used to submit Eform*//*
    public void userClicksOnSubmitButton() throws InterruptedException {
        Thread.sleep(3000);
        vsp.ClickSubmitBtn();
    }*/



