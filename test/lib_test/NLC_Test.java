package lib_test;
import java.io.File;
import java.io.IOException;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.*;
import watson_services.Credentials;

@SuppressWarnings("unused")
public class NLC_Test {
	public static void main(String[] args) throws IOException {
		Credentials creds = Credentials.loadCreds("credentials/nlc_cred");
		NaturalLanguageClassifier service = new NaturalLanguageClassifier();
		service.setUsernameAndPassword(creds.getUsername(), creds.getPassword());
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
