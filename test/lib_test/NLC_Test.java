package lib_test;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.Test;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.*;
import com.ibm.watson.developer_cloud.service.exception.UnauthorizedException;
import watson_services.Credentials;

public class NLC_Test {
	public static NaturalLanguageClassifier service = loadService();
	@SuppressWarnings("unused")
	private String testID = "f5b42fx173-nlc-3929";
	private String classifierID = "f5b432x172-nlc-3527";
	
	public static NaturalLanguageClassifier loadService() throws UnauthorizedException {
		try {
			NaturalLanguageClassifier service = new NaturalLanguageClassifier();
			Credentials creds = Credentials.loadCreds("credentials/nlc_cred");
			service.setUsernameAndPassword(creds.getUsername(), creds.getPassword());
			return service;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Test
	public void testGetClassifiers(){
		assertTrue(service.getClassifiers().execute().getClassifiers().size()>0);
	}
	@Test
	public void testClassiferReady(){
		assertTrue(service.getClassifier(classifierID).execute().getStatus().equals(Classifier.Status.AVAILABLE));
	}
//	@Test
//	public void eTestClassification(){
//		Classification returned = service.classify(testID,"Will it rain today?").execute();
//		assertTrue(returned.getClasses().remove(0).getName().equals("conditions"));
//	}
	@Test
	public void testSimpleClassificationIT(){
		Classification returned = service.classify(classifierID, "computer").execute();
		assertTrue(returned.getClasses().get(0).getName().equals("Information Technology"));
	}
	@Test
	public void testSimpleClassificationPsych(){
		Classification returned = service.classify(classifierID, "psychology").execute();
		assertTrue(returned.getClasses().get(0).getName().contains("Social Science"));
	}
	@Test
	public void testClassificationTeacher(){
		String keywords = "Substitute teacher, School";
		Classification returned = service.classify(classifierID, keywords).execute();
		String clazz = returned.getClasses().get(0).getName();
		String message = "Result is \"" + clazz + "\" instead of \"Education\"";
		assertTrue(message, clazz.equals("Education"));
	}
	@Test
	public void testClassificationNick(){
		String keywords = "Cognitive science, Neuroscience, Programming language";
		Classification returned = service.classify(classifierID, keywords).execute();
		String clazz = returned.getClasses().get(0).getName();
		String message = "Result is \"" + clazz + "\" instead of \"Social Science\"";
		assertTrue(message, clazz.contains("Social Science"));
	}
	@Test
	public void testClassificationLogan(){
		String keywords = "HTML, JavaScript, C, Java";
		Classification returned = service.classify(classifierID, keywords).execute();
		String clazz = returned.getClasses().get(0).getName();
		String message = "Result is \"" + clazz + "\" instead of \"Information Technology\"";
		assertTrue(message, clazz.contains("Information Technology"));
	}
}