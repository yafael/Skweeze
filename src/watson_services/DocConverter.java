package watson_services;
import java.io.File;
import java.io.IOException;

import com.ibm.watson.developer_cloud.document_conversion.v1.DocumentConversion;

public class DocConverter {
	
	private DocumentConversion service;
	
	public DocConverter() throws IOException{
		Credentials creds = Credentials.loadCreds("credentials/docconv_cred");
		service = new DocumentConversion(creds.getDate());
		service.setUsernameAndPassword(creds.getUsername(), creds.getPassword());
	}
	
	public String convert(String fileName){
		File resume = new File(fileName);
		String text = service.convertDocumentToText(resume).execute();
		return text;
	}
}
