package tests;

import java.io.IOException;

import pages.InvoicePage;
import utils.JsonDataReader;
import utils.PdfReader;
import utils.Messages;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;

/**
 * Unit test for simple App.
 */
public class InvoiceTests
{
	private WebDriver driver;
	private InvoicePage invoicePage;
	private Messages messages;


	@BeforeEach
	public void testSetup(TestInfo testInfo){
		System.out.println("\n=== STARTING TEST: " + testInfo.getDisplayName() + "\n");

		String downloadFilepath = System.getProperty("user.dir");
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.setExperimentalOption("prefs", chromePrefs);

		driver = new ChromeDriver(options);
		invoicePage = new InvoicePage(driver);

		invoicePage.openInvoicePage();

		messages = new Messages();
	}

	@AfterEach
	public void testTeardown(TestInfo testInfo){
		invoicePage.closePage();
		System.out.println("\n=== TEST FINISHED\n--- " + testInfo.getDisplayName());
	}

	@Test
	@DisplayName("TC-1 - Invoice page can be opened and has proper title")
	public void testOpenPage(){
		// version 1
		assertEquals("Generator Faktur VAT",invoicePage.getTitle());
	}

	@Test
	@DisplayName("TC-2 - Proper fields are displayed on page load")
	public void testAllDefaultFormElementsAreDisplayedOnPageLoad(){
		// TopNav
		invoicePage.verifyThatAllTopNavElementsAreVisible();
		// Footer
		invoicePage.verifyThatAllFooterElementsAreVisible();
		// Form fields
		invoicePage.verifyThatAllFormElementsAreVisible();
		// Corrections - not visible
		invoicePage.verifyThatCorrectionFieldAreNotVisible();
	}

	@Test
	@DisplayName("TC-3 - One row is generated on page load")
	public void testThatOneRowIsGeneratedOnLoad(){
		invoicePage.verifyThatOneInvoiceRowIsGeneratedByDefault();
	}

	@Test
	@DisplayName("TC-4 - Invoice can be generated and file name is proper")
	public void testInvoiceCanBeGeneratedAndFileNameIsProper(){
		// version 1
		// load data
		try {
        	JsonDataReader reader = new JsonDataReader("src/test/resources/invoiceWithOneItem.json");
			PdfReader pdfReader = new PdfReader(System.getProperty("user.dir") + "/faktura_1.pdf");
			// fill form
			invoicePage.fillFormFields(reader.getHeaders());
			invoicePage.fillFormFields(reader.getFrom());
			invoicePage.fillFormFields(reader.getTo());
			invoicePage.fillFormFields(reader.getFooter());
			// fill items
			invoicePage.fillItems(reader.getItems());

			// download invoice
			invoicePage.downloadPdf(messages.invoiceGenerated);

			// verify that file is downloaded
			assertTrue(pdfReader.exists(), "Didn't find file 'faktura_1.pdf'!");
			assertTrue(pdfReader.delete(), "Couldn't delete file 'faktura_1.pdf!");




    	} catch (IOException e) {
    	    e.printStackTrace();
    	    fail("IOException occurred: " + e.getMessage());
    	}
	}
}
