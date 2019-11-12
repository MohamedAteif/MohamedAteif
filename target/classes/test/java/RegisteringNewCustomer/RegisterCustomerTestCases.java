package RegisteringNewCustomer;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import CommenUsedMethods.CommenMethods;

public class RegisterCustomerTestCases {
	private static WebDriver driver;
	static Logger lg = Logger.getLogger(RegisteringNewCustomer.RegisterCustomerTestCases.class.getName());
	String baseUrl = "";
	CommenMethods CM = new CommenMethods(driver, baseUrl);
	static Date currentDate = new Date();
	static long time = currentDate.getTime();
	String PreviouslyUsedEmail = time + "@email.com";

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
 
//Testing the start create new account button direction to registration form
	@Test(priority = 0)
	public void createNewUserButton() throws InterruptedException {
		// wait for side my account side bar to load
		for (int second = 0;; second++) {
			lg.info("wait for side my account side bar to load, Trial No.: " + second + " \n");

			if (second >= 60) {
				// Write Log in log file
				lg.error("wait for side my account side bar isn't Found.");
				fail();
			}
			try {
				if (CM.isElementPresent(By.xpath("//span[@class='c24-customer-hover c24-header-hover']"), driver))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		// Clicking My Account
		try {
			lg.info("Clicking My Account");
			driver.findElement(By.xpath("//span[@class='c24-customer-hover c24-header-hover']")).click();

		} catch (Exception e) {
			lg.error("Can't Click My Account", e);

			e.printStackTrace();
		}
		// waiting start here option

		for (int second = 0;; second++) {
			lg.info("wait start here option, Trial No.: " + second + " \n");

			if (second >= 60) {
				// Write Log in log file
				lg.error("start here option isn't Found.");
				fail();
			}
			try {
				if (CM.isElementPresent(
						By.xpath("(//div[@class='c24-customer open']//a[@class='c24-meinkonto-reflink'])[2]"), driver))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		// Clicking start here
		try {
			lg.info("Clicking start here");
			driver.findElement(By.xpath("(//div[@class='c24-customer open']//a[@class='c24-meinkonto-reflink'])[2]"))
					.click();

		} catch (Exception e) {
			lg.error("Can't Click start here", e);

			e.printStackTrace();
		}

		// waiting Registration Email Field

		for (int second = 0;; second++) {
			lg.info("wait Registration Email Field, Trial No.: " + second + " \n");

			if (second >= 60) {
				// Write Log in log file
				lg.error("Registration Email Field isn't Found.");
				fail();
			}
			try {
				if (CM.isElementPresent(By.xpath("//input[@id='email']"), driver))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
 
	}

	// happy path providing all registering field with all needed verifications
	@Test(priority = 1)
	public void registerationHappyPath() throws InterruptedException {
		// Adding Email
		try {
			// using the PreviouslyUsedEmail here to be used as test data later for
			// PreviouslyUsedEmail test case
			lg.info("Adding Email \n");
			driver.findElement(By.xpath("//input[@id='email']")).clear();
			driver.findElement(By.xpath("//input[@id='email']")).sendKeys(PreviouslyUsedEmail);
			;
		} catch (Exception e) {
			lg.error("Can't Add Email " + e + "");
		}
		// Adding password
		try {
			lg.info("Adding password \n");
			driver.findElement(By.xpath("//input[@id='password']")).clear();
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Asd123!@#");
		} catch (Exception e) {
			lg.error("Can't Add password " + e + "");
		}
		// confirm password
		try {
			lg.info("confirm password \n");
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).clear();
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).sendKeys("Asd123!@#");
		} catch (Exception e) {
			lg.error("Can't confirm password " + e + "");
		}

		// verify the password strength indicator is working
		try {

			lg.info("Verifying that password indicator is working \n");
			assertEquals("Stark", driver.findElement(By.xpath("//span[@id='indicator-text']")).getText());

			lg.info("Verification is Successfully done \n");

		} catch (Exception e) {
			lg.info("Verification isn't done \n");
			lg.error("Verification Failed. \n");
			e.printStackTrace();
		}

		// waiting submit button

		for (int second = 0;; second++) {
			lg.info("wait submit button, Trial No.: " + second + " \n");

			if (second >= 60) {
				// Write Log in log file
				lg.error("submit button isn't Found.");
				fail();
			}
			try {
				if (CM.isElementPresent(By.xpath("//div[@class='right']"), driver))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		System.out.println("clicking submit");
		driver.findElement(By.xpath("//div[@class='right']")).click();
		System.out.println("clicking submit done");
		Thread.sleep(10000);
		// confirming the registering is done successfully by waiting the pop up welcome
		// message

		for (int second = 0;; second++) {
			lg.info("wait submit button, Trial No.: " + second + " \n");

			if (second >= 60) {
				// Write Log in log file
				lg.error("submit button isn't Found.");
				fail();
			}
			try {
				if (CM.isElementPresent(By.xpath("(//button[contains(text(),'schliessen')])[2]"), driver))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		// skipping the welcome message
		driver.findElement(By.xpath("(//button[contains(text(),'schliessen')])[2]")).click();
		// logging out to proceed with next test case for registration form
		driver.get("https://kundenbereich.check24.de/user/logout.html");

	}

	// checking that the email field only accept email format
	@Test(priority = 2)
	public void registerationWithNotEmailString() throws InterruptedException {
		// navigating to registration page to start next test case
		driver.get("https://kundenbereich.check24.de/user/register.html");
		// Adding NonEmail format string
		try {
			lg.info("Adding NonEmail String \n");
			driver.findElement(By.xpath("//input[@id='email']")).clear();
			driver.findElement(By.xpath("//input[@id='email']")).sendKeys("NonEmail");
			;
		} catch (Exception e) {
			lg.error("Can't Add the NonEmail String  " + e + "");
		}
// Adding password
		try {
			lg.info("Adding password \n");
			driver.findElement(By.xpath("//input[@id='password']")).clear();
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Asd123!@#");
		} catch (Exception e) {
			lg.error("Can't Add password " + e + "");
		}
// confirm password
		try {
			lg.info("confirm password \n");
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).clear();
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).sendKeys("Asd123!@#");
		} catch (Exception e) {
			lg.error("Can't confirm password " + e + "");
		}

// verify the password strength indicator is working
		try {

			lg.info("Verifying that password indicator is working \n");
			assertEquals("Stark", driver.findElement(By.xpath("//span[@id='indicator-text']")).getText());

			lg.info("Verification is Successfully done \n");

		} catch (Exception e) {
			lg.info("Verification isn't done \n");
			lg.error("Verification Failed. \n");
			e.printStackTrace();
		}
//waiting submit button 

		for (int second = 0;; second++) {
			lg.info("wait submit button, Trial No.: " + second + " \n");

			if (second >= 60) {
				// Write Log in log file
				lg.error("submit button isn't Found.");
				fail();
			}
			try {
				if (CM.isElementPresent(By.xpath("//div[@class='right']"), driver))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
// Clicking register now button
		try {
			lg.info("Clicking register now button");
			driver.findElement(By.xpath("//div[@class='right']")).click();

		} catch (Exception e) {
			lg.error("Can't Click register now button", e);

			e.printStackTrace();
		}

//waiting for the error desc message 
		for (int second = 0;; second++) {
			lg.info("wait error desc message , Trial No.: " + second + " \n");

			if (second >= 60) {
				// Write Log in log file
				lg.error("error desc message  isn't Found.");
				fail();
			}
			try {
				if (CM.isElementPresent(By.xpath("//div[@class='error-desc']"), driver))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
//verifying the error desc message 
		try {

			lg.info("Verifying the error desc message  \n");
			assertEquals("Bitte geben Sie eine gültige E-Mail-Adresse an.",
					driver.findElement(By.xpath("//div[@class='error-desc']")).getText());

			lg.info("Verification is Successfully done \n");

		} catch (Exception e) {
			lg.info("Verification isn't done \n");
			lg.error("Verification Failed. \n");
			e.printStackTrace();
		}
	}

	// checking that the email can not be empty when registering
	@Test(priority = 3)
	public void registerationWithEmptyEmailField() throws InterruptedException {
		driver.get("https://kundenbereich.check24.de/user/register.html");
		// Adding password
		try {
			lg.info("Adding password \n");
			driver.findElement(By.xpath("//input[@id='password']")).clear();
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Asd123!@#");
		} catch (Exception e) {
			lg.error("Can't Add password " + e + "");
		}
		// confirm password
		try {
			lg.info("confirm password \n");
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).clear();
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).sendKeys("Asd123!@#");
		} catch (Exception e) {
			lg.error("Can't confirm password " + e + "");
		}

		// verify the password strength indicator is working
		try {

			lg.info("Verifying that password indicator is working \n");
			assertEquals("Stark", driver.findElement(By.xpath("//span[@id='indicator-text']")).getText());

			lg.info("Verification is Successfully done \n");

		} catch (Exception e) {
			lg.info("Verification isn't done \n");
			lg.error("Verification Failed. \n");
			e.printStackTrace();
		}

		// waiting submit button then click it
		CM.wait("//div[@class='right']", "submit button", driver);
		CM.click("//div[@class='right']", "submit button", driver);
		// waiting error message
		CM.wait("//div[@class='error-desc']", "Empty Email Error", driver);
		CM.Verify("//div[@class='error-desc']", "Bitte E-Mail-Adresse angeben.", "Empty Email Error", driver);

	}

	// checking that the password can not be empty when registering
	@Test(priority = 4)
	public void registerationWithEmptyPassword() throws InterruptedException {
		driver.get("https://kundenbereich.check24.de/user/register.html");
		// Adding Email
		try {
			lg.info("Adding Email \n");
			driver.findElement(By.xpath("//input[@id='email']")).clear();
			CommenMethods.sendChar(driver.findElement(By.xpath("//input[@id='email']")), time + "@Email.com");
		} catch (Exception e) {
			lg.error("Can't Add Email " + e + "");
		}
		// confirm password
		try {
			lg.info("confirm password \n");
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).clear();
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).sendKeys("Asd123!@#");
		} catch (Exception e) {
			lg.error("Can't confirm password " + e + "");
		}

		// verify the password strength indicator is working(Password strength: too
		// short when not adding any password)

		CM.Verify("//span[@id='indicator-text']", "Zu kurz", "password indicator", driver);
		// waiting submit button then click it
		CM.wait("//div[@class='right']", "submit button", driver);
		CM.click("//div[@class='right']", "submit button", driver);
		// waiting error message
		CM.wait("//div[@class='error-desc']", "Empty password Error", driver);
		// verify the empty password error message
		CM.Verify("(//div[@class='error-desc'])[1]", "Bitte geben Sie Ihr Passwort ein.", "Empty password Error",
				driver);
		// verify the password doesn't match error message
		CM.Verify("(//div[@class='error-desc'])[2]", "Die Passwörter stimmen nicht überein.",
				"password doesn't match Error", driver);
	}

	// checking that the password confirmation field can not be empty when
	// registering
	@Test(priority = 5)
	public void registerationWithEmptyPasswordConfirmationField() throws InterruptedException {
		driver.get("https://kundenbereich.check24.de/user/register.html");
		// Adding Email
		try {
			lg.info("Adding Email \n");
			driver.findElement(By.xpath("//input[@id='email']")).clear();
			CommenMethods.sendChar(driver.findElement(By.xpath("//input[@id='email']")), time + "@Email.com");
		} catch (Exception e) {
			lg.error("Can't Add Email " + e + "");
		}
		// Adding password
		try {
			lg.info("Adding password \n");
			driver.findElement(By.xpath("//input[@id='password']")).clear();
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Asd123!@#");
		} catch (Exception e) {
			lg.error("Can't Add password " + e + "");
		}

		// verify the password strength indicator is working(Password strength: Stark)
		CM.Verify("//span[@id='indicator-text']", "Stark", "password indicator", driver);
		// waiting submit button then click it
		CM.wait("//div[@class='right']", "submit button", driver);
		CM.click("//div[@class='right']", "submit button", driver);
		// waiting error message
		CM.wait("//div[@class='error-desc']", "Empty password confirmation Error", driver);
		// verify the empty password error message
		CM.Verify("(//div[@class='error-desc'])[1]", "Die Passwörter stimmen nicht überein.",
				"Empty password confirmation Error", driver);

	}

	// checking error message for empty password and empty password confirmation
	@Test(priority = 6)
	public void registerationWithEmptyPasswordAndEmptyConfirmation() throws InterruptedException {
		driver.get("https://kundenbereich.check24.de/user/register.html");
		// Adding Email
		try {
			lg.info("Adding Email \n");
			driver.findElement(By.xpath("//input[@id='email']")).clear();
			CommenMethods.sendChar(driver.findElement(By.xpath("//input[@id='email']")), time + "@Email.com");
		} catch (Exception e) {
			lg.error("Can't Add Email " + e + "");
		}
		// verify the password strength indicator is working(Password strength: Stark)
		CM.Verify("//span[@id='indicator-text']", "Zu kurz", "password indicator", driver);
		// waiting submit button then click it
		CM.wait("//div[@class='right']", "submit button", driver);
		CM.click("//div[@class='right']", "submit button", driver);
		// waiting error message
		CM.wait("//div[@class='error-desc']", "Empty password confirmation Error", driver);
		// verify the empty password error message
		CM.Verify("(//div[@class='error-desc'])[1]", "Bitte geben Sie Ihr Passwort ein.",
				"Empty password confirmation Error", driver);
		// verify the empty password confirmation error message
		CM.Verify("(//div[@class='error-desc'])[2]", "Bitte geben Sie Ihr Passwort ein.",
				"Empty password confirmation Error", driver);
	}

	// checking that the password doesn't accept less than 6 charchters
	@Test(priority = 7)
	public void registerationWithFiveCharchtersPassword() throws InterruptedException {
		driver.get("https://kundenbereich.check24.de/user/register.html");
		String ShortPassword = CM.getAlphaNumericString(5);
		lg.info("short password is : " + ShortPassword);
		// Adding Email
		try {
			lg.info("Adding Email \n");
			driver.findElement(By.xpath("//input[@id='email']")).clear();
			CommenMethods.sendChar(driver.findElement(By.xpath("//input[@id='email']")), time + "@Email.com");
		} catch (Exception e) {
			lg.error("Can't Add Email " + e + "");
		}
		// Adding password
		try {
			lg.info("Adding password \n");
			driver.findElement(By.xpath("//input[@id='password']")).clear();
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys(ShortPassword);
		} catch (Exception e) {
			lg.error("Can't Add password " + e + "");
		}
		// confirm password
		try {
			lg.info("confirm password \n");
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).clear();
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).sendKeys(ShortPassword);
		} catch (Exception e) {
			lg.error("Can't confirm password " + e + "");
		}

		// verify the password strength indicator is working(Password strength: too
		// short when adding short password)

		CM.Verify("//span[@id='indicator-text']", "Zu kurz", "password indicator", driver);
		// waiting submit button then click it
		CM.wait("//div[@class='right']", "submit button", driver);
		CM.click("//div[@class='right']", "submit button", driver);
		// waiting error message
		CM.wait("//div[@class='error-desc']", "short password Error", driver);
		CM.Verify("(//div[@class='error-desc'])[1]", "Das Passwort sollte zwischen 6 und 50 Zeichen lang sein.",
				"short password", driver);
		CM.Verify("(//div[@class='error-desc'])[2]", "Das Passwort sollte zwischen 6 und 50 Zeichen lang sein.",
				"short confirmation password", driver);
	}

	// checking that the password doesn't accept more than 50 charachters
	@Test(priority = 8)
	public void registerationWithFiftyOneCharchtersPassword() throws InterruptedException {
		driver.get("https://kundenbereich.check24.de/user/register.html");
		String LongPassword = CM.getAlphaNumericString(51);
		lg.info("Long password is : " + LongPassword);
		// Adding Email
		try {
			lg.info("Adding Email \n");
			driver.findElement(By.xpath("//input[@id='email']")).clear();
			CommenMethods.sendChar(driver.findElement(By.xpath("//input[@id='email']")), time + "@Email.com");
		} catch (Exception e) {
			lg.error("Can't Add Email " + e + "");
		}
		// Adding password
		try {
			lg.info("Adding password \n");
			driver.findElement(By.xpath("//input[@id='password']")).clear();
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys(LongPassword);
		} catch (Exception e) {
			lg.error("Can't Add password " + e + "");
		}
		// confirm password
		try {
			lg.info("confirm password \n");
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).clear();
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).sendKeys(LongPassword);
		} catch (Exception e) {
			lg.error("Can't confirm password " + e + "");
		}

		// verify the password strength indicator is working(Password strength: very
		// strong)

		CM.Verify("//span[@id='indicator-text']", "Sehr stark", "password indicator for long password", driver);
		// waiting submit button then click it
		CM.wait("//div[@class='right']", "submit button", driver);
		CM.click("//div[@class='right']", "submit button", driver);

		Thread.sleep(3000);
		String CurrentUrl = driver.getCurrentUrl();
		lg.info(CurrentUrl);
		if (CurrentUrl != "https://kundenbereich.check24.de/user/register.html") {
			lg.error("TestCase Failed: password accepts more than 50 charchters");
			lg.info("TestCase Failed: password accepts more than 50 charchters");
			// logging out to proceed with next test case for registration form
			driver.get("https://kundenbereich.check24.de/user/logout.html");
		} else {
			lg.info("TestCase successfully done: password doesn't accept more than 50 charchters");
		}
	}

	// checking that the password doesn't accept only letters
	@Test(priority = 9)
	public void registerationWithOnlyLettersPassword() throws InterruptedException {
		driver.get("https://kundenbereich.check24.de/user/register.html");
		String OnlyLettersPassword = "asdfgh";
		lg.info("OnlyLettersPassword is : " + OnlyLettersPassword);
		// Adding Email
		try {
			lg.info("Adding Email \n");
			driver.findElement(By.xpath("//input[@id='email']")).clear();
			CommenMethods.sendChar(driver.findElement(By.xpath("//input[@id='email']")), time + "@Email.com");
		} catch (Exception e) {
			lg.error("Can't Add Email " + e + "");
		}
		// Adding password
		try {
			lg.info("Adding password \n");
			driver.findElement(By.xpath("//input[@id='password']")).clear();
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys(OnlyLettersPassword);
		} catch (Exception e) {
			lg.error("Can't Add password " + e + "");
		}
		// confirm password
		try {
			lg.info("confirm password \n");
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).clear();
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).sendKeys(OnlyLettersPassword);
		} catch (Exception e) {
			lg.error("Can't confirm password " + e + "");
		}

		// verify the password strength indicator is working(Password strength: very
		// weak)

		CM.Verify("//span[@id='indicator-text']", "Zu schwach", "password indicator for only letters password", driver);
		// waiting submit button then click it
		CM.wait("//div[@class='right']", "submit button", driver);
		CM.click("//div[@class='right']", "submit button", driver);

		Thread.sleep(3000);
		String CurrentUrl = driver.getCurrentUrl();
		lg.info(CurrentUrl);
		if (CurrentUrl != "https://kundenbereich.check24.de/user/register.html") {
			lg.error("TestCase Failed: password accepts only letters");
			lg.info("TestCase Failed: password accepts only letters");
			// logging out to proceed with next test case for registration form
			driver.get("https://kundenbereich.check24.de/user/logout.html");
		} else {
			lg.info("TestCase successfully done: password doesn't accept only letters");
		}
	}

