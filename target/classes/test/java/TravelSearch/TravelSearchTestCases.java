package TravelSearch;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import CommenUsedMethods.CommenMethods;
import net.bytebuddy.asm.Advice.Enter;
 
public class TravelSearchTestCases {
	private static WebDriver driver;
	static Logger lg = Logger.getLogger(RegisteringNewCustomer.RegisterCustomerTestCases.class.getName());
	String baseUrl = "";
	CommenMethods CM = new CommenMethods(driver, baseUrl);
	static Date currentDate = new Date();
	static long time = currentDate.getTime();
	
	@BeforeTest
	public void setUp() throws Exception {
		CommenMethods CM = new CommenMethods(driver, baseUrl);
		CM.setDrivers();
		driver = CM.setUpDriversWebDrivers();
		System.out.println("CM.baseUrl" + CM.baseUrl);
		baseUrl = CM.baseUrl;
		driver.get(baseUrl);
		// wait for cookie Alert to load
		CM.wait("//a[@class='c24-cookie-button']", "cookie Alert", driver);
		// accepting cookie
		driver.findElement(By.xpath("//a[@class='c24-cookie-button']")).click();
   
} 
	
	//Searching for a flight
		@Test(priority = 0)
		public void searchForFlight() throws InterruptedException {
			//clicking travel section
			CM.click("//div[@class='c24-nav-ele c24-nav-travel']", "Travel Section", driver);
			//wait the destination field
			CM.wait("//input[@name='destination']", "Destination Field", driver);
			
			// adding destination
			lg.info("adding destination \n");
			driver.findElement(By.xpath("//input[@name='destination']")).clear();
			driver.findElement(By.xpath("//input[@name='destination']")).sendKeys("Egypt");
			Thread.sleep(1000);
			driver.findElement(By.xpath("//input[@name='destination']")).sendKeys(Keys.ENTER);
			
			
			// adding origin
			lg.info("adding origin \n");
			driver.findElement(By.xpath("//input[@id='c24-travel-airport-element']")).clear();
			driver.findElement(By.xpath("//input[@id='c24-travel-airport-element']")).sendKeys("Deutschland");
			Thread.sleep(1000);
			driver.findElement(By.xpath("//input[@id='c24-travel-airport-element']")).sendKeys(Keys.ENTER);
			
			
			//clicking the dates section
			CM.click("//div[@class='c24-travel-duration-overlay c24-travel-js-open-duration-layer']", "dates section", driver);
			//wait the dialog of the number of weeks
			CM.wait("//label[@for='c24-travel-duration-3']", "2 weeks  button", driver);
			//clicking 2 weeks dates
			CM.click("//label[@for='c24-travel-duration-3']", "dates section", driver);
			Thread.sleep(1000);
			//clicking the from date 
			//CM.click("//input[@name='departureDate']", "from date", driver);
			//sending date in the from date
			driver.findElement(By.xpath("//input[@name='departureDate']")).clear();
			driver.findElement(By.xpath("//input[@name='departureDate']")).sendKeys("01.06.2020");
			Thread.sleep(1000);
			//clicking the to date 
			//CM.click("//input[@name='returnDate']", "to date", driver);
			//sending date in the to date 
			driver.findElement(By.xpath("//input[@name='returnDate']")).clear();
			driver.findElement(By.xpath("//input[@name='returnDate']")).sendKeys("01.06.2021"); 
			
			
			// clicking search button
			CM.wait("//button[@id='c24-travel-search-button-element']", "Search button", driver);
			CM.click("//button[@id='c24-travel-search-button-element']", "Search button", driver);
			Thread.sleep(2000);
			//waiting for loading bar to finish
			
			CM.wait("//div[@class='cheapest-lowest-price js-hotel-headline js-headline-multiple-hotels ']//span[@class='js-deferred-count deferred-count']", "result set", driver);
			//Verifying that search criteria actually returned any number of options
			if(Integer.parseInt(driver.findElement(By.xpath("//div[@class='cheapest-lowest-price js-hotel-headline js-headline-multiple-hotels ']//span[@class='js-deferred-count deferred-count']")).getText()) > 0 ) {
				lg.info("Search Criteria succeeded and number of results are"+driver.findElement(By.xpath("(//span[@class='js-deferred-count deferred-count'])[1]")).getText());
			}
			else {
				lg.error("Search Criteria failed and no result is retreived");
			}
					
			

		}

	
	
	
	
	
	
	
	
	
	
	
	
	@AfterTest
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
