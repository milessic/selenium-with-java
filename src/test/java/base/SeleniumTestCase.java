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

public class SeleniumTestCase {
	public WebDriver driver;
	public InvoicePage invoicePage;
	public Messages messages;
	public DataGenerator dataGenerator;

	public void performTestSetup(TestInfo testInfo){
		System.out.println("\n=== STARTING TEST: " + testInfo.getDisplayName() + "\n");

		String downloadFilepath = System.getProperty("user.dir");
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.setExperimentalOption("prefs", chromePrefs);

		this.driver = new ChromeDriver(options);
		this.invoicePage = new InvoicePage(driver);

		this.invoicePage.openInvoicePage();

		this.messages = new Messages();

		this.dataGenerator = new DataGenerator();
	}

	public void performTestTeardown(TestInfo testInfo){
		invoicePage.closePage();
		System.out.println("\n=== TEST FINISHED\n--- " + testInfo.getDisplayName());
	}

}
