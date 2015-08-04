package com.totalgarciniadiscount.automation;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class WebUIAutomation extends TestBase {
	
	
	/**
	 * This is an overloaded function which is used to check that Web Element exist on the page or not
	 * @param xpathKey
	 * @return boolean true/false
	 */
	public static boolean isElementPresent(String xpathKey){
		
		try{
			WebElement webElem = getObject(xpathKey);
			if(!(webElem == null)){
				
				return true;
				
			}else{
				
				return false;
			}
			
		}catch(Exception e){
			
			return false;
		}
	}
	
	
	
	/**
	 * This is an overloaded function which is used to click elements on the web page
	 * @param xpathKey
	 * @return boolean - true/false
	 */
	public static boolean clickObj(String xpathKey){
		
		try{
			if(isElementPresent(xpathKey)){
				
				getObject(xpathKey).click();
				return true;
				
			}else{
				
				return false;
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	/**
	 * This is an overloaded function which is used to enter value in the text box
	 * @param xpathKey
	 * @param value which needs to enter
	 * @return boolean - true/false
	 */
	public static boolean setText(String xpathKey, String value){
		
		try{
			if(isElementPresent(xpathKey)){
				
				// Clearing the text box before typing value
				getObject(xpathKey).clear();
				
				// typing the value in the text box
				getObject(xpathKey).sendKeys(value);
				
				return true;
				
			}else{
				
				return false;
			}
			
			
		}catch(Exception e){
			
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	/**
	 * This is an overloaded function used to wait for the element till it loads completely
	 * @param xpathKey
	 * @param maxTime maximum time in seconds for which we want the web driver to wait for the element
	 * @return boolean - true/false
	 */
	public static boolean isObjPresent(String xPathKey, int maxTime){
		
		try{
			for(int i=0; i<=maxTime; i++) {
				
				Thread.sleep(1000);
				if(isElementPresent(xPathKey)){
					
					return true;
				}
				
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			return false;
		}
		
		return false;
	}
	
	
	
	
	/**
	 * This is an overloaded function which is used to select value from drop down
	 * @param driver instance
	 * @param xpathKey
	 * @param strValue value which needs to be selected from drop down
	 * @return boolean - true/false
	 */
	public static boolean selectValueFromDrpDwn(String xPathKey, String strValue){
		
		try{
			Select element;
			if(!strValue.equals("")){
				
				element = new Select(getObject(xPathKey));
				element.selectByVisibleText(strValue);
			}
			
			return true;
			
		}catch(Exception e){
			
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	/**
	 * This function is used to check the value Run mode column inside the Test Case Excel file
	 * @param xls instance of Test Case Excel file
	 * @param testCase Name of the test case for which Run mode value needs to check
	 * @return boolean - true/false
	 */
	public static boolean checkTestCaseRunmode(String testCase){
		
		for(int i=2; i<=datatable.getRowCount("TotalGarciniaDiscount"); i++){
			
			if(datatable.getCellData("TotalGarciniaDiscount", "Test Case ID", i).equalsIgnoreCase(testCase)){
				
				if(datatable.getCellData("TotalGarciniaDiscount", "Runmode", i).equalsIgnoreCase("Y")){
					
					return true;
					
				}else{
					
					return false;
				}
				
			}
		}
		
		datatable = null;
		return false;
	}
	
	
	
	
	/**
	 * This function is used to get the complete data from the sheet into 2 dimensional array
	 * @param xls instance of Test Case Excel file
	 * @param sheetName Name of the sheet inside which Data is present
	 * @return Object[][] stores the data into 2D array
	 */
	public static Object[][] getData(String sheetname){
		
		if(!datatable.isSheetExist(sheetname)){
			
			datatable = null;
			return new Object[1][0];
			
		}
		
		// return and read test data from excel
		int rows = datatable.getRowCount(sheetname)-1;
		if(rows <=0){
			
			Object[][] testData = new Object[1][0];
			return testData;
		}
		
		rows = datatable.getRowCount(sheetname);
		int cols = datatable.getColumnCount(sheetname);
		
		Object data[][] = new Object[rows-1][cols];
		
		for(int rowNum=2; rowNum<=rows; rowNum++){
			
			for(int colNum=0; colNum<cols; colNum++){
				
				data[rowNum-2][colNum] = datatable.getCellData(sheetname, colNum, rowNum);
			}
		}
		
		return data;
	}
	

}
