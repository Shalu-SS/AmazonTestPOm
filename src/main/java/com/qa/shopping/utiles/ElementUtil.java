package com.qa.shopping.utiles;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;

import com.qa.shopping.factory.JavaScriptUtil;


public class ElementUtil {

	
	private WebDriver driver;
	private  JavaScriptUtil jsUtil;
//========================================================================
/***
 * 
 * @param locatorType
 * @param locatorValue
 * @return
 */
	public By getBy(String locatorType, String locatorValue) {
		By locator=null;
		switch (locatorType.toLowerCase()) {
		case "id":
			locator=By.id(locatorValue);
			break;
		case "name":
			locator=By.name(locatorValue);
			break;
		case "classname":
			locator=By.className(locatorValue);
			break;
		case "xpath":
			locator=By.xpath(locatorValue);
			break;
		case "csss":
			locator=By.cssSelector(locatorValue);
			break;
		case "linktext":
			locator=By.linkText(locatorValue);
			break;
		case "partiallinktext":
			locator=By.partialLinkText(locatorValue);
			break;
		case "tagname":
			locator=By.tagName(locatorValue);
			break;

		default:
			break;
		}			
		return locator;
		
	}
//=====================================================================================================================================
	
	public ElementUtil(WebDriver driver) {
		this.driver=driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	
	/***
	 * 
	 * @param locator
	 * @return
	 */
	public WebElement getElement(By locator) {
		WebElement ele = driver.findElement(locator);
		return ele;
	}
	/*
	public WebElement getElement(By locator) {
		return driver.findElement(locator);
	}
*/

//=========================Getattribute============================================================================================================
	public String doGetAttribute(By locator, String attribute) {
		return getElement(locator).getAttribute(attribute);
	}	

//=========================GetText===================================================================================================================
public String doGetText(By locator) {
	return getElement(locator).getText();
}	
	
//=========================Click===========================================================================================================--
	public void doClick(By locator) {
		getElement(locator).click();
	}
	public void doClick(String locatorType,String locatorValue) {
		getElement(getBy(locatorType,locatorValue)).click();
	}
	
//=========================SendKeys=====================================================================================================
	public void doSendKeys(By locator, String Value) {
		WebElement ele = getElement(locator);
		ele.clear();
		getElement(locator).sendKeys(Value);
	}
	public void doSendKeys(String locatorType, String locatorValue, String Value) {
		getElement(getBy(locatorType,locatorValue)).sendKeys(Value);
	}

//=========================IsDisplayed==================================================================================================

public boolean doIsdisplyed(By locator) {
	return getElement(locator).isDisplayed();
}
	
//=========================IsEnabled============================================================================================================--

public boolean doIsEnabled(By locator) {
	return getElement(locator).isEnabled();
}

//========================-ListWebElements================================================================================================


public List<WebElement> getElements(By locator) {
	return driver.findElements(locator);
}

//=========================Total Number of items====================================================================================
/***
 * Total Number of items
 * @param locator
 * @return
 */
public int getElementCount(By locator) {
	return getElements(locator).size();
}
//=========================List of Item should be print========================================================================-----
public void printElementsText(By loacatlor) {
	List<WebElement> eleList = getElements(loacatlor);
	for(WebElement e:eleList) {
		String text = e.getText();
		System.out.println(text);
	}
}

//=========================List of Item inside list and inside array list========================================================================-----
/***
 * This method will return the List of element's text
 * 
 * @param locator
 * @return
 */
public List<String> getElementTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();
		for(WebElement ele: eleList) {
			String text = ele.getText();
			if(!text.isEmpty()) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
}

//=========================List of Item inside list and inside array list========================================================================-----

/***
 * This method will return the List of element's attribute
 * @param locator
 * @param attrName
 * @return
 * @exception
 * By images = By.tagName("img");
 * System.out.println(Elem.getElementAttributeList(images, "src"));
 */

public List<String> getElementAttributeList(By locator, String attrName){
	List<WebElement> eleList = getElements(locator);
	List<String> eleAttrList = new ArrayList<String>();
	for(WebElement ele:eleList) {
		String attrval = ele.getAttribute(attrName);
		eleAttrList.add(attrval);
	}

	return eleAttrList;
}
/**
 * An expectation for checking that there is at least one element present on a web page.
 * @param locator
 * @param timeOut
 * @return
 */
public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
}

/**
 * An expectation for checking that all elements present on the web page that match the locator are visible.
 *  Visibility means that the elements are not only displayed 
 *  but also have a height and width that is greater than 0.
 * @param locator
 * @param timeOut
 * @return
 */
public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
}



