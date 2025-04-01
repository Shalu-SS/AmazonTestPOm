package com.qa.shopping.factory;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import java.time.Duration;

public class JavaScriptUtil {
	WebDriver driver;

	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
	}

	public void flash(WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 15; i++) {
			changeColor("rgb(255,127,80)", element);// 1
			changeColor(bgcolor, element);// 2
		}
	}

	private void changeColor(String color, WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);

		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}

	public String getTitleByJS() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("return document.title;").toString();
	}

	public void refreshBrowserByJS() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("history.go(0)");
	}

	public void generateAlert(String message) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("alert('" + message + "')");
	}

	public String getPageInnerText() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("return document.documentElement.innerText;").toString();
	}

	public void clickElementByJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	public void sendKeysUsingWithId(String id, String value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//document.getElementById('input-email').value = 'naveen@gmail.com'
		js.executeScript("document.getElementById('" + id + "').value='" + value + "'");
	}

	public void scrollPageDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollPageDown(String height) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, '" + height + "')");
	}

	public void scrollPageUp() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
	}

	public void scrollIntoView(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void drawBorder(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	 public  boolean scrollUntilElementFound(String elementXpath, int maxScrolls, int scrollDistance, int timeOut) {
	        // Cast WebDriver to JavascriptExecutor to execute JavaScript
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));


	        int scrollCount = 0;

	        // Try to find the element and scroll if necessary
	        while (scrollCount < maxScrolls) {
	            try {
	                // Try to locate the element
	                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementXpath)));

	                // If element is found, return true
//	                System.out.println("Element found: " + element.getText());
	                return true; // Exit the method once element is found

	            } catch (Exception e) {
	                // If element is not found, scroll down by the specified distance
	                js.executeScript("window.scrollBy(0, " + scrollDistance + ");");

	                // Increment the scroll count
	                scrollCount++;

	                // Optional: Wait a moment to allow page content to load
	                try {
	                    Thread.sleep(1000);  // Adjust the sleep time if necessary
	                } catch (InterruptedException ex) {
	                    ex.printStackTrace();
	                }

	                // Continue scrolling until the element is found or max scrolls are reached
	            }
	        }

	        // If the element was not found after max scrolls, return false
//	        System.out.println("Element not found after " + maxScrolls + " scrolls.");
	        return false;
	    }	
	
	   public void scrollUntilBottom(int scrollDistance, int waitTimeInMillis) {
	        JavascriptExecutor js = (JavascriptExecutor) driver;

	        boolean isBottomReached = false;

	        while (!isBottomReached) {
	            // Scroll the page by the specified scroll distance
	            js.executeScript("window.scrollBy(0, " + scrollDistance + ");");

	            // Wait for new content to load
	            try {
	                Thread.sleep(waitTimeInMillis); // Wait for the content to load (in milliseconds)
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }

	            // Get the current scroll position and the total page height
	            Long currentPosition = (Long) js.executeScript("return window.scrollY;");
	            Long pageHeight = (Long) js.executeScript("return document.body.scrollHeight;");

	            // Check if the current scroll position + window height is greater than or equal to the page height
	            if (currentPosition + (Long) js.executeScript("return window.innerHeight;") >= pageHeight - 150) {
	                isBottomReached = true;
	            }
	        }
	    }
	   public void scrollUntilElementVisible(By elementLocator, int scrollDistance, int waitTimeInMillis) {
	        JavascriptExecutor js = (JavascriptExecutor) driver;

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        
	        while (true) {
	            // Scroll the page by the specified scroll distance
	            js.executeScript("window.scrollBy(0, " + scrollDistance + ");");

	            // Wait for the page to load
	            try {
	                Thread.sleep(waitTimeInMillis); // Adjust the time if necessary
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }

	            // Check if the element is visible
	            try {
	                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
	                if (element.isDisplayed()) {
	                    System.out.println("Element is visible.");
	                    break;  // Exit the loop if the element is visible
	                }
	            } catch (Exception e) {
	                // Continue scrolling if the element is not found yet
	                // You can log this exception or just continue scrolling
	            }
	        }
	    }
}
