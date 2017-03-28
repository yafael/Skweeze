package lib_test;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import watson_services.Credentials;
import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Concept;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Concepts;

public class Alchemy_Test {
	private static Credentials creds;
	
	private static Concepts runService(String test) throws Exception{
		creds = (creds == null) ? Credentials.loadCreds("credentials/alchemy_cred") : creds;
		Map<String,Object> params = new HashMap<>();
		params.put(AlchemyLanguage.TEXT, test);
		AlchemyLanguage service = new AlchemyLanguage(creds.getAPI());
		return service.getConcepts(params).execute();
	}
	private static List<String> getKeywords(List<Concept> concepts){
		List<String> keywords = new ArrayList<>();
		for(Concept concept : concepts)
			keywords.add(concept.getText());
		return keywords;
	}
	@Test
	public void testTeacherResume() throws Exception{
		String test = "Substitute Teacher, Grace Community School District 220, Chicago, IL Winter 2008-2009"
				+ "\n• Requested as a substitute teacher by numerous teachers in Grace Middle School"
				+ "\n• Maintained order in the classroom by efficiently executing lesson plans left by the teacher."
				+ "\n• Applied the necessary teaching strategies to use in classrooms with various skill levels.";
		Concepts concepts = runService(test);
		List<Concept> conceptList = concepts.getConcepts();
		List<String> keywords = getKeywords(conceptList);
		assertTrue(keywords.contains("Substitute teacher"));
		assertTrue(keywords.contains("Education"));
		assertTrue(keywords.contains("School"));
	}
	@Test
	public void testNickResume() throws Exception{
		String test = "Skills"
				+ "- Computer programming (HTML, Java, MATLAB, C++/C#)"
				+ "- Time management, customer service, task prioritizing, and self-management"
				+ "- CITI HSR and RCR certified"
				+ "- Microsoft office programs and AutoCAD"
				+ "- Light board operating"
				+ "- Laboratory experience: chemistry, biology, cognitive neuroscience, computer vision"
				+ "- fMRI safety trained";
		Concepts concepts = runService(test);
		List<Concept> conceptList = concepts.getConcepts();
		List<String> keywords = getKeywords(conceptList);
		assertTrue(keywords.contains("Cognitive science"));
		assertTrue(keywords.contains("Neuroscience"));
		assertTrue(keywords.contains("Programming language"));
	}
}
