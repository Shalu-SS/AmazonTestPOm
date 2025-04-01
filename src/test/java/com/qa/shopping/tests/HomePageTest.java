package com.qa.shopping.tests;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;



public class HomePageTest extends BaseTest{
	
	
	@Test(priority = 1)
	public void loginPageTitleTest() throws Exception {
		boolean checkLoginStatus  = hp.loginPage(prop.getProperty("username").trim(),prop.getProperty("password").trim());
		Assert.assertTrue(checkLoginStatus);
	}

	@Test(priority = 2)
	public void doSearchItem() {
		boolean searchresult = hp.searchItem();
		Assert.assertTrue(searchresult);

	}
	@Test(priority = 3)
	public void doSearchItemClick() {
		hp.doFilter();
		hp.searchitemClickAndSaveCSV();

	}
	@Test(priority = 4)
	public void moveTop() {
		boolean checktitle=hp.firstrowdata();
		Assert.assertTrue(checktitle);

	}
	
	
	
}
