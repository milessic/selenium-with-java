package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.By;

public class BasePage {
	protected WebDriver driver;
	protected WebDriverWait wait;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	protected boolean isElementDisplayed(WebElement element){
		try{
			return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
		} catch(Exception e) {
			return false;
		}
	}

	protected void click(WebElement element){
		wait.until(ExpectedConditions.visibilityOf(element)).click();
	}

	public void clickByXpath(By xpath){
		WebElement element = driver.findElement(xpath);
		click(element);
	}

	public void closePage(){
		driver.close();
	}

	protected String getTextById(By id){
		WebElement element = driver.findElement(id);
		return wait.until(ExpectedConditions.visibilityOf(element)).getText();
	}

	protected void fillText( WebElement element, String text ) {
		wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
	}

	public String getTitle(){
		return driver.getTitle();
	}
	public void waitUntilElementByIdIsInvisible(String elementId){
		WebElement element = driver.findElement(By.id(elementId));
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public void waitUntilElementByByIsVisible(By by){
		WebElement element = driver.findElement(by);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitUntilElementByIdIsVisible(String elementId){
		WebElement element = driver.findElement(By.id(elementId));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitUntilElementByXpathIsVisible(String elementXpath){
		WebElement element = driver.findElement(By.xpath(elementXpath));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
}
