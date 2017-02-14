package watson_services;

import java.io.IOException;
import java.util.List;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.ClassifiedClass;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classifier;

public class CategoryClassifier {

	private String classifierId = "f5b432x172-nlc-3527";
	private NaturalLanguageClassifier service;
	
	public CategoryClassifier() throws IOException {
		service = new NaturalLanguageClassifier();
		Credentials creds = Credentials.loadCreds("credentials/nlc_cred");
		service.setUsernameAndPassword(creds.getUsername(), creds.getPassword());
	}
	
	public List<ClassifiedClass> getTopClasses(String input, int numberOfClasses) {
		Classifier classifier = service.getClassifier(classifierId).execute();
		Classification classification = service.classify(
				classifier.getId(), input).execute();
		List<ClassifiedClass> topClasses = classification.getClasses().subList(0, numberOfClasses);
		return topClasses;
	}
	
}
