package TestAssignment.TA_Test;

import java.io.IOException;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import TestAssignment.TA_TestConfig.DriverFactory;
import TestAssignment.TA_Utility.Utilities;

public class BaseTest extends DriverFactory {

	static ExtentSparkReporter reporter;
	static ExtentReports extentReports;
	static ExtentTest extentTest;
	ThreadLocal<ExtentTest> threadTest; 
	
	@BeforeSuite
	public synchronized void initialiseReport() throws Exception {

		reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\ExtentReports\\Report.html");
		extentReports = new ExtentReports();
		extentReports.attachReporter(reporter);
		reporter.config().setDocumentTitle("Full Test Report");
		reporter.config().setTheme(Theme.STANDARD);
		reporter.config().setTimeStampFormat("MMMM dd, YYYY, hh:mm:ss a '('zzz')'");
		reporter.config().setEncoding("UTF-8");
	}

	@BeforeTest
	@Parameters({"browser", "author"})
	public synchronized void startTest(ITestContext context, String browser, String author) {

		driver = getDriver(browser);
		reporter.config().setReportName(context.getSuite().getName());
		extentReports.setSystemInfo("Author", author);
		threadTest = new ThreadLocal<ExtentTest>();
		extentTest = extentReports.createTest(context.getName());
		threadTest.set(extentTest);
	}

	@AfterMethod
	public synchronized void checkStatus(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {
			// MarkupHelper is used to display the output in different colors
			threadTest.get().log(Status.FAIL,
					MarkupHelper.createLabel("Test Case FAILED --> " + result.getName(), ExtentColor.RED));
			threadTest.get().log(Status.FAIL,
					MarkupHelper.createLabel("Test Case FAILED --> " + result.getThrowable(), ExtentColor.RED));
			String screenshotPath = Utilities.getScreenShot(driver, result.getMethod().getMethodName(),
					result.getTestClass().getName());
			threadTest.get().fail("Test Case Failed").addScreenCaptureFromPath(screenshotPath);
		} else if (result.getStatus() == ITestResult.SKIP) {
			threadTest.get().log(Status.SKIP,
					MarkupHelper.createLabel("Test Case SKIPPED --> " + result.getName(), ExtentColor.ORANGE));
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			threadTest.get().log(Status.PASS,
					MarkupHelper.createLabel("Test Case PASSED --> " + result.getName(), ExtentColor.GREEN));
			String screenshotPath = Utilities.getScreenShot(driver, result.getMethod().getMethodName(),
					result.getTestClass().getName());
			threadTest.get().pass("Test Case Passed").addScreenCaptureFromPath(screenshotPath);
		}
	}

	@AfterTest
	public synchronized void generateReport() {
		extentReports.flush();
		threadTest.remove();
		driver.close();
	}

	@AfterSuite
	public synchronized void endTest() {
		driver.quit();
	}
}