	// checking that the password doesn't accept only numbers
	@Test(priority = 10)
	public void registerationWithOnlyNumbersPassword() throws InterruptedException {
		driver.get("https://kundenbereich.check24.de/user/register.html");
		String OnlyNumbersPassword = "123456";
		lg.info("OnlyNumbersPassword is : " + OnlyNumbersPassword);
		// Adding Email
		try {
			lg.info("Adding Email \n");
			driver.findElement(By.xpath("//input[@id='email']")).clear();
			CommenMethods.sendChar(driver.findElement(By.xpath("//input[@id='email']")), time + "@Email.com");
		} catch (Exception e) {
			lg.error("Can't Add Email " + e + "");
		}
		// Adding password
		try {
			lg.info("Adding password \n");
			driver.findElement(By.xpath("//input[@id='password']")).clear();
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys(OnlyNumbersPassword);
		} catch (Exception e) {
			lg.error("Can't Add password " + e + "");
		}
		// confirm password
		try {
			lg.info("confirm password \n");
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).clear();
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).sendKeys(OnlyNumbersPassword);
		} catch (Exception e) {
			lg.error("Can't confirm password " + e + "");
		}

		// verify the password strength indicator is working(Password strength: very
		// weak)

		CM.Verify("//span[@id='indicator-text']", "Zu schwach", "password indicator for only numbers password", driver);
		// waiting submit button then click it
		CM.wait("//div[@class='right']", "submit button", driver);
		CM.click("//div[@class='right']", "submit button", driver);

		Thread.sleep(3000);
		String CurrentUrl = driver.getCurrentUrl();
		lg.info(CurrentUrl);
		if (CurrentUrl != "https://kundenbereich.check24.de/user/register.html") {
			lg.error("TestCase Failed: password accepts only numbers");
			lg.info("TestCase Failed: password accepts only numbers");
			// logging out to proceed with next test case for registration form
			driver.get("https://kundenbereich.check24.de/user/logout.html");
		} else {
			lg.info("TestCase successfully done: password doesn't accept only numbers");
		}

	}

	// checking that the password doesn't accept only special charachters
	@Test(priority = 11)
	public void registerationWithOnlySpecialCharchtersPassword() throws InterruptedException {
		driver.get("https://kundenbereich.check24.de/user/register.html");
		String OnlySpecialCharchtersPassword = "!@#$%^";
		lg.info("OnlySpecialCharchtersPassword is : " + OnlySpecialCharchtersPassword);
		// Adding Email
		try {
			lg.info("Adding Email \n");
			driver.findElement(By.xpath("//input[@id='email']")).clear();
			CommenMethods.sendChar(driver.findElement(By.xpath("//input[@id='email']")), time + "@Email.com");
		} catch (Exception e) {
			lg.error("Can't Add Email " + e + "");
		}
		// Adding password
		try {
			lg.info("Adding password \n");
			driver.findElement(By.xpath("//input[@id='password']")).clear();
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys(OnlySpecialCharchtersPassword);
		} catch (Exception e) {
			lg.error("Can't Add password " + e + "");
		}
		// confirm password
		try {
			lg.info("confirm password \n");
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).clear();
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).sendKeys(OnlySpecialCharchtersPassword);
		} catch (Exception e) {
			lg.error("Can't confirm password " + e + "");
		}

		// verify the password strength indicator is working(Password strength: very
		// weak)

		CM.Verify("//span[@id='indicator-text']", "Zu schwach",
				"password indicator for only special charachters password", driver);
		// waiting submit button then click it
		CM.wait("//div[@class='right']", "submit button", driver);
		CM.click("//div[@class='right']", "submit button", driver);

		Thread.sleep(3000);
		String CurrentUrl = driver.getCurrentUrl();
		lg.info(CurrentUrl);
		if (CurrentUrl != "https://kundenbereich.check24.de/user/register.html") {
			lg.error("TestCase Failed: password accepts only special charchters");
			lg.info("TestCase Failed: password accepts only special charchters");
			// logging out to proceed with next test case for registration form
			driver.get("https://kundenbereich.check24.de/user/logout.html");
		} else {
			lg.info("TestCase successfully done: password doesn't accept only special charchters");
		}
	}

	// checking that the password doesn't accept passwords similar to email
	@Test(priority = 12)
	public void registerationWithPasswordSimilarToEmail() throws InterruptedException {
		driver.get("https://kundenbereich.check24.de/user/register.html");
		String PasswordSimilarToEmail = time + "@Email.com";
		lg.info("PasswordSimilarToEmail is : " + PasswordSimilarToEmail);
		// Adding Email
		try {
			lg.info("Adding Email \n");
			driver.findElement(By.xpath("//input[@id='email']")).clear();
			driver.findElement(By.xpath("//input[@id='email']")).sendKeys(PasswordSimilarToEmail);
			;
		} catch (Exception e) {
			lg.error("Can't Add Email " + e + "");
		}
		// Adding password
		try {
			lg.info("Adding password \n");
			driver.findElement(By.xpath("//input[@id='password']")).clear();
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys(PasswordSimilarToEmail);
		} catch (Exception e) {
			lg.error("Can't Add password " + e + "");
		}
		// confirm password
		try {
			lg.info("confirm password \n");
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).clear();
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).sendKeys(PasswordSimilarToEmail);
		} catch (Exception e) {
			lg.error("Can't confirm password " + e + "");
		}

		// verify the password strength indicator is working(Password strength: very
		// weak)

		CM.Verify("//span[@id='indicator-text']", "Zu schwach", "password indicator for only numbers password", driver);
		// waiting submit button then click it
		CM.wait("//div[@class='right']", "submit button", driver);
		CM.click("//div[@class='right']", "submit button", driver);

		Thread.sleep(3000);
		String CurrentUrl = driver.getCurrentUrl();
		lg.info(CurrentUrl);
		if (CurrentUrl != "https://kundenbereich.check24.de/user/register.html") {
			lg.error("TestCase Failed: password accepts passwords similar to email");
			lg.info("TestCase Failed: password accepts passwords similar to email");
			// logging out to proceed with next test case for registration form
			driver.get("https://kundenbereich.check24.de/user/logout.html");
		} else {
			lg.info("TestCase successfully done: password doesn't accept password similar to email");
		}

	}

	// checking that the email field doesn't accept previously used emails
	@Test(priority = 13)
	public void registerationWithPreviouslyUsedEmail() throws InterruptedException {
		driver.get("https://kundenbereich.check24.de/user/register.html");
		lg.info("PreviouslyUsedEmail is : " + PreviouslyUsedEmail);
		// Adding Email
		try {
			lg.info("Adding Email \n");
			driver.findElement(By.xpath("//input[@id='email']")).clear();
			// using the PreviouslyUsedEmail that was used in the happy path test case 1
			driver.findElement(By.xpath("//input[@id='email']")).sendKeys(PreviouslyUsedEmail);
			;
		} catch (Exception e) {
			lg.error("Can't Add Email " + e + "");
		}
		// Adding password
		try {
			lg.info("Adding password \n");
			driver.findElement(By.xpath("//input[@id='password']")).clear();
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Asd123!@#");
		} catch (Exception e) {
			lg.error("Can't Add password " + e + "");
		}
		// confirm password
		try {
			lg.info("confirm password \n");
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).clear();
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).sendKeys("Zxc123!@#");
		} catch (Exception e) {
			lg.error("Can't confirm password " + e + "");
		}
		// waiting submit button then click it
		CM.wait("//div[@class='right']", "submit button", driver);
		CM.click("//div[@class='right']", "submit button", driver);

		Thread.sleep(3000);
		String CurrentUrl = driver.getCurrentUrl();
		lg.info(CurrentUrl);
		if (CurrentUrl != "https://kundenbereich.check24.de/user/register.html") {
			lg.error("TestCase Failed: email accepts previously used email");
			lg.info("TestCase Failed: email accepts previously used email");
			// logging out to proceed with next test case for registration form
			driver.get("https://kundenbereich.check24.de/user/logout.html");
		} else {
			lg.info("TestCase successfully done: password doesn't accept previously used emails");
		}
	}

	// checking that the password field doesn't accept easy to guess password
	@Test(priority = 14)
	public void registerationWithEasyToGuessPassword() throws InterruptedException {
		driver.get("https://kundenbereich.check24.de/user/register.html");
		String EasyToGuessPassword = "abc123";
		lg.info("EasyToGuessPassword is : " + EasyToGuessPassword);
		// Adding Email
		try {
			lg.info("Adding Email \n");
			driver.findElement(By.xpath("//input[@id='email']")).clear();
			CommenMethods.sendChar(driver.findElement(By.xpath("//input[@id='email']")), time + "@Email.com");
		} catch (Exception e) {
			lg.error("Can't Add Email " + e + "");
		}
		// Adding password
		try {
			lg.info("Adding password \n");
			driver.findElement(By.xpath("//input[@id='password']")).clear();
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys(EasyToGuessPassword);
		} catch (Exception e) {
			lg.error("Can't Add password " + e + "");
		}
		// confirm password
		try {
			lg.info("confirm password \n");
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).clear();
			driver.findElement(By.xpath("//input[@id='passwordrepetition']")).sendKeys(EasyToGuessPassword);
		} catch (Exception e) {
			lg.error("Can't confirm password " + e + "");
		}

		// verify the password strength indicator is working(Password strength: very
		// weak)

		CM.Verify("//span[@id='indicator-text']", "Zu schwach", "password indicator for only numbers password", driver);
		// waiting submit button then click it
		CM.wait("//div[@class='right']", "submit button", driver);
		CM.click("//div[@class='right']", "submit button", driver);

		Thread.sleep(3000);
		String CurrentUrl = driver.getCurrentUrl();
		lg.info(CurrentUrl);
		if (CurrentUrl != "https://kundenbereich.check24.de/user/register.html") {
			lg.error("TestCase Failed: password accepts Easy to Guess Passwords");
			lg.info("TestCase Failed: password accepts Easy to Guess Passwords");
			// logging out to proceed with next test case for registration form
			driver.get("https://kundenbereich.check24.de/user/logout.html");
		} else {
			lg.info("TestCase successfully done: password doesn't accept Easy to Guess Passwords");
		}
	}
	@Test(priority = 15)
	public void registerationTermsOfUseAndPrivacy() throws InterruptedException {
		driver.get("https://kundenbereich.check24.de/user/register.html");
		// waiting terms of use
		CM.wait("//div[@id='terms-update']//a[@title='Nutzungsbedingungen']", "TermsOfUse", driver);
		CM.click("//div[@id='terms-update']//a[@title='Nutzungsbedingungen']", "TermsOfUse", driver);
		Thread.sleep(3000);
		String CurrentUrl = driver.getCurrentUrl();
		lg.info(CurrentUrl);
		if (CurrentUrl != "https://kundenbereich.check24.de/terms.html") {
			lg.error("TestCase Failed: Terms of use link is broken");
			lg.info("TestCase Failed: Terms of use link is broken");
			// logging out to proceed with next test case for registration form
			driver.get("https://kundenbereich.check24.de/user/logout.html");
		} else {
			lg.info("TestCase successfully done: terms of use link is working fine");
			//navigate to register form again to try privacy link
			driver.get("https://kundenbereich.check24.de/user/register.html");
		}
		
		
	}

	@AfterTest
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
