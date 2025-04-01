package com.qa.shopping.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.shopping.factory.JavaScriptUtil;
import com.qa.shopping.utiles.Constants;
import com.qa.shopping.utiles.ElementUtil;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	
	private WebDriver driver;
	private ElementUtil eleutil;
	private JavaScriptUtil jsutil;
	
	private By siginInLink = By.id("nav-link-accountList");//input[@id='ap_email']
	private By emailLogIN = By.id("ap_email");
	private By continueButton = By.id("continue");
	private By passwordTxt = By.id("ap_password");
	private By submitButton = By.id("signInSubmit");
	private By loginTxt = By.xpath("//span[@id='nav-link-accountList-nav-line-1']");
	
	
	private By searchtextbox = By.id("twotabsearchtextbox");
	private By searchtexitem = By.xpath("//div[@role='listitem']");
	
	private By newsearchtexitem = By.xpath("(//div[@class='a-section a-spacing-small a-spacing-top-small'])");
	
	private By Resultsitem = By.xpath("//h2[normalize-space()='Results']");
	
	private By reviewLink = By.xpath("(//a[@class='a-link-emphasis a-text-bold'][normalize-space()='See more reviews'])[1]");
	
	private By starClick = By.xpath("//div[@id='reviewsRefinements']//span[@class='a-size-small a-color-base'][normalize-space()='& Up']");
	
//	private By lowprice = By.xpath("//span[normalize-space()='$0']");
	
	private By lowprice = By.xpath("(//span[@class='a-size-base a-color-base a-text-bold'])[1]");
	private By upprice = By.xpath("(//span[@class='a-size-base a-color-base a-text-bold'])[3]");
	private By goButtonClick = By.xpath("//input[@aria-label='Go - Submit price range']");
	
	private By nextButton = By.xpath("//a[normalize-space()='Next']");
	
	private By FirstTitle = By.xpath("(//div[@class='a-section a-spacing-small a-spacing-top-small'])[1]//h2[1]");
	private By ExpectedTitle = By.xpath("//h1[@class='a-size-large a-spacing-none']");
     private static String TESt_DATA_CSV = "src\\main\\resources\\csv\\data.csv";

