package MFRP.test;

import MFRP.BasePage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class TestCases extends BasePage {

	@Test
	public void test1() throws IOException {

		logger = report.createTest("Test One");

		invokeBrowser("Chrome");
		openURL("websiteURL");
		
		String filepath = System.getProperty("user.dir") +"\\TestData\\Test_Data.xlsx";
		Workbook wb = null;
		try {
			new WorkbookFactory();
			wb = WorkbookFactory.create(new FileInputStream(new File(filepath)));
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet sh = wb.getSheet("DATA");
		Driver.findElement(By.xpath("//*[@id='suggestionBoxEle']")).sendKeys(sh.getRow(1).getCell(0).getStringCellValue() + Keys.ENTER);
		
		click("searchbutton_Id", "Search");
		
		int data = (int) wb.getSheetAt(0).getRow(1).getCell(1).getNumericCellValue();
		String str1 = Integer.toString(data);
		Driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/main/div/div/div[2]/div[1]/div/div[1]/div[4]/form/input[1]")).sendKeys(str1);
		int data1 = (int) wb.getSheetAt(0).getRow(1).getCell(2).getNumericCellValue();
		String str2 = Integer.toString(data1);
		Driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/main/div/div/div[2]/div[1]/div/div[1]/div[4]/form/input[2]")).sendKeys(str2);
		
		click("gobutton_Xpath", "Apply Filter");

		String value1 = gettext("name1_Xpath");
		String item1 = gettext("price1_Xpath");
		String value2 = gettext("name2_Xpath");
		String item2 = gettext("price2_Xpath");
		String value3 = gettext("name3_Xpath");
		String item3 = gettext("price3_Xpath");
		String value4 = gettext("name4_Xpath");
		String item4 = gettext("price4_Xpath");
		String value5 = gettext("name5_Xpath");
		String item5= gettext("price5_Xpath");

		

		int rowCount = sh.getLastRowNum() - sh.getFirstRowNum();
		Row newRow = sh.createRow(rowCount + 1);

		Cell cell0 = newRow.createCell(0);
		cell0.setCellValue(value1);

		Cell cell1 = newRow.createCell(1);
		cell1.setCellValue(value2);
		
		Cell cell2 = newRow.createCell(2);
		cell2.setCellValue(value3);
		
		Cell cell3 = newRow.createCell(3);
		cell3.setCellValue(value4);

		Cell cell4 = newRow.createCell(4);
		cell4.setCellValue(value5);
		
		Row newRow1 = sh.createRow(rowCount + 2);

		Cell cell_0 = newRow1.createCell(0);
		cell_0.setCellValue(item1);

		Cell cell_1 = newRow1.createCell(1);
		cell_1.setCellValue(item2);
		
		Cell cell_2 = newRow1.createCell(2);
		cell_2.setCellValue(item3);
		
		Cell cell_3 = newRow1.createCell(3);
		cell_3.setCellValue(item4);

		Cell cell_4 = newRow1.createCell(4);
		cell_4.setCellValue(item5);

		
		FileOutputStream fos = new FileOutputStream(filepath);
		wb.write(fos);
		fos.close();
		
		takeScreenshot();
		
		quitBrowser();

	}

	@AfterTest

	public void endReport() {
		report.flush();
	}

}
