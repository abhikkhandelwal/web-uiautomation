package com.totalgarciniadiscount.automation;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestBase {

	public static Properties config = null;
	public static Properties OR = null;
	public static boolean loggedIn = false;
	public WebDriver wbDv = null;
	public static EventFiringWebDriver driver = null;
	public static MSExcelAutomation datatable = null;
	protected DesiredCapabilities capabilities = new DesiredCapabilities();

	@BeforeSuite
	public void initialize() {

		// loading all the configuration values
		try {
			config = new Properties();
			FileInputStream fp = new FileInputStream(new File(".").getCanonicalPath() + File.separator
					+ "config" + File.separator + "config.properties");
			config.load(fp);

			// loading Objects Repositories
			OR = new Properties();
			fp = new FileInputStream(new File(".").getCanonicalPath()
					+ File.separator + "config" + File.separator
					+ "ObjectRepo.properties");
			OR.load(fp);

			datatable = new MSExcelAutomation(new File(".").getCanonicalPath()
					+ File.separator + "xls" + File.separator
					+ "Controller.xlsx");
			// checking the type of browser
			if (config.getProperty("browserType").equalsIgnoreCase("Firefox")) {

				wbDv = new FirefoxDriver();

			} else if (config.getProperty("browserType")
					.equalsIgnoreCase("IE")) {

				wbDv = new InternetExplorerDriver();
				DesiredCapabilities ieCapabilities = DesiredCapabilities
						.internetExplorer();
				ieCapabilities
						.setCapability(
								InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
								true);
				wbDv = new InternetExplorerDriver(ieCapabilities);

			}

			else if (config.getProperty("browserType").equalsIgnoreCase(
					"Chrome")) {

							System.setProperty("webdriver.chrome.driver",
							System.getProperty("user.dir")
									+ "\\SeleniumLibrary\\chromedriver.exe");
					wbDv = new ChromeDriver();
				}

			driver = new EventFiringWebDriver(wbDv);

			// putting an implicit wait after every Action or Event
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// opening the browser
			driver.navigate().to(config.getProperty("testSiteURL"));

			// maximizing the windows
			driver.manage().window().maximize();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/*
	 * public static WebElement getObject(String xpathKey){
	 * 
	 * WebElement obj = null;
	 * 
	 * try{ obj = driver.findElement(By.xpath(OR.getProperty(xpathKey)));
	 * 
	 * }catch(Exception e){
	 * 
	 * obj = null; }
	 * 
	 * return obj;
	 * 
	 * }
	 */

	/**
	 * This function is used to identify the object on the Application
	 * 
	 * @author Abhik
	 * @param xpathKey
	 *            - unique sudo name which we have kept for every object on the
	 *            web page
	 * @return WebElement
	 */
	public static WebElement getObject(String pathKey) {
		return TestBase.getObject(pathKey, OR);
	}

	public static WebElement getObject(String pathKey, Properties prop) {

		WebElement obj = null;

		try {
			obj = driver.findElement(Helper.getLocator(pathKey, prop));
		} catch (Exception e) {
			obj = null;
		}
		return obj;
	}

	/**
	 * This function is used to identify the objects on the Application
	 * 
	 * @author Abhik
	 * @param xpathKeyOfElements
	 * @return List<WebElement>
	 */
	public static List<WebElement> getObjects(String xpathKeyOfElements) {

		List<WebElement> obj = null;

		try {
			obj = driver.findElements(By.xpath(OR
					.getProperty(xpathKeyOfElements)));

		} catch (Exception e) {

			obj = null;
		}

		return obj;

	}

	/**
	 * This method will remove all items from the cart
	 * 
	 * @throws InterruptedException
	 *             @ Author : Abhik
	 */
	public static void removeAllItemsFromCart() throws InterruptedException {

		// Precondition : Empty the cart before starting Execution
		Reporter.log("Emptying the cart before the test");

		// click on 'View cart' button
		WebUIAutomation.clickObj("LNK_ViewCart_HEADER");

		int itemNo = getObjects("BTN_Remove_SHOPPINGCART").size();

		for (int i = 1; i <= itemNo; i++) {
			WebUIAutomation
					.clickObj("BTN_RemoveFirstProductFromCart_SHOPPINGCART");
			Thread.sleep(3000);
		}

		driver.navigate().to(config.getProperty("testSiteURL"));
	}

	public static boolean logIn(String userName, String password) {

		Reporter.log("Enter user, password and click Log in button");

		// Enter username
		WebUIAutomation.setText("TXT_UserName_LOGINPAGE", userName);

		// Enter password
		WebUIAutomation.setText("TXT_EnterPassword_LOGINPAGE", password);

		// click on 'Log in' button
		WebUIAutomation.clickObj("BTN_Login_LOGINPAGE");

		// Wait for the page load
		WebUIAutomation.isObjPresent("TXT_EmailId_HEADER", 20);

		// Verify whether user is logged in or not
		Reporter.log("Verify Log in of User");

		String userId = getObject("TXT_EmailId_HEADER").getText(); // store
																	// userId
																	// into a
																	// String
																	// variable
																	// for
																	// verification

		if (userId.equalsIgnoreCase(userName)) {

			return true;
		} else
			return false;
	}

	@AfterSuite
	public void closeBrowser() {

		driver.quit();
		if (!Helper.sendMailwithAttachment()) {
			System.out.println("Mail hasn't Sent successfully");
		}
	}

}