//	listitem
	
	
	public HomePage(WebDriver driver) {
		this.driver=driver;
		eleutil = new ElementUtil(driver);
		jsutil = new JavaScriptUtil(driver);

	}
	
	public boolean loginPage(String userName, String password) throws InterruptedException {
		eleutil.WaitForElementVisible(siginInLink, Constants.DEFAULT_TIME_OUT_FIRSTPAGE);
		eleutil.ActionClick("id", "nav-link-accountList");
		eleutil.doSendKeys(emailLogIN, userName);
		eleutil.doClick(continueButton);
		eleutil.doSendKeys(passwordTxt, password);
		eleutil.doClick(submitButton);
		String txt = eleutil.doGetText(loginTxt);
		if (txt.contains("Hello")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean searchItem() {
		eleutil.doSendKeys(searchtextbox, Constants.SEARCHITEM);
		eleutil.PressENTERKeys("id", "twotabsearchtextbox");
		boolean items = eleutil.checkElementsCount(searchtexitem, Constants.DEFAULT_TIME_OUT);
		System.out.println("items " + items);
		if (items) {
			return true;
		} else {
			return false;
		}

	}
	
	
	public void doFilter() {
		//div[@id='averageCustomerReviews_feature_div']//span[@class='a-size-small a-color-base'][normalize-space()='4.5']
		eleutil.WaitForElementPresence(starClick, 50);
		eleutil.doClick(starClick);
		
		eleutil.WaitForElementPresence(lowprice, 50);
		eleutil.doClick(lowprice);
		eleutil.LowerPrice("xpath", "(//span[@class='a-size-base a-color-base a-text-bold'])[1]", 50, 47);
		

		eleutil.WaitForElementPresence(upprice, 50);
		eleutil.doClick(upprice);
		eleutil.UpperPrice("xpath", "(//span[@class='a-size-base a-color-base a-text-bold'])[3]", 50, 400);
		
		eleutil.WaitForElementPresence(goButtonClick, Constants.DEFAULT_TIME_OUT);
		eleutil.doClick(goButtonClick);


		
	}
	public void searchitemClickAndSaveCSV() {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

	        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[@class='a-section a-spacing-small a-spacing-top-small'])")));
	        jsutil.scrollUntilBottom(100, 400);
	        eleutil.WaitForElementPresence(nextButton, 50);
	        List<WebElement> colInnerDivs = driver.findElements(By.xpath("(//div[@class='a-section a-spacing-small a-spacing-top-small'])"));

	        System.out.println("Number of elements found: " + colInnerDivs.size());
	        int maxItemsPerPage = 0;
	        /*
	        List<String> titles = new ArrayList<>();
	        List<String> ratings = new ArrayList<>();
	        List<String> prices = new ArrayList<>();
	        List<String> offers = new ArrayList<>();*/
	        List<String> titleRatingList = new ArrayList<>();

	        for (WebElement colDiv : colInnerDivs) {
	            try {
	            	  maxItemsPerPage = maxItemsPerPage+1;
	    			  String newItemXapth= "//div[@role='listitem']["+maxItemsPerPage+"]";				  
	            	  jsutil.scrollUntilElementFound(newItemXapth, 10, 100, Constants.DEFAULT_TIME_OUT);	            	  
	                WebElement titleElement = colDiv.findElement(By.cssSelector("h2"));
	                WebElement ratingElement = colDiv.findElement(By.cssSelector("span.a-size-small.a-color-base"));
	                
	                String price = "empty";
	                String offer = "empty";
	                String reviews = "empty";
	                String resultTitle = titleElement != null ? titleElement.getText() : "No title";
	                String rating = ratingElement != null ? ratingElement.getText() : "No rating";
	                String title = resultTitle.replaceAll(",", "");

//	                
	                
	                try {
	                    WebElement reviewElement = colDiv.findElement(By.xpath(".//span[@class='a-size-small puis-normal-weight-text s-underline-text']"));
	                    reviews = (reviewElement != null && !reviewElement.getText().trim().isEmpty())
	                            ? reviewElement.getText()
	                            : "empty"; // Add "empty" if the price is not available or is empty
//	                    offers.add(offer);
	                } catch (Exception e) {
//	                	offers.add("empty");
	                }

	                try {
	                    WebElement priceElement = colDiv.findElement(By.xpath(".//span[@class='a-price-whole']"));
	                    price = (priceElement != null && !priceElement.getText().trim().isEmpty())
	                            ? priceElement.getText()
	                            : "empty"; // Add "empty" if the price is not available or is empty
//	                    prices.add(price);
	                } catch (Exception e) {
//	                    prices.add("empty");
	                }
	                try {
	                    WebElement offerElement = colDiv.findElement(By.xpath(".//span[@class='a-color-base' and contains(text(), '$')]"));
	                    offer = (offerElement != null && !offerElement.getText().trim().isEmpty())
	                            ? offerElement.getText()
	                            : "empty"; // Add "empty" if the price is not available or is empty
//	                    offers.add(offer);
	                } catch (Exception e) {
//	                	offers.add("empty");
	                }
//	                titles.add(title);
//	                titles.add(""); // First blank line
//	                titles.add("");
//	                ratings.add(rating);
//	                ratings.add("");
//	                ratings.add("");
	                titleRatingList.add("Title: " +"\"" + title + "\"" + ", Rating: " + rating + ", price: "+price+ ", Offers: "+ offer + ", Reviews: " +reviews+"\n");
	            } catch (Exception e) {
	                System.out.println("Error extracting details from a div: " + e.getMessage());
	            }
	        }
	        // Printing all the extracted details
	        System.out.println("Titles and Ratings:");
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TESt_DATA_CSV, false))) { 
	        for (String entry : titleRatingList) {
	            System.out.print(entry);
	            writer.write(entry);
                writer.newLine();
	        }
	        System.out.println("Data has been written to " + TESt_DATA_CSV);
	        } catch (IOException e) {
	            System.out.println("An error occurred while writing the CSV file: " + e.getMessage());
	        }
/*	        
 * 	        System.out.println("Extracted Titles:");
for (String title : titles) {
	            System.out.println(title);
	        }

	        System.out.println("\nExtracted Ratings:");
	        for (String rating : ratings) {
	            System.out.println(rating);
	        }

	        System.out.println("\nExtracted Prices:");
	        for (String offs : offers) {
	            System.out.println(offs);
	        }*/
	    }
//	(//div[@class='a-section a-spacing-small a-spacing-top-small'])[8]	
	public boolean firstrowdata() {
		eleutil.WaitForElementPresence(nextButton, 50);
		jsutil.scrollPageUp();
		eleutil.WaitForElementPresence(FirstTitle, 50);
		String fiTitle = eleutil.doGetText(FirstTitle);
		System.out.println(fiTitle);
		eleutil.doClick(FirstTitle);
		eleutil.WaitForElementPresence(ExpectedTitle, Constants.DEFAULT_TIME_OUT);
		String newExpectedTitle= eleutil.doGetText(ExpectedTitle);
		if (fiTitle.equals(newExpectedTitle)) {
            System.out.println("str1 and str2 are equal.");
            return true;
        } else {
            System.out.println("str1 and str2 are NOT equal.");
            return false;
        }
		
		

		
		
	}

}
	
