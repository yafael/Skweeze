package lib_test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import com.ibm.watson.developer_cloud.document_conversion.v1.DocumentConversion;
import com.ibm.watson.developer_cloud.service.exception.UnauthorizedException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import watson_services.Credentials;
import watson_services.DocConverter;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DocConvert_Test {
	@Test
	public void aTestCreds() throws UnauthorizedException {
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
	public void bTestDoc() throws IOException{
		DocConverter dc = new DocConverter();
		String returned = dc.convert("data/test.doc");
		assertTrue(returned.contains("test"));
	}
	@Test
	public void cTestDocx() throws IOException{
		DocConverter dc = new DocConverter();
		String returned = dc.convert("data/test.docx");
		assertTrue(returned.contains("test"));
	}
	@Test
	public void dTestPDF() throws IOException{
		DocConverter dc = new DocConverter();
		String returned = dc.convert("data/test.pdf");
		assertTrue(returned.contains("test"));
	}
}
