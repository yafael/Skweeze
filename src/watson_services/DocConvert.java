package watson_services;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import com.ibm.watson.developer_cloud.document_conversion.v1.DocumentConversion;
import com.ibm.watson.developer_cloud.document_conversion.v1.model.Answers;

public class DocConvert {
	
	private String date = "2015-12-01";
	private String username = "f9b79c9f-8a20-4a1f-aaa7-310d74edb32c";
	private String password = "vbPJHMIMOJYw";
	private DocumentConversion service;
	
	public DocConvert() {
		service = new DocumentConversion(date);
		service.setUsernameAndPassword(username, password);
	}
	
	public String convert(String fileName){
		File resume = new File(fileName);
		String text = service.convertDocumentToText(resume).execute();	
		return text;
	}
}
