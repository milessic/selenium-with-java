package tests;

import java.io.IOException;

import pages.InvoicePage;
import utils.JsonDataReader;
import utils.PdfReader;
import utils.Messages;
import utils.DataGenerator;

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
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class InvoiceTests extends SeleniumTestCase
{
	/*
	 * from SeleniumTestCase:
	WebDriver driver;
	InvoicePage invoicePage;
	Messages messages;
	DataGenerator dataGenerator;
	*/


	public InvoiceTests(){
		super();
	}

	@AfterEach
	public void testTeardown(TestInfo testInfo){
		this.performTestTeardown(testInfo);
	}

	@BeforeEach
	public void testSetup(TestInfo testInfo){
		this.performTestSetup(testInfo);
	}

	@Test
	@DisplayName("TC-1 - Invoice page can be opened and has proper title")
	public void testOpenPage(){
		// version 1
		assertEquals("Generator Faktur VAT", invoicePage.getTitle());
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
			String invoiceNumber = dataGenerator.generateInvoiceName();
        	JsonDataReader reader = new JsonDataReader("src/test/resources/invoiceWithOneItem.json");
			PdfReader pdfReader = new PdfReader(System.getProperty("user.dir") + "/faktura_" + invoiceNumber +".pdf");

			// set unique invoice number
			Map <String, String> invoiceHeaders = reader.getHeaders();
			invoiceHeaders.put("invoiceNumber", invoiceNumber);
			// fill form
			invoicePage.fillFormFields(invoiceHeaders);
			invoicePage.fillFormFields(reader.getFrom());
			invoicePage.fillFormFields(reader.getTo());
			invoicePage.fillFormFields(reader.getFooter());
			// fill items
			invoicePage.fillItems(reader.getItems());

			// download invoice
			invoicePage.downloadPdf(messages.getInvoiceGeneratedMessage(invoiceNumber));

			// verify that file is downloaded
			assertTrue(pdfReader.exists(), "Didn't find file 'faktura_" + invoiceNumber + ".pdf'!");
			assertTrue(pdfReader.delete(), "Couldn't delete file 'faktura_" + invoiceNumber + ".pdf!");

    	} catch (IOException e) {
    	    e.printStackTrace();
    	    fail("IOException occurred: " + e.getMessage());
    	}
	}

	@Test
	@DisplayName("TC-5 - Invoice cannot be generated if there are no invoice items")
	public void testInvoiceCannotBeGeneratedIfThereAreNoInvoiceItems(){
		try {
        	JsonDataReader reader = new JsonDataReader("src/test/resources/invoiceWithOneItem.json");
			PdfReader pdfReader = new PdfReader(System.getProperty("user.dir") + "/faktura_2.pdf");
			// fill form
			invoicePage.fillFormFields(reader.getHeaders());
			invoicePage.fillFormFields(reader.getFrom());
			invoicePage.fillFormFields(reader.getTo());
			invoicePage.fillFormFields(reader.getFooter());
			// Do not fill items

			// download invoice
			invoicePage.downloadPdf(messages.getFillAllFieldsMessage());

			// verify that file is not downloaded (it will take some time)
			assertFalse(pdfReader.exists(), "File has been downloaded without items 'faktura_2.pdf'!");

		} catch (IOException e){
			e.printStackTrace();
			fail("IOException occured: " + e.getMessage());
		}

	}
}
