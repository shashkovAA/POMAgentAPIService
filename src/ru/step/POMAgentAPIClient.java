package ru.step;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

import ru.step.objects.EmptyContactList;
import ru.step.objects.GetContactAttributeValueFromList;
import ru.step.objects.MyLogger;
import ru.step.objects.UpdateContactAttributeValueToList;



public class POMAgentAPIClient {
	private final String PROPERTYFILE = "epm.properties";
	
	private final static Properties properties = new Properties();
	private String webServiceResponse;
	
	
	public POMAgentAPIClient() {
		
		MyLogger.initLogger();
		
		loadProperties();
	}
	
	public String emptyContactList(String contactListName){
		EmptyContactList emptyContactList = new EmptyContactList(contactListName);
		webServiceResponse = emptyContactList.getResponseString();
		getLogger().info(webServiceResponse);
		return webServiceResponse;

	}	
		
			
	public String updateContactAttributeValueToList(String contactID, String contactListName, String attributeName, String attributeValue){
		
		UpdateContactAttributeValueToList updateContactAttributeValueToList = new UpdateContactAttributeValueToList(contactID, contactListName, attributeName, attributeValue);
		webServiceResponse = updateContactAttributeValueToList.getResponseString();
		
		getLogger().info(webServiceResponse);
		return webServiceResponse;
	}
	
	public String getContactAttributeValueFromList(String contactID, String contactListName, String attributeName){
		
		GetContactAttributeValueFromList getContactAttributeValueFromList = new GetContactAttributeValueFromList(contactID, contactListName, attributeName);
		webServiceResponse = getContactAttributeValueFromList.getResponseString();
		
		getLogger().info(webServiceResponse);
		return webServiceResponse;
	}
	
	
	private void loadProperties() {   	

		try{
	   	  	  FileInputStream fis = new FileInputStream(PROPERTYFILE);
		      properties.load( fis );
		      getLogger().debug("Getting properties from file [" + PROPERTYFILE + "]:");
		      
		      for (Enumeration names = properties.propertyNames(); names.hasMoreElements();) {
		      	String key = (String) names.nextElement();
		      	getLogger().debug("key =" + key + " value =" + properties.getProperty(key, ""));
		      	System.setProperty(key, properties.getProperty(key, ""));	            
		      }
		      fis.close();
	   	  } catch (FileNotFoundException e) {
	   	      getLogger().error("Failed to load properties file: exception=" + e.getMessage());
	   	      System.exit(0);
	   	  } catch (IOException e) {
	   	      getLogger().error("Failed to load properties file: exception=" + e.getMessage());
	   	      System.exit(0);
	   	  }	      
	   }
	
	
	private Logger getLogger() {		
	    return MyLogger.log;
	}
	
	private String printErrorStackTrace(Exception except) {
		StringWriter sw = new StringWriter();
    	except.printStackTrace(new PrintWriter(sw));
    	return sw.toString();
	}
	
	public static void main(String[] args) {
		
		POMAgentAPIClient client = new POMAgentAPIClient();
				
		String result = client.updateContactAttributeValueToList("1001", "TestList", "retrycount", "2");
		
	}
	
}

