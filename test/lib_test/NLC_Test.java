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
//		Classifier classifier = service.createClassifier("test","en",
//				new File("data/weather_data_train.csv")).execute();
//		System.out.println(classifier);
		//Query status TODO find out how to get classifier ID automatically
		Classifier classifier = service.getClassifier("f5b432x172-nlc-2347").execute();
		System.out.println(classifier);
		//Classify sentence
		Classification classification = service.classify(
				"f5b432x172-nlc-2347","How hot will it be today?").execute();
		System.out.println(classification);
		//Delete classifier
//		service.deleteClassifier("f5b432x172-nlc-2347").execute();
	}
}
