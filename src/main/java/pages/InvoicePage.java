package pages;

import utils.ConfigReader;
import utils.InvoiceItem;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class InvoicePage extends BasePage{
	String [] xpathsOfTopBarElements = {
		"//h2[text()='Generator Faktur']",
		"//button[@id='btn-pdf']",
		"//button[@id='show-example']",
	};

	String [] xpathsOfFooterElements = {
		"//button[@id='importJson']",
		"//button[@id='exportJson']",
		"//a[@href='https://github.com/milessic/fv' and text()='fv on GitHub']"
	};

	String [] idsOfCorrectionFields = {
		"correctionReason",
		"correctedDocument",
	};

	String [] idsOfInvoiceHeaders = {
		"isMetodaKasowa",
		"isCorrection",
		"invoiceNumber",
		"invoiceDate",
		"dateOfSales",
		"paymentTerm",
		"paymentMethod",
		"bankAccountNumber",
		"authorizedPerson",
		"note",
	};

	String [] idsOfSeller = {
		"fromName",
		"fromName2",
		"fromStreet",
		"fromCity",
		"fromNip",
		"fromRegon",
		"fromKrs",
		"fromCountry"
	};

	String [] idsOfBuyer = {
		"toName",
		"toName2",
		"toStreet",
		"toCity",
		"toNip",
		"toKrs",
		"toCountry"
		};
	private final By isMetodaKasowa = By.id("isMetodaKasowa");
	private final By isCorrection = By.id("isCorrection");
	private final By invoiceNumber = By.id("invoiceNumber");
	private final By invoiceDate = By.id("invoiceDate");
	private final By dateOfSales = By.id("dateOfSales");
	private final By paymentTerm = By.id("paymentTerm");
	private final By paymentMethod = By.id("paymentMethod");
	private final By bankAccountNumber = By.id("bankAccountNumber");

	private final By correctionReason = By.id("correctionReason");
	private final By correctedDocument = By.id("correctedDocument");
	private final By authorizedPerson = By.id("authorizedPerson");
	private final By note = By.id("note");

	private final By fromName = By.id("fromName");
	private final By fromName2 = By.id("fromName2");
	private final By fromStreet = By.id("fromStreet");
	private final By fromCity = By.id("fromCity");
	private final By fromNip = By.id("fromNip");
	private final By fromRegon = By.id("fromRegon");
	private final By fromKrs = By.id("fromKrs");
	private final By fromAddressCountry = By.id("fromAddressCountry");

	private final By toName = By.id("toName");
	private final By toName2 = By.id("toName2");
	private final By toStreet = By.id("toStreet");
	private final By toCity = By.id("toCity");
	private final By toNip = By.id("toNip");
	private final By toKrs = By.id("toKrs");
	private final By toCountry = By.id("toCountry");

	private final By pdfStatusId = By.id("pdf-status");
	private final By addNewItemXpath = By.xpath("//button[@id='addNewItem']");
	private final By downloadPdfXpath = By.xpath("//button[@id='btn-pdf']");



	public InvoicePage(WebDriver driver) {
		super(driver);
	}

	public void openInvoicePage() {
		String url = ConfigReader.getProperty("url");
		driver.get(url);
	}


	public void fillFormFields(Map<String, String> formData){
		for (Map.Entry<String, String> entry : formData.entrySet()){
			WebElement element = driver.findElement(By.id(entry.getKey()));
			wait.until(ExpectedConditions.visibilityOf(element));
			fillText(element, entry.getValue());
		}
	}

	public void fillItems(ArrayList<InvoiceItem> itemsData){
		// iterate over itemsData
		for (int i = 0; i < itemsData.size() ; i++){
			// click add new item button
			if ( i > 0 ) {
				clickByXpath(addNewItemXpath);
			}
			// iterate over each field
			for (Map.Entry<String, String> entry : itemsData.get(i).getAsMap().entrySet()){
				fillText(getItemWebElement(entry.getKey(), i), entry.getValue());
			}
		}
		System.out.println("\tFilled " + itemsData.size() + " items");
	}
	public void verifyThatAllTopNavElementsAreVisible(){
		System.out.println("-Checking TopNav section");
		for ( String elementId : this.xpathsOfTopBarElements){
			this.waitUntilElementByXpathIsVisible(elementId);
		}
	}

	public void verifyThatAllFooterElementsAreVisible(){
		System.out.println("-Checking Footer section");
		for ( String elementId : this.xpathsOfFooterElements){
			this.waitUntilElementByXpathIsVisible(elementId);
		}
	}

	public void verifyThatAllFormElementsAreVisible(){
		this.waitUntilElementByByIsVisible(isMetodaKasowa);
		// Headers
		System.out.println("-Checking Headers section");
		for ( String elementId : this.idsOfInvoiceHeaders){
			this.waitUntilElementByIdIsVisible(elementId);
		}
		// Seller
		System.out.println("-Checking Seller section");
		for ( String elementId : this.idsOfSeller){
			this.waitUntilElementByIdIsVisible(elementId);
		}
		// Buyer
		System.out.println("-Checking Buyer section");
		for ( String elementId : this.idsOfBuyer){
			this.waitUntilElementByIdIsVisible(elementId);
		}
		System.out.println("\tAll form fields verified properly!");

	}


	public void verifyThatCorrectionFieldAreNotVisible(){
		System.out.println("-Checking that Correction fields are not displayed");
		for ( String elementId : this.idsOfCorrectionFields ){
			this.waitUntilElementByIdIsInvisible(elementId);
		}
		System.out.println("\tCorrection fields are not displayed");
	}

	public void verifyThatOneInvoiceRowIsGeneratedByDefault(){
		verifyThatCountOfInvoiceRowsIs(1);
	}

	public void verifyThatCountOfInvoiceRowsIs(int numOfRows){
		System.out.println("-Checking that " + numOfRows + " row is generated");
		int numOfElements = this.getNumberOfVisibleRows();
		if ( numOfElements != 1 ){
			throw new RuntimeException("Found " + numOfElements + " rows, but expected 1!");
		}
		System.out.println("\t" + numOfRows +"  row is generatd");
	}

	public int getNumberOfVisibleRows(){
		return driver.findElements(By.xpath("//tbody[@id='itemsTable']/tr")).size();
	}

	private WebElement getItemWebElement(String className, int i){
		return driver.findElement(By.xpath("(//*[@class='" + className + "'])[" + i+1 + "]"));
	}

	public void downloadPdf(String expectedMessage){
		this.clickByXpath(downloadPdfXpath);
		this.waitUntilMessageIs(expectedMessage);

	}

	public void waitUntilMessageIs(String expectedMessage){
		this.waitUntilElementByXpathIsVisible("//p[@id='pdf-status' and text()=\"" + expectedMessage + "\"]");
		System.out.println("Status was as expected: '" + expectedMessage + "'");

	}

}
