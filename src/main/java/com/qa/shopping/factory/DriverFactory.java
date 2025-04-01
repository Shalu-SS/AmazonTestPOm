package com.qa.shopping.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



public class DriverFactory {
	
	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;

	
//	WebDriver driver;
	ThreadLocal<WebDriver> tdldriver = new ThreadLocal<WebDriver>();

//	public WebDriver initdriver() {
//
//		ChromeOptions cop = new ChromeOptions();
//		cop.addArguments("--disable-notifications");
//		tdldriver.set(new ChromeDriver(cop));
//		driver = tdldriver.get();
//		driver.get("https://www.amazon.com/");
//		driver.manage().deleteAllCookies();
//		driver.manage().window().maximize();
//		
//		return driver;
//	}

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal();	
	public static boolean alreadyExecuted = false;
	
	/***
	 * This method is used to initialize the driver using browser name
	 * @param browserName
	 * @return this returns Webdriver
	 */
	public WebDriver init_driver(Properties prop) {
		String browserName = prop.getProperty("browser").trim();
		System.out.println("Browser name is " + browserName);
		optionsManager = new OptionsManager(prop);
		if(browserName.equalsIgnoreCase("chrome")) {
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		}
		else {
			System.out.println("Please pass the Correct Driver");
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url").trim());
		return getDriver();
	}

	/***
	 * this method is used to initialize the properties
	 * @return this returns the properties class reference
	 */
	public Properties init_prop() {
		prop = new Properties();
		try {
			FileInputStream ip = new FileInputStream("src\\test\\resources\\config\\config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}
/***
 * 
 * @return local tlDriver
 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}



}
