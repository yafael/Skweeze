package main;

import helper_classes.RankedJobPosting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import watson_services.CategoryClassifier;
import watson_services.DocConverter;
import watson_services.KeywordExtractor;
import watson_services.RandR;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.ClassifiedClass;

public class JobFinder {

	private ArrayList<RankedJobPosting> topPostings;
	
	public JobFinder() {
		topPostings = new ArrayList<>();
	}
	
	public ArrayList<RankedJobPosting> getTopPostings() {
		return topPostings;
	}
	
	public void searchForJobs(File resume) throws IOException, SolrServerException {
		DocConverter dc = new DocConverter();
		String resumeText = dc.convert(resume.getAbsolutePath());
		
		KeywordExtractor extractor = new KeywordExtractor();
		String keywords = extractor.getKeywords(resumeText, 40);

		CategoryClassifier classifier = new CategoryClassifier();
		List<ClassifiedClass> topClasses = classifier.getTopClasses(keywords, 3);
		
		printClassificationResults(topClasses);
		
		String k = extractor.getKeywords(resumeText, 5);
		RandR retrieve = new RandR();
		topPostings = retrieve.rank(k, topClasses);

		printPostingResults(topPostings);
	}
	
	private void printClassificationResults(List<ClassifiedClass> topClasses) {
		for (ClassifiedClass c : topClasses) {
			System.out.println("Class: " + c.getName() + " - Confidence: " + c.getConfidence());
		}
	}
	
	private void printPostingResults(ArrayList<RankedJobPosting> topPostings) {
		for (int i = 0; i < 5; i++) {
			RankedJobPosting p = topPostings.get(i);
			System.out.println("Text: " + p.getPostingText());
			System.out.println("Ranking: " + p.getRanking());
			System.out.println("Category: " + p.getCategory());
		}
	}
}
