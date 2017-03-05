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
		
		KeywordExtractor extractor = new KeywordExtractor();
		String keywords = extractor.getKeywords(resumeText);
		
		//System.out.println(keywords);
		
		CategoryClassifier classifier = new CategoryClassifier();
		List<ClassifiedClass> topClasses = classifier.getTopClasses(keywords, 3);
		
		retrieveTopJobs(resumeText, topClasses);
		
		printClassificationResults(topClasses);
	}
	
	private static void retrieveTopJobs(String resumeText, List<ClassifiedClass> topClasses) throws IOException, SolrServerException {
		RandR retrieve = new RandR();
		for (ClassifiedClass c : topClasses) {
			retrieve.rank(resumeText, c);
		}
	}
	
	private static void printClassificationResults(List<ClassifiedClass> topClasses) {
		for (ClassifiedClass c : topClasses) {
			System.out.println("Class: " + c.getName() + " - Confidence: " + c.getConfidence());
		}
	}
}
