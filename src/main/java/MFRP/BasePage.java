package MFRP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.*;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utilities.Dateutils;
import utils.ExtentReport;

public class BasePage {

	public WebDriver Driver;
	public Properties prop;
	public ExtentReports report = ExtentReport.getReportInstance();
	public ExtentTest logger;
	public void invokeBrowser(String Browsername) {
try {
		if (Browsername.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\Drivers\\chromedriver.exe");
			Driver = new ChromeDriver();

		} else {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\src\\Drivers\\geckodriver.exe");
			Driver = new FirefoxDriver();
		}
		}
		catch (Exception e) {
			reportFail(e.getMessage());
		}
		Driver.manage().timeouts().implicitlyWait(260, TimeUnit.SECONDS);
		Driver.manage().timeouts().pageLoadTimeout(260, TimeUnit.SECONDS);
		Driver.manage().window().maximize();

		if (prop == null) {
			prop = new Properties();
			try {
				FileInputStream file = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\propConfig.properties");
				prop.load(file);
			} catch (Exception e) {
				reportFail(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void openURL(String websiteURLkey) {
		try {
		Driver.get(prop.getProperty(websiteURLkey));
		reportPass(websiteURLkey +"Identified Successfully");
		}
		catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void tearDown() {
		Driver.close();

	}

	public void quitBrowser() {

		Driver.quit();
	}


	public void entertext(String xpathkey, String value) {
		try {
		getElement(xpathkey).sendKeys(value);
		reportPass(value+ "-Entered Successfully in locator Element:"+ xpathkey);
	}catch (Exception e) {
		reportFail(e.getMessage());
	}
	}
	
	
	

	public void click(String xpathkey, String value) {
		try {
		getElement(xpathkey).click();
		reportPass(value +" Button Clicked Successfully ");
	} catch (Exception e) {
		reportFail(e.getMessage());
	}
	}

	public String gettext(String xpathkey) {
		
		String value=getElement(xpathkey).getText();
		reportPass("Item Successfully Retrieved: " +xpathkey);
	
		return value;
	}
	public WebElement getElement(String locatorkey) {
		WebElement element = null;
		try {

			if (locatorkey.endsWith("_Xpath")) {
				element = Driver.findElement(By.xpath(prop.getProperty(locatorkey)));
				logger.log(Status.INFO, "Locator Identified:"+locatorkey);
			} else if (locatorkey.endsWith("_Id")) {
				element = Driver.findElement(By.id(prop.getProperty(locatorkey)));
				logger.log(Status.INFO, "Locator Identified:"+locatorkey);
			} else if (locatorkey.endsWith("_CSS")) {
				element = Driver.findElement(By.cssSelector(prop.getProperty(locatorkey)));
				logger.log(Status.INFO, "Locator Identified:"+locatorkey);
			} else {
				reportFail("Failing the Testcase, Invalid Loctor" + locatorkey);

			}
		} catch (Exception e) {
			reportFail(e.getMessage());
			e.printStackTrace();

			Assert.fail("Failing the Testcase:" + e.getMessage());
			takeScreenshot();

		}

		return element;
	}

	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenshot();
		Assert.fail(reportString);

	}

	public void reportPass(String reportString) {

		logger.log(Status.PASS, reportString);
	}

	public void takeScreenshot() {

		TakesScreenshot takeScreenshot = (TakesScreenshot) Driver;
		File sourcefile = takeScreenshot.getScreenshotAs(OutputType.FILE);
		File destfile = new File(System.getProperty("user.dir")+"\\ScreenShots\\" + Dateutils.getTimeStamp()+".png");
		try {
			FileUtils.copyFile(sourcefile, destfile);
			logger.addScreenCaptureFromPath(System.getProperty("user.dir")+"\\ScreenShots\\" + Dateutils.getTimeStamp()+".png");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
