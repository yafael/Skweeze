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
	
	private final static int NLC_KEYWORD_COUNT = 30;
	private final static int NLC_NUMBER_OF_CLASSES = 3;
	private final static int RETRIEVE_AND_RANK_KEYWORD_COUNT = 35;
	
	public static ArrayList<RankedJobPosting> searchForJobs(File resume) throws IOException, SolrServerException {
		DocConverter dc = new DocConverter();
		String resumeText = dc.convert(resume.getAbsolutePath());
		
		KeywordExtractor extractor = new KeywordExtractor();
		String keywords = extractor.getKeywords(resumeText, NLC_KEYWORD_COUNT);

		CategoryClassifier classifier = new CategoryClassifier();
		List<ClassifiedClass> topClasses = classifier.getTopClasses(keywords, NLC_NUMBER_OF_CLASSES);
		float total = 0;
		for (ClassifiedClass c : topClasses) {
			total+= c.getConfidence();
		}
		for (ClassifiedClass c : topClasses) {
			c.setConfidence(c.getConfidence()/total);
		}
		
		String k = extractor.getKeywords(resumeText, RETRIEVE_AND_RANK_KEYWORD_COUNT);
		RandR retrieve = new RandR();
		ArrayList<RankedJobPosting> topPostings = retrieve.rank(k, topClasses);
		
		return topPostings;
	}
}
