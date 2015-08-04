package com.totalgarciniadiscount.automation;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Buy2CheckoutTest extends TestBase {
	
	// Checking the script has to execute or not
		@BeforeTest
		public void doIHaveToSkip() {

			if (!(WebUIAutomation.checkTestCaseRunmode(this.getClass()
					.getSimpleName()))) {
				System.out.println(this.getClass().getSimpleName());
				throw new SkipException("Runmode set to No");
			}
		}
		
		@Test(dataProvider = "getData")
		// Buy one check out test
		public void buy2CheckoutTest(String FirstName, String LastName,
				String Address, String City, String State, String Zip,
				String Email, String Phone, String CCN, String CardType,
				String CardExpMonth, String CardExpYear, String CVV) {

			// Click on purchase link on home page
			Reporter.log("Click on purchase link on home page ");
			if (!WebUIAutomation.clickObj("lnk_purchase_home")) {
				Assert.fail("Purchase link has not been clicked successfully"
						+ "And subsquient page has not been displayed");
			}
			// Click on add to cart button of buy two bottle
			Reporter.log("Click on add to cart button of buy two bottle");

			WebUIAutomation.isObjPresent("btn_addtocartbuy2_purchase", 20);
			if (!WebUIAutomation.clickObj("btn_addtocartbuy2_purchase")) {
				Assert.fail("add to cart button has not been clicked successfully"
						+ "And subsquient page has not been displayed");
			}
			// Enter Details into billing details form
			Reporter.log("Entering Details into billing details form");
			if (!WebUIAutomation.setText("txt_firstname_billingdetails", FirstName)) {

				Assert.fail("Text has not been entered sucessfully into firstname text field");
			}
			if (!WebUIAutomation.setText("txt_lastname_billingdetails", LastName)) {
				Assert.fail("Text has not been entered sucessfully into lastname text field");
			}

			if (!WebUIAutomation.setText("txt_address_billingdetails", Address)) {
				Assert.fail("Text has not been entered sucessfully into Address text field");
			}
			if (!WebUIAutomation.setText("txt_city_billingdetails", City)) {
				Assert.fail("Text has not been entered sucessfully into City text field");
			}
			if (!WebUIAutomation.selectValueFromDrpDwn(
					"dropdown_state_billingdetails", State)) {
				Assert.fail("Text has not been entered sucessfully into State text field");
			}
			if (!WebUIAutomation.setText("txt_zip_billingdetails", Zip)) {
				Assert.fail("Text has not been entered sucessfully into Zip text field");
			}
			if (!WebUIAutomation.setText("txt_email_billingdetails", Email)) {
				Assert.fail("Text has not been entered sucessfully into Email text field");
			}
			if (!WebUIAutomation.setText("txt_phn_billingdetails", Phone)) {
				Assert.fail("Text has not been entered sucessfully into Phone text field");
			}
			if (!WebUIAutomation.setText("txt_cc_billingdetails", CCN)) {
				Assert.fail("Text has not been entered sucessfully into CCN text field");
			}
			if (!WebUIAutomation.selectValueFromDrpDwn("dropdown_cardtype_billingdetails", CardType)) {
				Assert.fail("card type has not been selected sucessfully into CardType field");
			}
			if (!WebUIAutomation.selectValueFromDrpDwn("dropdown_cardexpmnth_billingdetails", CardExpMonth)) {
				Assert.fail("Text has not been entered sucessfully into CardExpMonth text field");
			}
			if (!WebUIAutomation.selectValueFromDrpDwn("dropdown_cardexpyear_billingdetails", CardExpYear)) {
				Assert.fail("CardExpMonth has not been selected sucessfully into CardExpYear field");
			}
			if (!WebUIAutomation.setText("txt_CVV_billingdetails", CVV)) {
				Assert.fail("CVV has not been entered sucessfully into CVV text field");
			}
			if (!WebUIAutomation.clickObj("chkbox_terms_conditions_billingdetails")) {
				Assert.fail("Checkbox has not been clicked successfully");
			}
			if (!WebUIAutomation.clickObj("btn_placeorder_billingdetails")) {
				Assert.fail("Place Order button has not been clicked successfully");
			}
			
			if (WebUIAutomation.getObject("txt_error_msg").getText().equalsIgnoreCase("There Seems To Be A Problem!")) {
				Assert.fail("Unsucessful Transaction");
			}
		
		}

		@DataProvider
		public Object[][] getData() {

			return WebUIAutomation.getData("Buy3CheckoutTest");
		}


}