//=========================Click the link from a website--============================================================================
/***
 * 
 * @param locator
 * @param linkText
 */
public void clickOnLink(By locator, String linkText) {
	List<WebElement> eleList = getElements(locator);
//	System.out.println("Check");
//	List<String> eleTextList = new ArrayList<String>();
	for(WebElement ele: eleList) {
		String text = ele.getText();
		System.out.println(text);
		if(text.contains(linkText)) {
			ele.click();
		}
	}
}

//=========================DropDown Util==============================================================================================
/***
 * Select DropDown ByIndex
 * Elem.doSelectDropDownByIndex(country, 5);
 * @param locator
 * @param index
 */

public void doSelectDropDownByIndex(By locator,int index){
	Select select =new Select(getElement(locator));
	select.selectByIndex(index);
}


//=========================DropDown Util==============================================================================================

/***
 * By country = By.id("Form_getForm_Country");
 * Elem.doSelectDropDownByVisibleText(country, "India"); 
 * @param locator
 * @param VisibleText
 */
public void doSelectDropDownByVisibleText(By locator,String VisibleText){
	Select select =new Select(getElement(locator));
	select.selectByVisibleText(VisibleText);
}
//=========================DropDown Util==============================================================================================

/***
 * By country = By.id("Form_getForm_Country");
 * Elem.doSelectDropDownByValue(country, "India");
 * @param locator
 * @param value
 */
public void doSelectDropDownByValue(By locator,String value){
	Select select =new Select(getElement(locator));
	select.selectByValue(value);
}


//=========================DropDown Util but no <html> select==============================================================================================

/***
 * This is used to select the dropdown created using jQuery not by <html> select
 * By xpath = By.xpath("//*[@id=\"justAnInputBox\"]");
 * Elem.doClick(xpath);
 * By country = By.xpath("//span[@class='comboTreeItemTitle']");
 * Elem.doSelectChoice(country,"all");
 * Elem.doSelectChoice(country, "choice 1", "choice 2", "choice 2 1");
 * @param locator
 * @param value
 */
public void doSelectChoice(By locator,String...value){
	List<WebElement> choicesList = getElements(locator);
	boolean flag = false;
    ArrayList<Object> wrongEntry = new ArrayList<Object>(Arrays.asList(value));
    ArrayList<Object> CorrectEntry = new ArrayList<Object>();
	if(!value[0].equalsIgnoreCase("all")){
		for(WebElement eles:choicesList) {
			String txt = eles.getText();
//			System.out.println(txt);
			for(int i=0; i < value.length;i++) {
				if(txt.equals(value[i])) {
					flag = true;
					eles.click();
					wrongEntry.remove(txt);
					CorrectEntry.add(txt);
					break;
				}
				else {
					flag = false;
				}
			}
		}
	}
	else { 
		try {
		flag = true;
		for(WebElement eles:choicesList) {
			eles.click();
		}
		}
		catch (ElementNotInteractableException e) {
			// TODO: handle exception
			System.out.println("Elements are over");
		}
		}
	if (flag==false && !wrongEntry.isEmpty()) {
		System.out.println("Wrong Choice are "+wrongEntry);
		System.out.println("CorrectEntry Choice are "+CorrectEntry);
	}
	}
	
//=========================Move Element==============================================================================================
/***
 * String classname = "menulink";
 * Elem.MoveTo("classname", classname);
 * @param locatorType
 * @param locatorValue
 */
public void MoveTo(String locatorType,String locatorValue) {
	WebElement contentParaent = getElement(getBy(locatorType,locatorValue));
	Actions act = new Actions(driver);
	act.moveToElement(contentParaent).build().perform();
}	

