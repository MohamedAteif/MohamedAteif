package CommenUsedMethods;
 
import java.util.Hashtable;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CommenMethods {
	private WebDriver driver;
	public String baseUrl;

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
}
