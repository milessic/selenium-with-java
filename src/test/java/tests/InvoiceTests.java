package tests;

import pages.InvoicePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Unit test for simple App.
 */
public class InvoiceTests
{
	private WebDriver driver;
	private InvoicePage invoicePage;


	@BeforeEach
	public void testSetup(){
		driver = new ChromeDriver();
		invoicePage = new InvoicePage(driver);

		invoicePage.openInvoicePage();
	}

	@AfterEach
	public void testTeardown(){
		invoicePage.closePage();
	}
	@Test
	public void testOpenPage(){
		assertEquals("Generator Faktur VAT",invoicePage.getTitle());
	}

	@Test
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
	public void testThatOneRowIsGeneratedOnLoad(){
		invoicePage.verifyThatOneInvoiceRowIsGeneratedByDefault();
	}

    /**
     * @return the suite of tests being tested
     */
	/*
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }
	*/

    /**
     * Rigourous Test :-)
     */
	/*
    public void testApp()
    {
        assertTrue( true );
    }
	*/
}
