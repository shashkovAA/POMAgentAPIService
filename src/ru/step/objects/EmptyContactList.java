package ru.step.objects;

import java.util.ArrayList;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.w3c.dom.NodeList;

public class EmptyContactList extends POMWebServiceOperation{
	private final String actionServiceName = "EmptyContactList";;
	private SOAPMessage response;
	private ArrayList<String> params;
	
	public 	EmptyContactList(ArrayList<String> params){
		this.params = params;
		getLogger().info("Run request for POM WebService:" + actionServiceName);
		
		if (isParamsSufficient())			
		 response = callPOMWebService(actionServiceName);
		else {
			getLogger().error("Number parameters for EmptyContactList is insufficient!");
			getLogger().error("POMWebService not called!");
			System.exit(0);
		}			
	}
		
	private boolean isParamsSufficient() {
		if (params.size() >= 1)
			return true;
		else 		
			return false;
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
        	soapBodyElem.addChildElement("ContactListName", namespace).addTextNode(params.get(0));
		} catch (SOAPException except) {
			getLogger().error(except.getMessage());
			getLogger().debug(printErrorStackTrace(except));
		}
  
	/* Example of Request
	<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:agen="http://services.pim.avaya.com/AgentAPI/">
    <soapenv:Header/>
    <soapenv:Body>
      <agen:EmptyContactList>
         <agen:ContactListName>rshb_test1</agen:ContactListName>
      </agen:EmptyContactList>
    </soapenv:Body>
	</soapenv:Envelope>
		*/
    }	
	
	public String getResponseString() {
		return convertSOAPMessageToString(response); 
	}

	public String getResponseResult() {
		// TODO Auto-generated method stub
		return convertSOAPMessageToResult(response); 
	}

	private String convertSOAPMessageToResult(SOAPMessage response) {
		
		SOAPBody body = null;
		
    	try {
			body = response.getSOAPBody();
		} catch (SOAPException except) {
			getLogger().error(except.getMessage());
		}
    	NodeList list = null;
    	String totalContacts = "-1";
    	String soapResponseString = convertSOAPMessageToString(response);
    	
    	String partResponse = soapResponseString.substring(0,soapResponseString.indexOf(namespaceURI));
    	String responseNamespace = partResponse.substring(partResponse.lastIndexOf(':') + 1, partResponse.lastIndexOf('='));
    	
    	//Find namespace from Response. Example: It is [ns1] from  xmlns:ns1="http://services.pim.avaya.com/CmpMgmt/"
    	list = body.getElementsByTagName(responseNamespace + ":TotalContacts");
    	
    	if (list.getLength()!=0)
    		try {
    			totalContacts = list.item(0).getTextContent();
    		} catch (Exception except){
    			getLogger().error(except.getMessage());
    			getLogger().debug(printErrorStackTrace(except));
    		}
    	
    	return totalContacts;
	}

}
