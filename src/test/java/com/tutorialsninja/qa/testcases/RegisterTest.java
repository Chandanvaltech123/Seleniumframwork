package com.tutorialsninja.qa.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Selenium.com.PageObject.AccountSuccessPage;
import Selenium.com.PageObject.HomePage;
import Selenium.com.PageObject.RegisterPage;
import Selenium.com.utils.Utilities;
import base.Base;

public class RegisterTest extends Base {

	public RegisterPage registerPage;
	AccountSuccessPage accountSuccessPage;

	public RegisterTest() {
		super();
	}

	public WebDriver driver;

	@BeforeMethod
	public void setup() throws IOException {

		driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browser"));
		HomePage homePage = new HomePage(driver);
		registerPage = homePage.navigateToRegisterPage();
	}

	@AfterMethod
	public void tearDown() {

		driver.quit();

	}

	// verify Registering An Account With Mandatory Fields
	@Test(priority = 1)
	public void verifyRegisteringAnAccountWithMandatoryFields() throws InterruptedException {

		Thread.sleep(2000);
		accountSuccessPage = registerPage.registerWithMandatoryFields(prop.getProperty("firstName"),
				prop.getProperty("lastName"), Utilities.generateEmailWithTimeStamp(),
				prop.getProperty("telephoneNumber"), prop.getProperty("password"));
		Assert.assertEquals(accountSuccessPage.retrieveAccountSuccessPageHeading(),
				prop.getProperty("accountSuccessfullyCreatedHeading"), "Account Success page is not displayed");

	}

	// verify Registering Account By Providing All Fields
	@Test(priority = 2)
	public void verifyRegisteringAccountByProvidingAllFields() throws InterruptedException {
		Thread.sleep(2000);
		accountSuccessPage = registerPage.registerWithAllFields(prop.getProperty("firstName"),
				prop.getProperty("lastName"), Utilities.generateEmailWithTimeStamp(),
				prop.getProperty("telephoneNumber"), prop.getProperty("password"));
		Assert.assertEquals(accountSuccessPage.retrieveAccountSuccessPageHeading(),
				prop.getProperty("accountSuccessfullyCreatedHeading"), "Account Success page is not displayed");

	}

	@Test(priority = 3)
	public void verifyRegisteringAccountWithExistingEmailAddress() {

		registerPage.registerWithAllFields(prop.getProperty("firstName"), prop.getProperty("lastName"),
				prop.getProperty("email"), prop.getProperty("telephoneNumber"), prop.getProperty("password"));
		Assert.assertTrue(
				registerPage.retrieveDuplicateEmailAddressWarning().contains(prop.getProperty("duplicateEmailWarning")),
				"Warning message regaring duplicate email address is not displayed");

	}

	@Test(priority = 4)
	public void verifyRegisteringAccountWithoutFillingAnyDetails() {

		registerPage.clickOnContinueButton();
		Assert.assertTrue(registerPage.displayStatusOfWarningMessages(prop.getProperty("privacyPolicyWarning"),
				prop.getProperty("firstNameWarning"), prop.getProperty("lastNameWarning"),
				prop.getProperty("emailWarning"), prop.getProperty("telephoneWarning"),
				prop.getProperty("passwordWarning")));

	}
}
