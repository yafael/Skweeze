package lib_test;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.ibm.watson.developer_cloud.document_conversion.v1.DocumentConversion;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.*;
import com.ibm.watson.developer_cloud.service.exception.UnauthorizedException;

import watson_services.CategoryClassifier;
import watson_services.Credentials;

@SuppressWarnings("unused")
public class NLC_Test {
	@Test
	public void testCreds() throws UnauthorizedException {
		try {
			Credentials creds = Credentials.loadCreds("credentials/nlc_cred");
			NaturalLanguageClassifier service = new NaturalLanguageClassifier();
			service.setUsernameAndPassword(creds.getUsername(), creds.getPassword());
			for (int i=0; i<8; i++){
				System.out.println(service.getClassifiers().execute().getClassifiers().get(i));
			}
//			for (int i=1;i<8;i++){
//				service.deleteClassifier(service.getClassifiers().execute().getClassifiers().get(i).getId());
//			}
			//service.createClassifier("test", "en", new File("data/weather_data_train.csv")).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testClassification() throws IOException {
		CategoryClassifier classifier = new CategoryClassifier();
		List<ClassifiedClass>returned = classifier.getTopClasses("Will it rain today?", 1);
		System.out.println(returned);
		//Create classifier
		//Classifier classifier = service.createClassifier("test","en",
				//new File("data/keyword_data.csv")).execute();
//		System.out.println(classifier);
		//Query status (training, available, etc) TODO decide how to get classifier ID
		//Classifier classifier = service.getClassifiers().execute().getClassifiers().get(0);
		//System.out.println(classifier);
		//Classify sentence
//		Classification classification = service.classify(
//				classifier.getId(),"Will it rain Saturday?").execute();
//		System.out.println(classification);
		//Delete classifier
//		service.deleteClassifier("f5b432x172-nlc-2347").execute();
	}
}