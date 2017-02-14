package lib_test;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.ibm.watson.developer_cloud.document_conversion.v1.DocumentConversion;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.*;
import com.ibm.watson.developer_cloud.service.exception.UnauthorizedException;

import watson_services.CategoryClassifier;
import watson_services.Credentials;

@SuppressWarnings("unused")
public class NLC_Test {
	public static NaturalLanguageClassifier service;
	private String testID = "f5b42fx173-nlc-3929";
	@Test
	public void testCreds() throws UnauthorizedException {
		try {
			service = new NaturalLanguageClassifier();
			Credentials creds = Credentials.loadCreds("credentials/nlc_cred");
			service.setUsernameAndPassword(creds.getUsername(), creds.getPassword());
			service.getClassifiers().execute().getClassifiers();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testClassification() throws IOException {
		Classification returned = service.classify(testID,"Will it rain today?").execute();
		List<ClassifiedClass> max = returned.getClasses().subList(0, 1);
		assertTrue(max.remove(0).getName().equals("conditions"));
	}
}