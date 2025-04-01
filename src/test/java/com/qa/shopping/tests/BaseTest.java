package com.qa.shopping.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.qa.shopping.factory.DriverFactory;
import com.qa.shopping.pages.HomePage;

public class BaseTest {
	WebDriver driver;
	DriverFactory df;
	Properties prop;
	HomePage hp;

	@BeforeTest
	public void setup() {
		df = new DriverFactory();
		prop = df.init_prop();
		driver = df.init_driver(prop);
		hp = new HomePage(driver);

	}

	
	
	@AfterTest
	public void teardown() {
		// driver.quit();

	}

}
