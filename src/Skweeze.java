import java.io.IOException;
import java.util.List;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.ClassifiedClass;

import watson_services.*;

public class Skweeze {
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException {
		DocConverter dc = new DocConverter();
		String resumeText = dc.convert("data/CV.pdf");
		
		KeywordExtractor extractor = new KeywordExtractor();
		String concepts = extractor.getKeywords(resumeText);
		
		CategoryClassifier classifier = new CategoryClassifier();
		List<ClassifiedClass> topClasses = classifier.getTopClasses(concepts, 3);
		
		printClassificationResults(topClasses);
	}
	
	private static void printClassificationResults(List<ClassifiedClass> topClasses) {
		for (ClassifiedClass c : topClasses) {
			System.out.println("Class: " + c.getName() + " - Confidence: " + c.getConfidence());
		}
	}
}
