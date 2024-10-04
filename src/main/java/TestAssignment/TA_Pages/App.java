package TestAssignment.TA_Pages;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import TestAssignment.TA_Utility.Utilities;
import TestAssignment.TA_Elements.ElementPath;
import TestAssignment.TA_Elements.TestConstants;

public class App extends Utilities {

	public void goToJustWatch(WebDriver driver) {
		driver.get(TestConstants.URL);
	}

	public void searchMovie(WebDriver driver, ThreadLocal<ExtentTest> extentTest, String movieName) {
		if (isDisplayed(driver, extentTest, ElementPath.searchBar, 100)) {
			sendKeysUsingXPath(driver, extentTest, ElementPath.searchBar, movieName, 100);
			keyPress(driver, ElementPath.searchBar, Keys.ENTER);
		}
	}

	public void openMovieDescription(WebDriver driver, ThreadLocal<ExtentTest> extentTest) {
		if (isDisplayed(driver, extentTest, ElementPath.movieName, 100)) {
			List<WebElement> movieList = getAllElements(driver, ElementPath.movieName);
			if (movieList.size() > 0) {
				extentTest.get().log(Status.INFO, "Movie Name: " + getText(driver, extentTest, ElementPath.movieName, 100));
				clickOnElement(driver, extentTest, ElementPath.movieName, 100);
			}
		}
	}

	public void checkInfoTabs(WebDriver driver, ThreadLocal<ExtentTest> extentTest) {
		if (isDisplayed(driver, extentTest, ElementPath.infoTabs, 100)) {
			List<WebElement> infoTabs = getAllElements(driver, ElementPath.infoTabs);
			if (infoTabs.size() > 0) {
				for (int i = 1; i <= infoTabs.size(); i++) {
					extentTest.get().log(Status.INFO,
							"Tab: " + getText(driver, extentTest, ElementPath.infoTabs + "[" + i + "]", 100));
					clickOnElementUsingJS(driver, extentTest, ElementPath.infoTabs + "[" + i + "]", 100);
				}
			}
		}
	}

	public void goToHome(WebDriver driver, ThreadLocal<ExtentTest> extentTest) {
		clickOnElement(driver, extentTest, ElementPath.homeLink, 100);
	}
}
