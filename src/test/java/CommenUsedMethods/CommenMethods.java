package CommenUsedMethods;
   
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

import javax.naming.ConfigurationException;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.javascript.host.file.File;
 
public class CommenMethods {
	private WebDriver driver;
	public String baseUrl;
	static Logger lg = Logger.getLogger(RegisteringNewCustomer.RegisterCustomerTestCases.class.getName());
  
	public CommenMethods(WebDriver driver, String baseUrl) {
		this.driver = driver;
		this.baseUrl = baseUrl; 
	}
 
	public void setDrivers() {
		if (!System.getProperty("os.name").contains("Windows")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.home") + System.getProperty("file.separator") + "geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.home") + System.getProperty("file.separator") + "geckodriver.exe");
		}

		if (!System.getProperty("os.name").contains("Windows")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.home") + System.getProperty("file.separator") + "chromedriver");
		} else {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.home") + System.getProperty("file.separator") + "chromedriver.exe");
		}

	}
 
	public WebDriver setUpDriversWebDrivers() throws Exception {
		/*String Browser = System.getProperty("Test.Browser");
		String Server = System.getProperty("Test.Server");*/
		String Browser = "GoogleChrome";
		String Server = "www.check24.de";
		if (Browser.equalsIgnoreCase("GoogleChrome")) {
			Map<String, Object> preferences = new Hashtable<String, Object>();
			preferences.put("profile.default_content_settings.popups", 0);
			// preferences.put("download.default_directory", downloadFilepath);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("prefs", preferences);
			options.addArguments("--start-maximized");
			driver = new ChromeDriver(options);
		} else if (Browser.equalsIgnoreCase("Firefox")) {
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
		}
		baseUrl = "https://" + Server;
		return driver;
	}
	public  boolean isElementPresent(By by, WebDriver driver) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	public static void sendChar(WebElement element, String value) {
		element.clear();

		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			String s = new StringBuilder().append(c).toString();
			element.sendKeys(s);
		}
	}
	public  void wait(String Locator, String value,WebDriver driver) throws InterruptedException {
	for (int second = 0;; second++) {
		lg.info("wait "+value+", Trial No.: " + second + " \n");

		if (second >= 60) {
			// Write Log in log file
			lg.error(value+" isn't Found.");
			fail();
		}
		try {
			if (isElementPresent(
					By.xpath(Locator),
					driver))
				break;
		} catch (Exception e) {
		}
		Thread.sleep(1000); 
	}
	}
	public  WebDriver waitForPageLoaded(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Throwable error) {
			fail("Timeout waiting for Page Load Request to complete.");
		}
		return driver;
	}
	
	
	public  void click(String Locator, String value,WebDriver driver) {
	try {
		lg.info("Clicking "+value);
		driver.findElement(
				By.xpath(Locator)).click();

	} catch (Exception e) {
		lg.error("Can't Click "+value, e);

		e.printStackTrace();
	}
	}
	
	public  void Verify(String Locator, String value, String name,WebDriver driver) {

	try {

		lg.info("Verifying "+name+ "\n");
		assertEquals(value,driver.findElement(By.xpath(Locator)).getText());
		lg.info("Verification is Successfully done \n");

		} catch (Exception e) {
		lg.info("Verification isn't done \n");
			lg.error("Verification Failed. \n");
			e.printStackTrace();
		}
	}

	
	public String getAlphaNumericString(int n) 
    { 
  
        // chose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++) { 
  
            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index 
                = (int)(AlphaNumericString.length() 
                        * Math.random()); 
  
            // add Character one by one in end of sb 
            sb.append(AlphaNumericString 
                          .charAt(index)); 
        } 
  
        return sb.toString(); 
    } 
	
	
	// getProperty is method for retrieve strings from property file

//	public String getProperty(String property, String FilePath) {
//		String propertyValue = "";
//		InputStream in = null;
//
//		try {
//			Properties props = new Properties();
//			// Try to open File
//			in = new FileInputStream(FilePath);
//			// Load File in RAM
//			props.load(in);
//			// Get the Value of the property
//			propertyValue = props.getProperty(property).trim();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return propertyValue;
//	}
//
//	// updateproperty is method for Updating strings in property file
//
//	public void updateproperty(String FilePath, String key, String value) throws IOException, ConfigurationException {
//		File file = new File(FilePath);
//		PropertiesConfiguration config = new PropertiesConfiguration();
//		PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout(config);
//		layout.load(new InputStreamReader(new FileInputStream(file)));
//		config.setProperty(key, value);
//		layout.save(new FileWriter(FilePath, false));
//	}
//
//	// writeInLogFile is method for write log in file
//
//	public void writeInLogFile(String FilePath, String Log) throws IOException {
//
//		BufferedWriter writer = new BufferedWriter(new FileWriter(FilePath, true));
//		writer.append(Log);
//
//		writer.close();
//	}
//
//	public void EmptyFile(String FilePath, String Log) throws IOException {
//
//		PrintWriter writer = new PrintWriter(FilePath);
//		writer.print(Log);
//		writer.close();
//	}
}
