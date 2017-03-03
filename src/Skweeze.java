import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.ClassifiedClass;

import watson_services.*;

// hey
public class Skweeze {
	public static void main(String[] args) throws IOException, SolrServerException {
		DocConverter dc = new DocConverter();
		String resumeText = dc.convert("data/teacher_resume.pdf");
		//String resumeText = dc.convert("data/accounting_major_resume.pdf");
		//String resumeText = dc.convert("data/CV.pdf");
		
		//QuestionGenerator qg = new QuestionGenerator();
		//qg.generateQuestions();
		
		KeywordExtractor extractor = new KeywordExtractor();
		String keywords = extractor.getKeywords(resumeText);
		RandR retrieve = new RandR();
		retrieve.rank("computer");
		
		//System.out.println(keywords);
		
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
