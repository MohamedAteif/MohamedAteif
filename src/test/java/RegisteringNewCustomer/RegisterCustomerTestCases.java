package RegisteringNewCustomer;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import CommenUsedMethods.CommenMethods;

public class RegisterCustomerTestCases {
	private static WebDriver driver;
	static Logger lg = Logger.getLogger(RegisteringNewCustomer.RegisterCustomerTestCases.class.getName());
	String baseUrl = "";

	@BeforeTest
	public void setUp() throws Exception {
		CommenMethods CM = new CommenMethods(driver, baseUrl);
		CM.setDrivers();
		driver = CM.setUpDriversWebDrivers();
		baseUrl = CM.baseUrl;
	}

}
