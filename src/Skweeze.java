import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.ClassifiedClass;

import watson_services.*;

// hey
public class Skweeze {
	public static void main(String[] args) throws IOException, SolrServerException, JSONException {
		DocConverter dc = new DocConverter();
		String resumeText = dc.convert("data/teacher_resume.pdf");
		//String resumeText = dc.convert("data/accounting_major_resume.pdf");
		//String resumeText = dc.convert("data/CV.pdf");

		//QuestionGenerator qg = new QuestionGenerator();
		//qg.generateQuestions();
		KeywordExtractor extractor = new KeywordExtractor();
		String keywords = extractor.getKeywords(resumeText);
		RandR retrieve = new RandR();
		SolrDocumentList results = retrieve.rank("computer", "Education");
		//Map<Integer, JSONObject> formatted = retrieve.formatRanking(results);
		//String doc = formatted.get(1).get("searchText").toString();
		//System.out.println(doc);

		CategoryClassifier classifier = new CategoryClassifier();
		List<ClassifiedClass> topClasses = classifier.getTopClasses(keywords, 3);

		printClassificationResults(topClasses);
	}

	private static void printClassificationResults(List<ClassifiedClass> topClasses) {
		for (ClassifiedClass c : topClasses) {
			System.out.println("Class: " + c.getName() + " - Confidence: " + c.getConfidence());
		}
	}
}
