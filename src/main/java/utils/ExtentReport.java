package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;

import utilities.Dateutils;


public class ExtentReport {
	
	//public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports report;
	
	public static ExtentReports getReportInstance() {
		if(report==null) {
			String reportName = Dateutils.getTimeStamp()+".html";
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\test-output\\test-report.html");
			report = new ExtentReports();
			report.attachReporter(htmlReporter);
			
			report.setSystemInfo("OS", "Windows 10");
			report.setSystemInfo("Environment", "U&T");
			report.setSystemInfo("Build", "10.8.1");
			report.setSystemInfo("Browser", "Chrome");
			
			htmlReporter.config().setDocumentTitle("U&T UI Automation Results");
			htmlReporter.config().setReportName("All headlines UI Test Report");
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
		}
		
		return report;
	}

}
