package lib_test;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.*;
import com.ibm.watson.developer_cloud.service.exception.UnauthorizedException;
import watson_services.Credentials;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NLC_Test {
	public static NaturalLanguageClassifier service;
	private String testID = "f5b42fx173-nlc-3929";
	private String classifierID = "f5b432x172-nlc-3527";
	@Test
	public void aTestCreds() throws UnauthorizedException {
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
	public void bTrainedClassifiersExist(){
		assertTrue(service.getClassifiers().execute().getClassifiers().size()>0);
	}
	@Test
	public void cClassifierCanAdd(){
		assertTrue(service.getClassifiers().execute().getClassifiers().size()<8);
	}
	@Test
	public void dClassiferJobReady(){
		assertTrue(service.getClassifier(classifierID).execute().getStatus().equals(Classifier.Status.AVAILABLE));
	}
	@Test
	public void dClassiferTestReady(){
		assertTrue(service.getClassifier(testID).execute().getStatus().equals(Classifier.Status.AVAILABLE));
	}
	@Test
	public void eTestClassification(){
		Classification returned = service.classify(testID,"Will it rain today?").execute();
		assertTrue(returned.getClasses().remove(0).getName().equals("conditions"));
	}
	@Test
	public void fTestJobClassificationCSE(){
		Classification returned = service.classify(classifierID, "computer").execute();
		assertTrue(returned.getClasses().remove(0).getName().equals("Information Technology"));
	}
	@Test
	public void gTestJobClassificationPsych(){
		Classification returned = service.classify(classifierID, "psychology").execute();
		assertTrue(returned.getClasses().remove(0).getName().equals("Social Science, Psychologist"));
	}
	@Test
	public void hTestJobClassificationEducation(){
		Classification returned = service.classify(classifierID, "teach").execute();
		assertTrue(returned.getClasses().remove(0).getName().equals("Education"));
	}
}