package RegisteringNewCustomer;

import org.testng.annotations.Test;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import CommenUsedMethods.CommenMethods;
 
public class RegisterCustomerTestCases {
	private static WebDriver driver;
	static Logger lg = Logger.getLogger(RegisteringNewCustomer.RegisterCustomerTestCases.class.getName());
	String baseUrl = "";
	CommenMethods CM = new CommenMethods(driver, baseUrl);

	@BeforeTest
	public void setUp() throws Exception {
		CommenMethods CM = new CommenMethods(driver, baseUrl);
		CM.setDrivers();
		driver = CM.setUpDriversWebDrivers();
		System.out.println("CM.baseUrl" + CM.baseUrl);
		baseUrl = CM.baseUrl;
	}

	@Test(priority = 0)
	public void oneMoreTest() {

		System.out.println("This is a TestNG-Maven based test");
	}

	@AfterTest
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
