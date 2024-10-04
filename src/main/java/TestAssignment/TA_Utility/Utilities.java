package TestAssignment.TA_Utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class Utilities {

	public static String getScreenShot(WebDriver driver, String screenshotName, String methodName) throws IOException {
		String dateName = new SimpleDateFormat("_yyyy_MM_dd_hh_mm_ss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/Screenshots/" + methodName + "_" + screenshotName
				+ "_capture_" + dateName + ".jpg";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	private static String getLocator(String identifier) {
		String[] strArray = identifier.split(";");
		return strArray[1];
	}

	private static String getLabel(String identifier) {
		String[] strArray = identifier.split(";");
		return strArray[0];
	}

	protected boolean isDisplayed(WebDriver driver, ThreadLocal<ExtentTest> test, String identifier, int timeout) {

		String locator = getLocator(identifier);
		String label = getLabel(identifier);
		String screenPath = null;
		boolean isDisplay = false;
		try {
			test.get().log(Status.INFO, "Check if " + label + " is Displayed");
			Duration waitTime = Duration.ofSeconds(timeout);
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
			isDisplay = driver.findElement(By.xpath(locator)).isDisplayed();
			screenPath = getScreenShot(driver, test.getClass().getName(), test.getClass().getSimpleName());
			test.get().log(Status.INFO, label + " --> " + (isDisplay ? "Displayed" : "Not Displayed"))
					.info(MediaEntityBuilder.createScreenCaptureFromPath(screenPath).build());
		} catch (Exception e) {
			test.get().log(Status.INFO, "Unable to find " + label + ", using XPATH: {" + locator + "}-->" + e)
					.info(MediaEntityBuilder.createScreenCaptureFromPath(screenPath).build());
			;
		}
		return isDisplay;
	}

	protected static void clickOnElement(WebDriver driver, ThreadLocal<ExtentTest> test, String identifier, int timeout) {

		String locator = getLocator(identifier);
		String label = getLabel(identifier);
		String screenPath = null;
		try {
			test.get().log(Status.INFO, "Check if " + label + " is Clickable");
			Duration waitTime = Duration.ofSeconds(timeout);
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
			screenPath = getScreenShot(driver, test.getClass().getName(), test.getClass().getSimpleName());
			driver.findElement(By.xpath(locator)).click();
			test.get().log(Status.PASS, label + " is clicked ")
					.pass(MediaEntityBuilder.createScreenCaptureFromPath(screenPath).build());
		} catch (Exception e) {
			test.get().fail("Unable to click " + label + ", using XPATH: {" + locator + "}-->" + e,
					MediaEntityBuilder.createScreenCaptureFromPath(screenPath).build());
		}

	}

	protected static void clickOnElementUsingJS(WebDriver driver, ThreadLocal<ExtentTest> test, String identifier, int timeout) {

		String locator = getLocator(identifier);
		String label = getLabel(identifier);
		String screenPath = null;
		try {
			test.get().log(Status.INFO, "Check if " + label + " is Clickable");
			Duration waitTime = Duration.ofSeconds(timeout);
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
			screenPath = getScreenShot(driver, test.getClass().getName(), test.getClass().getSimpleName());
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(By.xpath(locator)));
			test.get().log(Status.PASS, label + " is clicked ")
					.pass(MediaEntityBuilder.createScreenCaptureFromPath(screenPath).build());
		} catch (Exception e) {
			test.get().fail("Unable to click " + label + ", using XPATH: {" + locator + "}-->" + e,
					MediaEntityBuilder.createScreenCaptureFromPath(screenPath).build());
		}

	}

	protected void sendKeysUsingXPath(WebDriver driver, ThreadLocal<ExtentTest> test, String identifier, String text, int timeout) {

		String locator = getLocator(identifier);
		String label = getLabel(identifier);
		String screenPath = null;
		try {
			test.get().log(Status.INFO, "Check if text can be entered in " + label);
			Duration waitTime = Duration.ofSeconds(timeout);
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
			driver.findElement(By.xpath(locator)).sendKeys(text);
			screenPath = getScreenShot(driver, test.getClass().getName(), test.getClass().getSimpleName());
			test.get().log(Status.PASS, "Text is added to " + label)
					.pass(MediaEntityBuilder.createScreenCaptureFromPath(screenPath).build());
		} catch (Exception e) {
			test.get().fail("Unable to add text to " + label + ", using XPATH: {" + locator + "}-->" + e,
					MediaEntityBuilder.createScreenCaptureFromPath(screenPath).build());
		}
	}

	protected String getText(WebDriver driver, ThreadLocal<ExtentTest> test, String identifier, int timeout) {

		String locator = getLocator(identifier);
		String label = getLabel(identifier);
		String returnText = " ";
		String screenPath = null;
		try {
			test.get().log(Status.INFO, "Check if " + label + " is Present");
			Duration waitTime = Duration.ofSeconds(timeout);
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
			returnText = driver.findElement(By.xpath(locator)).getText();
			screenPath = getScreenShot(driver, test.getClass().getName(), test.getClass().getSimpleName());
			test.get().log(Status.PASS, "Text is received from " + label)
					.pass(MediaEntityBuilder.createScreenCaptureFromPath(screenPath).build());
		} catch (Exception e) {
			test.get().fail("Unable to get text from " + label + ", using XPATH: {" + locator + "}-->" + e,
					MediaEntityBuilder.createScreenCaptureFromPath(screenPath).build());
		}
		return returnText;
	}

	protected void keyPress(WebDriver driver, String identifier, Keys key) {

		String locator = getLocator(identifier);
		driver.findElement(By.xpath(locator)).click();
		driver.findElement(By.xpath(locator)).sendKeys(Keys.ENTER);
	}

	protected List<WebElement> getAllElements(WebDriver driver, String identifier) {

		String locator = getLocator(identifier);
		List<WebElement> elementList = driver.findElements(By.xpath(locator));
		return elementList;
	}
}
