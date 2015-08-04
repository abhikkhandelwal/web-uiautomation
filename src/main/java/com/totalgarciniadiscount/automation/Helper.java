package com.totalgarciniadiscount.automation;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.openqa.selenium.By;

public class Helper {

	public static boolean sendMailwithAttachment() {

		// Create the attachment
		EmailAttachment attachment = new EmailAttachment();

		try {
			attachment.setPath(new File(".").getCanonicalPath()
					+ File.separator + "test-output" + File.separator
					+ "emailable-report.html");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("Status of Automation");
		attachment.setName("Report.html");

		// Create the email message
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(25);
		email.setAuthenticator(new DefaultAuthenticator("teknotrait@gmail.com",
				"Techy123"));
		email.setSSLOnConnect(true);
		try {
			email.addTo("croissance.abhik@gmail.com", "Abhik");
			email.setFrom("teknotrait@gmail.com", "Teknotrait Email");
			email.setSubject("Automation Test Execution Report");
			email.setMsg("Here is the automation test execution report.");
			email.attach(attachment);
			// send the email
			email.send();
			return true;
		} catch (EmailException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static By getLocator(String strElement, Properties prop)
			throws Exception {

		// retrieve the specified object from the object list
		String locator = prop.getProperty(strElement);

		// extract the locator type and value from the object
		String locatorType = locator.split(":")[0];
		String locatorValue = locator.split(":")[1];

		// for testing and debugging purposes
		System.out.println("Retrieving object of type '" + locatorType
				+ "' and value '" + locatorValue + "' from the object map");

		// return a instance of the By class based on the type of the locator
		// this By can be used by the browser object in the actual test
		switch (locatorType.toLowerCase().trim()) {
		case "id":
			return By.id(locatorValue);
		case "name":
			return By.name(locatorValue);
		case "classname":
		case "class":
			return By.className(locatorValue);
		case "tagname":
		case "tag":
			return By.className(locatorValue);
		case "linktext":
		case "link":
			return By.linkText(locatorValue);
		case "partiallinktext":
			return By.partialLinkText(locatorValue);
		case "cssselector":
		case "css":
			return By.cssSelector(locatorValue);
		case "xpath":
			return By.xpath(locatorValue);
		default:
			throw new Exception("Unknown locator type '" + locatorType + "'");
		}
	}

}
