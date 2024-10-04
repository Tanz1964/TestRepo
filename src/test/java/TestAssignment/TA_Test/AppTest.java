package TestAssignment.TA_Test;

import org.testng.annotations.Test;

import TestAssignment.TA_Pages.App;

public class AppTest extends BaseTest {

	App appFunctions = new App();

	@Test
	public void testApp() {
		
		appFunctions.goToJustWatch(driver);
		appFunctions.searchMovie(driver, threadTest, "Love");
		appFunctions.openMovieDescription(driver, threadTest);
		appFunctions.checkInfoTabs(driver, threadTest);
		appFunctions.goToHome(driver, threadTest);
	}
}
