package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Selenium.com.PageObject.AccountPage;
import Selenium.com.PageObject.HomePage;
import Selenium.com.PageObject.LoginPage;
import Selenium.com.utils.Utilities;
import base.Base;



public class LoginTest extends Base {

	LoginPage loginPage;

	public LoginTest() {
		super();
	}

	public WebDriver driver;

	@BeforeMethod
	public void setup() {

		driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browser"));
		HomePage homePage = new HomePage(driver);
		loginPage = homePage.naviageToLoginPage();

	}

	@AfterMethod
	public void tearDown() {

		driver.quit();

	}

	@Test(priority = 1)
	public void verifyLoginWithValidCredentials() throws InterruptedException {

		AccountPage accountPage = loginPage.login(prop.getProperty("email"), prop.getProperty("password"));
		Assert.assertTrue(accountPage.getDisplayStatusOfEditYourAccountInformationOption(),
				"Edit Your Account Information option is not displayed");

	}

	@Test(priority = 2)
	public void verifyLoginWithInvalidCredentials() {

		loginPage.login(Utilities.generateEmailWithTimeStamp(), prop.getProperty("invalidPassword"));
		Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText().contains(
				prop.getProperty("emailPasswordNoMatchWarning")), "Expected Warning message is not displayed");

	}

	@Test(priority = 3)
	public void verifyLoginWithInvalidEmailAndValidPassword() {

		loginPage.login(Utilities.generateEmailWithTimeStamp(), prop.getProperty("validPassword"));
		Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText().contains(
				prop.getProperty("emailPasswordNoMatchWarning")), "Expected Warning message is not displayed");

	}

	@Test(priority = 4)
	public void verifyLoginWithValidEmailAndInvalidPassword() {

		loginPage.login(prop.getProperty("validEmail"), prop.getProperty("invalidPassword"));
		Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText().contains(
				prop.getProperty("emailPasswordNoMatchWarning")), "Expected Warning message is not displayed");

	}

	@Test(priority = 5)
	public void verifyLoginWithoutProvidingCredentials() {

		loginPage.clickOnLoginButton();
		Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText().contains(
				prop.getProperty("emailPasswordNoMatchWarning")), "Expected Warning message is not displayed");

	}

}