/***
 * Not yet completed need to fix some error
 * @param locatorType
 * @param locatorValue
 * @param xval
 * @param yval
 */
public void MoveToLoaction(String locatorType,String locatorValue, int xval, int yval) {
	WebElement contentParaent = getElement(getBy(locatorType,locatorValue));
	Actions act = new Actions(driver);
	act.moveByOffset(xval, yval).perform();
}	

public void ActionClick(String locatorType,String locatorValue) {
	WebElement contentParaent = getElement(getBy(locatorType,locatorValue));
	Actions act = new Actions(driver);
    act.moveToElement(contentParaent).perform();
//    wait.until(ExpectedConditions.visibilityOf(linkOne));
	act.moveToElement(contentParaent).click().perform();
}	

public void MoveToClick(String locatorType,String locatorValue) {
	WebElement contentParaent = getElement(getBy(locatorType,locatorValue));
	Actions act = new Actions(driver);
	act.moveToElement(contentParaent).click().build().perform();
}
public void PressENTERKeys(String locatorType,String locatorValue) {
	WebElement contentParaent = getElement(getBy(locatorType,locatorValue));
	Actions act = new Actions(driver);
	act.moveToElement(contentParaent).click().sendKeys(Keys.ENTER).perform();
}

public void WaitMoveToClick(String locatorType, String locatorValue, int timeout) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));  // Set timeout (10 seconds)
    WebElement contentParent = wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(locatorType, locatorValue)));
    Actions act = new Actions(driver);
    act.moveToElement(contentParent).click().build().perform();
}
public void LowerPrice(String locatorType, String locatorValue, int timeout,int numberClick) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    WebElement contentParent = wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(locatorType, locatorValue)));
    Actions act = new Actions(driver);
    act.sendKeys(Keys.ARROW_LEFT).perform();
    for (int i = 0; i < numberClick; i++) {
//    	act.contextClick(elementToRightClick).perform();
    	act.sendKeys(Keys.ARROW_RIGHT).perform();
        String text = contentParent.getText();
//        System.out.println(text);
        String result = text.replace("$", "");
        int number = Integer.parseInt(result);
        if (number > 49) {
        	break;
        }

    // Optional: You can add a small delay between the clicks if needed
    try {
		Thread.sleep(100);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  // Delay of 500ms between each right-click (adjust as needed)
}
}


public void UpperPrice(String locatorType, String locatorValue, int timeout,int numberClick) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    WebElement contentParent = wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(locatorType, locatorValue)));
    Actions act = new Actions(driver);
    act.sendKeys(Keys.ARROW_LEFT).perform();
    for (int i = 0; i < numberClick; i++) {
//    	act.contextClick(elementToRightClick).perform();
    	act.sendKeys(Keys.ARROW_LEFT).perform();
        String text = contentParent.getText();
//        System.out.println(text);
        String result = text.replace("$", "").replace(",", "");;
        int number = Integer.parseInt(result);
        if (number <= 201) {
        	break;
        }

    // Optional: You can add a small delay between the clicks if needed
    try {
		Thread.sleep(50);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  // Delay of 500ms between each right-click (adjust as needed)
}
}
//=========================Maxwindow==============================================================================================

/***
 * Maxwindow
 */
public void Maxwindow() {
	driver.manage().window().maximize();
}
//=========================FileUpload==============================================================================================

public void fileUpload(String locatorType, String locatorValue, String fullPath) {
	doSendKeys(locatorType,locatorValue,fullPath);
}

//=========================Alert Accept==============================================================================================
public void AlertDismiss() {
	driver.switchTo().alert().accept();
}

//=========================Alert Dismiss==============================================================================================
public void alertDissmiss() {
	driver.switchTo().alert().dismiss();;
}
//=========================Window Switch to Child==============================================================================================

