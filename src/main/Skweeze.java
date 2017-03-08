package main;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import helper_classes.RankedJobPosting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.json.JSONException;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.ClassifiedClass;

import watson_services.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class Skweeze {
	
	public static void main(String[] args) throws IOException, SolrServerException, JSONException {
		
		staticFileLocation("/public");
		
		// Displays webpage at http://localhost:4567/
		get("/", (request, response) -> {
	        Map<String, Object> attributes = new HashMap<>();
	        return new ModelAndView(attributes, "index.ftl");
	    }, new FreeMarkerEngine());
		
		DocConverter dc = new DocConverter();
		String resumeText = dc.convert("data/teacher_resume.pdf");
		//String resumeText = dc.convert("data/accounting_major_resume.pdf");
		//String resumeText = dc.convert("data/CV.pdf");

		//System.out.println(resumeText);
		
		KeywordExtractor extractor = new KeywordExtractor();
		String keywords = extractor.getKeywords(resumeText, 40);

		CategoryClassifier classifier = new CategoryClassifier();
		List<ClassifiedClass> topClasses = classifier.getTopClasses(keywords, 3);
		
		printClassificationResults(topClasses);
		
		String k = extractor.getKeywords(resumeText, 5);
		RandR retrieve = new RandR();
		ArrayList<RankedJobPosting> topPostings = retrieve.rank(k, topClasses);

		printPostingResults(topPostings);
		
		//stop();
	}

	private static void printClassificationResults(List<ClassifiedClass> topClasses) {
		for (ClassifiedClass c : topClasses) {
			System.out.println("Class: " + c.getName() + " - Confidence: " + c.getConfidence());
		}
	}
	
	private static void printPostingResults(ArrayList<RankedJobPosting> topPostings) {
		for (int i = 0; i < 5; i++) {
			RankedJobPosting p = topPostings.get(i);
			System.out.println("Text: " + p.getPostingText());
			System.out.println("Ranking: " + p.getRanking());
			System.out.println("Category: " + p.getCategory());
		}
	}
}
