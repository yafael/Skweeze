package lib_test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import com.ibm.watson.developer_cloud.document_conversion.v1.DocumentConversion;
import com.ibm.watson.developer_cloud.service.exception.UnauthorizedException;

import org.junit.Test;

import watson_services.Credentials;
import watson_services.DocConverter;

public class DocConvert_Test {
	@Test
	public void testCreds() throws UnauthorizedException {
		try {
			Credentials creds = Credentials.loadCreds("credentials/docconv_cred");
			DocumentConversion service = new DocumentConversion(creds.getDate());
			service.setUsernameAndPassword(creds.getUsername(), creds.getPassword());
			service.convertDocumentToAnswer(new File("data/CV.pdf")).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testDoc() throws IOException{
		DocConverter dc = new DocConverter();
		String returned = dc.convert("data/test.doc");
		assertTrue(returned.contains("test"));
	}
	@Test
	public void testDocx() throws IOException{
		DocConverter dc = new DocConverter();
		String returned = dc.convert("data/test.docx");
		assertTrue(returned.contains("test"));
	}
	@Test
	public void testPDF() throws IOException{
		DocConverter dc = new DocConverter();
		String returned = dc.convert("data/test.pdf");
		assertTrue(returned.contains("test"));
	}
}
