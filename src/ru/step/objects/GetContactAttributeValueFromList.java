package ru.step.objects;

import java.util.ArrayList;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class GetContactAttributeValueFromList extends POMWebServiceOperation {
	private final String actionServiceName = "GetContactAttributeValueFromList";;
	private SOAPMessage response;

	private String contactID;
	private String contactListName;
	private String attributeName;
	
	
	public 	GetContactAttributeValueFromList(String contactID, String contactListName, String attributeName){
		this.contactID = contactID;
		this.contactListName = contactListName;
		this.attributeName = attributeName;
		
		getLogger().info("Run request for POM WebService:" + actionServiceName);
				
	}

	@Override
	public void createSoapEnvelope(SOAPMessage soapMessage) {
		SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = null;
        SOAPBody soapBody;
        SOAPElement soapBodyElem;
        
		try {
			envelope = soapPart.getEnvelope();
			envelope.addNamespaceDeclaration(namespace, namespaceURI);
			soapBody = envelope.getBody();
			    		
    		soapBodyElem = soapBody.addChildElement(actionServiceName, namespace);		    		
    		soapBodyElem.addChildElement("ContactID", namespace).addTextNode(contactID);
        	soapBodyElem.addChildElement("ContactListName", namespace).addTextNode(contactListName);
        	soapBodyElem.addChildElement("AttributeName", namespace).addTextNode(attributeName);

		} catch (SOAPException except) {
			getLogger().error(except.getMessage());
			getLogger().debug(printErrorStackTrace(except));
		}
  
	/* Example of Request
	
		*/
    }	
	
	public String getResponseString() {
		return convertSOAPMessageToString(response); 
	}
}
