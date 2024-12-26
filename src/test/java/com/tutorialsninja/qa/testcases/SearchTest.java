package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Selenium.com.PageObject.HomePage;
import Selenium.com.PageObject.SearchPage;
import base.Base;

//Updated Comment - Added more details

public class SearchTest extends Base {
	
	public SearchPage searchPage;
	HomePage homePage;
	
	public SearchTest() {
		super();
	}
	
	public WebDriver driver;
	
	@BeforeMethod
	public void setup() {
		
		driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browser"));
		homePage = new HomePage(driver);
	}
	
	@AfterMethod
	public void tearDown() {
		
		driver.quit();
		
	}
	
	@Test(priority=1)
	public void verifySearchWithValidProduct() throws InterruptedException {
		
		searchPage = homePage.searchForAProduct(prop.getProperty("validProduct"));
		Assert.assertTrue(searchPage.displayStatusOfHPValidProduct(),"Valid product HP is not displayed in the search results");
		Thread.sleep(3000);
	}
	
	@Test(priority=2)
	public void verifySearchWithInvalidProduct() throws InterruptedException {
		
		searchPage = homePage.searchForAProduct(prop.getProperty("invalidProduct"));
		Assert.assertEquals(searchPage.retrieveNoProductMessageText(),"There is no product that matches the search criteria.");
		Thread.sleep(3000);
	}
	
	@Test(priority=3,dependsOnMethods={"verifySearchWithValidProduct","verifySearchWithInvalidProduct"})
	public void verifySearchWithoutAnyProduct() {
		
		searchPage = homePage.clickOnSearchButton();
		Assert.assertEquals(searchPage.retrieveNoProductMessageText(),prop.getProperty("NoProductTextInSearchResults"),"No product message in search results is not displayed");
		
	}
	
}