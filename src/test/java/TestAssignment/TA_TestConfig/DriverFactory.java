package TestAssignment.TA_TestConfig;

import java.time.Duration;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;

	public WebDriver getDriver(String browserName) {
		if(driver==null) {

			if(browserName.equalsIgnoreCase("firefox")) {

				FirefoxOptions options = new FirefoxOptions();
				//options.addArguments("--remote-allow-origins=*");
				options.addArguments("--start-maximized");
				options.addArguments("force-device-scale-factor=1.15");
				options.addArguments("high-dpi-support=1.15");
				options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				options.setImplicitWaitTimeout(Duration.ofSeconds(45));

				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
				options.merge(capabilities);

				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver(options);
				driver.manage().deleteAllCookies();

			}else if(browserName.equalsIgnoreCase("chrome")) {

				ChromeOptions options = new ChromeOptions();
				options.addArguments("--remote-allow-origins=*");
				options.addArguments("--start-maximized");
				options.addArguments("force-device-scale-factor=1.15");
				options.addArguments("high-dpi-support=1.15");
				options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				options.setImplicitWaitTimeout(Duration.ofSeconds(45));

				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				options.merge(capabilities);

				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(options);
				driver.manage().deleteAllCookies();

			}else if(browserName.equalsIgnoreCase("edge")) {

				EdgeOptions options = new EdgeOptions();
				options.addArguments("--remote-allow-origins=*");
				options.addArguments("--start-maximized");
				options.addArguments("force-device-scale-factor=1.15");
				options.addArguments("high-dpi-support=1.15");
				options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				options.setImplicitWaitTimeout(Duration.ofSeconds(45));

				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(EdgeOptions.CAPABILITY, options);
				options.merge(capabilities);

				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver(options);
				driver.manage().deleteAllCookies();

			}else {
				System.out.println("Browser has not beem setup");
			}
		}
		return driver;
	}
}
