package TestAssignment.TA_Elements;

public class ElementPath {
	
	//This is the list of System Variables
    //Declared as 'static', so that we do not need to instantiate a class object
    //Declared as 'final', so that the value of this variable can be changed
	
	public static final String searchBar = "Search Bar;//input[@aria-label='search text']";
	public static final String movieName = "Movie;//div[@class='title-list-row']//span[@class='header-title']";
	public static final String infoTabs = "Tab;//div[@class='info-container']//div[@class='hidden-horizontal-scrollbar__items']/a";
	public static final String homeLink = "Home;//a/span[text()='Home']";
	

}