public String switchtochildwindow() {
	Set <String> handler =driver.getWindowHandles();
	Iterator <String> it = handler.iterator();
	String paraentWindowId = it.next();
//	System.out.println("Parent Window Id" + paraentWindowId);
	String chileWindowId = it.next();
//	System.out.println("chileWindowId " + chileWindowId);
	driver.switchTo().window(chileWindowId);
	return paraentWindowId;
}
//=========================Window Switch to Parent==============================================================================================
public void switchtoTypestParentWindow(String parentId) {
	driver.switchTo().window(parentId);
}
public WebElement WaitForElementPresence(By locator, int timeout) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
	return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
}

/***
 * By emailID = By.id("input-email");
 * String id = "input-email";
 * Elem.WaitForElementVisible(emailID, 10).sendKeys("SSSSSSSSSSSS");
 * @param locator
 * @param timeout
 * @return
 */

public WebElement WaitForElementVisible(By locator, int timeout) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
	return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
}

/***
 * An expectation for checking that an element is present on the DOM of a page and 
 * visible.Visibility means that the element is not only 
 * displayed but also has a height and width that isgreater than 0.
 * String id = "input-email";
 * Elem.newWaitForElementVisible("id",id, 10).sendKeys("SSSSSSSSSSSS");
 * @param locatorType
 * @param locatorValue
 * @param timeout
 * @return
 */
public WebElement newWaitForElementVisible(String locatorType, String locatorValue, int timeout) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
	return wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(locatorType,locatorValue)));
}


public boolean WaitForPageTitle(String titleVal, int timeout) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
	return wait.until(ExpectedConditions.titleContains(titleVal));
}

public boolean WaitForActTitle(String titleVal, int timeout) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
	return wait.until(ExpectedConditions.titleIs(titleVal));
}

public String doGetPageTitle(String titleVal, int timeout) {
	if(WaitForPageTitle(titleVal, timeout)) {
		return driver.getTitle();
	}
	return null;

}

public String waitForURLContains(String urlFraction, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	try {
		if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
			return driver.getCurrentUrl();
		} else {
			System.out.println(urlFraction + " url value is not present...");
			return null;
		}
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println(urlFraction + " url value is not present...");
		return null;
	}
}


public WebElement waitForElementPresntUsingFluentWait(By locator, int timeOut, int pollingTime) {
	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			.withTimeout(Duration.ofSeconds(timeOut))
			.pollingEvery(Duration.ofSeconds(pollingTime))
			.ignoring(NoSuchElementException.class)
			.ignoring(StaleElementReferenceException.class)
			.withMessage("Element is not found....");
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

}

public WebElement waitForElementUsingWebDriverWait(By locator, int timeOut, int pollingTime) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	wait
	.pollingEvery(Duration.ofSeconds(pollingTime))
	.ignoring(NoSuchElementException.class)
	.ignoring(StaleElementReferenceException.class)
	.withMessage("Element is not found....");

	return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

}

public boolean checkElementsCount(By locator, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	try {
		Thread.sleep(1000);
	} catch (Exception e) {
		System.out.println("List Error");
		// TODO Auto-generated catch block
	}
	List<WebElement> list = getElements(locator);
	if (list.isEmpty()) {
        System.out.println("The list is empty.");
		return false;
	} else {
		return true;
	}
}

public void ALlDetails(int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='a-section a-spacing-small a-spacing-top-small']")));
    List<WebElement> colInnerDivs = driver.findElements(By.xpath("//div[@class='a-section a-spacing-small a-spacing-top-small']"));

    for (WebElement colDiv : colInnerDivs) {
        try {
            // Extract title, price, and rating (if available) for each div
            WebElement titleElement = colDiv.findElement(By.cssSelector("h2"));
            WebElement ratingElement = colDiv.findElement(By.cssSelector("span.a-size-small.a-color-base"));
            WebElement priceElement = colDiv.findElement(By.cssSelector("span.a-price-whole"));

            String title = titleElement != null ? titleElement.getText() : "No title";

            
//            String rating = ratingElement.getText();
            String rating = ratingElement != null ? ratingElement.getText() : "No rating";

            String price = priceElement != null ? priceElement.getText() : "Price not available";
        }
        catch (Exception e) {
			// TODO: handle exception
		}
        }
	
}





















}
